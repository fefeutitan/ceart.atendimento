package br.com.ivia.ceart.atendimento.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.ivia.ceart.atendimento.model.PessoaPerfil;

@Repository
public interface PessoaPerfilRepository extends PagingAndSortingRepository<PessoaPerfil, Integer>, JpaSpecificationExecutor<PessoaPerfil> {

}
