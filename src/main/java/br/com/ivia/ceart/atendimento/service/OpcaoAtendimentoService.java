package br.com.ivia.ceart.atendimento.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import br.com.ivia.ceart.atendimento.model.OpcaoAtendimento;
import br.com.ivia.ceart.atendimento.model.PessoaCelula;
import br.com.ivia.ceart.atendimento.repository.OpcaoAtendimentoRepository;
import br.com.ivia.ceart.atendimento.util.exception.OpcaoAtendimentoException;
import br.com.ivia.ceart.atendimento.util.specification.OpcaoAtendimentoSpecification;

@Service
public class OpcaoAtendimentoService {

    @Autowired
    private OpcaoAtendimentoRepository repository;
    
    public List<OpcaoAtendimento> findAll() {
        List<OpcaoAtendimento> opcoes = Lists.newArrayList(repository.findAll());
        if (opcoes == null || opcoes.isEmpty()) {
            throw new OpcaoAtendimentoException("Erro ao procurar as opçoes de atendimentos.");
        }
        return opcoes;
    }
    
    public List<OpcaoAtendimento> findByTipoId(Integer id) {
    	OpcaoAtendimentoSpecification specification = new OpcaoAtendimentoSpecification(id);
    	List<OpcaoAtendimento> opcoes = repository.findAll(specification);
    	
    	List<PessoaCelula> validos = new ArrayList<PessoaCelula>();
    	Date data = new Date();		
    	for(OpcaoAtendimento opcaoAtendimento : opcoes) {
    		validos = new ArrayList<PessoaCelula>();
    		for(PessoaCelula pessoaCelula : opcaoAtendimento.getCelula().getPessoa()) {
    			if((pessoaCelula.getDt_inicio() != null && pessoaCelula.getDt_fim() != null) && ((pessoaCelula.getDt_inicio().before(data) || pessoaCelula.getDt_inicio().equals(data)) && (pessoaCelula.getDt_fim().after(data) || pessoaCelula.getDt_fim().equals(data)))) {				
    				validos.add(pessoaCelula);
    			} else if((pessoaCelula.getDt_inicio() != null && pessoaCelula.getDt_fim() == null) && (pessoaCelula.getDt_inicio().before(data) || pessoaCelula.getDt_inicio().equals(data))) {
    				validos.add(pessoaCelula);
    			}
    		}
    		
    		if(validos != null) {
	    		opcaoAtendimento.getCelula().getPessoa().clear();
	    		opcaoAtendimento.getCelula().setPessoa(validos); 
    		}
    	}
  	
    	
    	if (opcoes == null || opcoes.isEmpty()) {
			throw new OpcaoAtendimentoException("Erro ao procurar as opçoes de atendimentos.");
		}
    	return opcoes;
    }
}
