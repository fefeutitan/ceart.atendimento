package br.com.ivia.ceart.atendimento.util.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.ivia.ceart.atendimento.model.MotivoSituacao;
import br.com.ivia.ceart.atendimento.model.Recurso;
import br.com.ivia.ceart.atendimento.to.MotivoSituacaoTO;
import br.com.ivia.ceart.atendimento.util.enums.SituacaoAtual;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MotivoSituacaoSpecification implements Specification<MotivoSituacao> {

	private static final long serialVersionUID = 526893085782026542L;

	private MotivoSituacaoTO criteria;

	@Override
	public Predicate toPredicate(Root<MotivoSituacao> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Path<Integer> recurso = root.get("recursoId");
		Path<SituacaoAtual> situacao = root.get("situacao");
		Path<Boolean> ativo = root.get("ativo");
		
		List<Predicate> predicates = new ArrayList<>();

		if (criteria.getIdRecurso() != null) {
			predicates.add(cb.equal(recurso, criteria.getIdRecurso()));
		}
		if (criteria.getSituacao() != null) {
			predicates.add(cb.equal(situacao, criteria.getSituacao()));
		}
		if (criteria.getAtivo() != null) {
			predicates.add(cb.equal(ativo, criteria.getAtivo()));
		}

		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}
}
