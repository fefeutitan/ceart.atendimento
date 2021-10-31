package br.com.ivia.ceart.atendimento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.ivia.ceart.atendimento.model.MotivoSituacao;
import br.com.ivia.ceart.atendimento.repository.MotivoSituacaoRepository;
import br.com.ivia.ceart.atendimento.to.MotivoSituacaoTO;
import br.com.ivia.ceart.atendimento.util.enums.SituacaoAtual;
import br.com.ivia.ceart.atendimento.util.exception.AtendimentoException;
import br.com.ivia.ceart.atendimento.util.specification.MotivoSituacaoSpecification;

@Service
public class MotivoSituacaoService {

	@Value("${recurso.atendimento}")
	private Integer atedimentoId;
	
	@Autowired
	MotivoSituacaoRepository repository;

	public List<MotivoSituacao> findBySituacao(SituacaoAtual situacao) {
		MotivoSituacaoTO to = new MotivoSituacaoTO();
		to.setSituacao(situacao);
		to.setIdRecurso(atedimentoId);
		to.setAtivo(true);
		
		MotivoSituacaoSpecification specification = new MotivoSituacaoSpecification(to);
		List<MotivoSituacao> list = repository.findAll(specification);
		if (list == null || list.isEmpty()) {
			throw new AtendimentoException("Erro ao procurar por motivos de alterar situação.");
		}
		return list;
	}
}
