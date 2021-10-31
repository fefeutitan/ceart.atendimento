package br.com.ivia.ceart.atendimento.util.specification;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.ivia.ceart.atendimento.model.OpcaoAtendimento;
import br.com.ivia.ceart.atendimento.model.TipoAtendimento;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpcaoAtendimentoSpecification implements Specification<OpcaoAtendimento> {
	
	private static final long serialVersionUID = 5069937966580032131L;
	
	private Integer criteria;
	
	@Override
	public Predicate toPredicate(Root<OpcaoAtendimento> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		
		Path<TipoAtendimento> tipoAtendimento = root.get("tipoAtendimento");
		final List<Predicate> predicate = new ArrayList<Predicate>();
		
		if (criteria != null) {
			predicate.add(cb.equal(tipoAtendimento.get("id"), criteria));
		}
				
		return cb.and(predicate.toArray(new Predicate[predicate.size()]));
	}
	
}
