package br.com.ivia.ceart.atendimento.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import br.com.ivia.ceart.atendimento.model.Atendimento;
import br.com.ivia.ceart.atendimento.model.AtendimentoAnexo;
import br.com.ivia.ceart.atendimento.model.HistoricoAtendimento;
import br.com.ivia.ceart.atendimento.model.MotivoSituacao;
import br.com.ivia.ceart.atendimento.repository.AtendimentoRepository;
import br.com.ivia.ceart.atendimento.repository.HistoricoAtendimentoRepository;
import br.com.ivia.ceart.atendimento.to.AtendimentoTO;
import br.com.ivia.ceart.atendimento.to.MessageTO;
import br.com.ivia.ceart.atendimento.to.MotivoSituacaoTO;
import br.com.ivia.ceart.atendimento.util.enums.SituacaoAtual;
import br.com.ivia.ceart.atendimento.util.exception.AtendimentoException;
import br.com.ivia.ceart.atendimento.util.exception.OpcaoAtendimentoException;
import br.com.ivia.ceart.atendimento.util.specification.AtendimentoSpecification;
import br.com.ivia.ceart.atendimento.util.specification.HistoricoAtendimentoSpecification;

@Service
public class AtendimentoService {

	@Autowired
	private EmailService emailService;

	@Autowired
	private AtendimentoRepository repository;

	@Autowired
	HistoricoAtendimentoRepository histRepository;

	public List<Atendimento> findAll() {
		List<Atendimento> atendimentos = Lists.newArrayList(repository.findAll());
		if (atendimentos == null || atendimentos.isEmpty()) {
			throw new OpcaoAtendimentoException("Erro ao procurar os atendimentos.");
		}
		return atendimentos;
	}

	public MessageTO findAll(AtendimentoTO atendimento) {
		AtendimentoSpecification specification = new AtendimentoSpecification(atendimento);
		PageRequest pageRequest = PageRequest.of(atendimento.getPageNumber() - 1, atendimento.getPageSize(),
				new Sort(atendimento.getDirection(), atendimento.getBy()));
		Page<Atendimento> page = repository.findAll(specification, pageRequest);
		List<Atendimento> atendimentos = page.getContent();
		return new MessageTO("Atendimento encontrados", true, page.getTotalPages(), atendimentos);
	}

	public Atendimento findById(Integer id) {
		Optional<Atendimento> atendimento = repository.findById(id);
		if (!atendimento.isPresent()) {
			throw new AtendimentoException("Erro ao procurar o atendimento");
		}

		Atendimento object = atendimento.get();

		if (object.getSituacao() == SituacaoAtual.ENCERRADO) {
			if (object.getOpcaoAtendimento().getPadrao()) {
				object.setMotivo(null);
				object.setObservacao("Atendimento encerrado automaticamente.");
			} else {
				HistoricoAtendimentoSpecification specification = new HistoricoAtendimentoSpecification(object.getId());
				String[] by = { "id" };
				List<HistoricoAtendimento> historico = histRepository.findAll(specification,
						new Sort(Direction.DESC, by));

				if (!historico.isEmpty()) {
					for (HistoricoAtendimento hist : historico) {
						if (hist.getMotivo() != null && hist.getMotivo().getSituacao() != null
								&& hist.getMotivo().getSituacao().equals(SituacaoAtual.ENCERRADO)) {
							
							object.setMotivo(hist.getMotivo() != null ? hist.getMotivo().getDescricao() : "");
							object.setObservacao(hist.getObservacao());
							break;
						}
					}
				}
			}
		}

		return object;
	}

	@Transactional
	public Atendimento save(Atendimento entidade) {
		if (entidade.getId() == null) {
			if (entidade.getOpcaoAtendimento().getPadrao()) {
				entidade.setSituacao(SituacaoAtual.ENCERRADO);
			} else {
				entidade.setSituacao(SituacaoAtual.ENCAMINHADO);
			}

			Calendar calendar = Calendar.getInstance();
			entidade.setAnoProtocolo(calendar.get(Calendar.YEAR));
			entidade.setNumeroProtocolo("123456");
			entidade = repository.save(entidade);
			entidade.setNumeroProtocolo(gerarNumeroProtocolo(entidade.getId()));

		} else {
			emailService.sendOrderConfirmationHtmlEmail(entidade);
		}

		// Registrar o histórico
		HistoricoAtendimento hist = new HistoricoAtendimento();
		hist.setAtendimento(entidade.getId());
		hist.setData(new Date());
		hist.setUsuario(1); // TEMPORARIO
		String obs = "";
		if (entidade.getSituacao().equals(SituacaoAtual.ENCERRADO)) {
			obs = "Atendimento encerrado automaticamente.";
		} else if (entidade.getSituacao().equals(SituacaoAtual.ENCAMINHADO)) {
			obs = "Atendimento encaminhado automaticamente.";
		} else if (entidade.getSituacao().equals(SituacaoAtual.REENCAMINHADO)) {
			obs = "Atendimento reencaminhado.";
		}
		hist.setObservacao(obs);
		histRepository.save(hist);

		if (entidade.getAnexo() != null) {
			for (AtendimentoAnexo anexo : entidade.getAnexo()) {
				if (anexo.getAtendimento() == null) {
					anexo.setAtendimento(entidade);
				}
			}
		}

		return repository.save(entidade);
	}

	@Transactional
	public Atendimento reenviar(Atendimento entidade) {
		emailService.sendOrderConfirmationHtmlEmail(entidade);

		// Registrar o histórico
		HistoricoAtendimento hist = new HistoricoAtendimento();
		hist.setAtendimento(entidade.getId());
		hist.setData(new Date());
		hist.setUsuario(1); // TEMPORARIO
		hist.setObservacao("Atendimento reencaminhado.");
		histRepository.save(hist);

		return entidade;
	}

	@Transactional
	public HistoricoAtendimento alterarSituacao(MotivoSituacaoTO to) {

		Atendimento atendimento = repository.findById(to.getIdEntidade()).get();
		atendimento.setSituacao(SituacaoAtual.values()[to.getIdSituacao()]);
		repository.save(atendimento);

		HistoricoAtendimento hist = new HistoricoAtendimento();
		hist.setAtendimento(to.getIdEntidade());
		hist.setData(new Date());
		MotivoSituacao motivo = new MotivoSituacao();
		motivo.setId(to.getIdMotivo());
		hist.setMotivo(motivo);
		hist.setUsuario(1); // TEMPORARIO
		hist.setObservacao(to.getObservacao());

		return histRepository.save(hist);
	}

	public Atendimento enviarEmail(Atendimento entidade) {
		Optional<Atendimento> atendimento = repository.findById(entidade.getId());
		emailService.sendOrderConfirmationHtmlEmail(atendimento.get());
		return entidade;
	}

	private String gerarNumeroProtocolo(Integer atendimentoId) {
		Random gerador = new Random();
		int numeroRandom = Math.abs(gerador.nextInt(9000000));
		final String strZeros = "0000000";
		String numeroRandomFormat = String.valueOf(numeroRandom);
		String numeroRandomOk = numeroRandomFormat + strZeros.substring(numeroRandomFormat.length());

		final String formato = numeroRandomOk.toString();
		String idFormat = String.valueOf(atendimentoId);

		return formato.substring(idFormat.length()) + idFormat;
	}
}
