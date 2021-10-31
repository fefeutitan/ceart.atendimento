package br.com.ivia.ceart.atendimento.util.specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.ivia.ceart.atendimento.model.Atendimento;
import br.com.ivia.ceart.atendimento.to.AtendimentoTO;
import br.com.ivia.ceart.atendimento.util.exception.AtendimentoException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AtendimentoSpecification implements Specification<Atendimento> {

	private static final long serialVersionUID = -4872477752331955784L;

	private AtendimentoTO criteria;

	@Override
	public Predicate toPredicate(Root<Atendimento> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Path<Integer> anoProtocolo = root.get("anoProtocolo");
		Path<String> numeroProtocolo = root.get("numeroProtocolo");
		
		Path<String> solicitante = root.get("solicitante");
		Path<String> cpfCnpj = root.get("cpfCnpj");
		Path<Date> data = root.get("data");
		Path<String> tipoAtendimento = root.get("tipoAtendimento");
		Path<String> opcaoAtendimento = root.get("opcaoAtendimento");
		Path<String> situacao = root.get("situacao");
		final List<Predicate> predicates = new ArrayList<Predicate>();

		if (criteria.getSolicitante() != null) {
			predicates.add(cb.like(cb.lower(solicitante), "%" + criteria.getSolicitante().toLowerCase() + "%"));
		}
		
		if (criteria.getProtocolo() != null && !criteria.getProtocolo().trim().isEmpty()) {
			if(!criteria.getProtocolo().contains("/")) {
				throw new AtendimentoException("O número do protocolo não está no formato correto!");
			}
			
			String protocolo[] = criteria.getProtocolo().split("/");
			predicates.add(cb.equal(anoProtocolo, protocolo[0]));
			predicates.add(cb.equal(numeroProtocolo, protocolo[1]));
		}
		
		if (criteria.getDataInicioBusca() != null && criteria.getDataFimBusca() != null) {

			Date dataInicioBusca = setHorasDate(criteria.getDataInicioBusca());
			Date dataFimBusca = setHorasDate(criteria.getDataFimBusca());

			predicates.add(cb.between(data, dataInicioBusca, dataFimBusca));
		}
		if (criteria.getCpfCnpj() != null) {
			predicates.add(cb.equal(cpfCnpj, criteria.getCpfCnpj()));
		}
		if (criteria.getTipoAtendimento() != null) {
			predicates.add(cb.equal(tipoAtendimento, criteria.getTipoAtendimento()));
		}
		if (criteria.getOpcaoAtendimento() != null) {
			predicates.add(cb.equal(opcaoAtendimento, criteria.getOpcaoAtendimento()));
		}
		if (criteria.getSituacao() != null) {
			predicates.add(cb.equal(situacao, criteria.getSituacao()));
		}

		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}

	private Date setHorasDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);

		return calendar.getTime();
	}
}
