package br.com.ivia.ceart.atendimento.util.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.ivia.ceart.atendimento.model.HistoricoAtendimento;
import br.com.ivia.ceart.atendimento.to.AtendimentoTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HistoricoAtendimentoSpecification implements Specification<HistoricoAtendimento> {

	private static final long serialVersionUID = 89218473926645474L;

	private Integer criteria;

	@Override
	public Predicate toPredicate(Root<HistoricoAtendimento> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

		Path<Integer> atendimentoId = root.get("atendimento");
		final List<Predicate> predicate = new ArrayList<Predicate>();

		if (criteria != null) {
			predicate.add(cb.equal(atendimentoId, criteria));
		}

		return cb.and(predicate.toArray(new Predicate[predicate.size()]));
	}
}
