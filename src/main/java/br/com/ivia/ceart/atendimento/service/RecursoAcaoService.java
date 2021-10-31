package br.com.ivia.ceart.atendimento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ivia.ceart.atendimento.model.RecursoAcao;
import br.com.ivia.ceart.atendimento.repository.RecursoAcaoRepository;
import br.com.ivia.ceart.atendimento.to.RecursoAcaoTO;
import br.com.ivia.ceart.atendimento.util.specification.RecursoAcaoSpecification;

@Service
public class RecursoAcaoService {

	@Autowired
	private RecursoAcaoRepository repository;
	
	public List<RecursoAcao> findAllBy(RecursoAcaoTO recursoAcaoTO) {
		RecursoAcaoSpecification specification = new RecursoAcaoSpecification(recursoAcaoTO);
		return repository.findAll(specification);
	}
}
