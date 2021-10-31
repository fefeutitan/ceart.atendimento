package br.com.ivia.ceart.atendimento.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ivia.ceart.atendimento.model.AtendimentoAnexo;
import br.com.ivia.ceart.atendimento.repository.AtendimentoAnexoRepository;
import br.com.ivia.ceart.atendimento.util.exception.AtendimentoException;

@Service
public class AtendimentoAnexoService {

    @Autowired
    private AtendimentoAnexoRepository repository;
    
    public AtendimentoAnexo save(AtendimentoAnexo entidade) {
   	     return repository.save(entidade);
    }
    
    public AtendimentoAnexo findById(Integer id) {
        Optional<AtendimentoAnexo> preCadastroAnexo = repository.findById(id);
        if (!preCadastroAnexo.isPresent()) {
            throw new AtendimentoException("Erro ao procurar o Atendimento anexo.");
        }
        return preCadastroAnexo.get();
    }
    
	public void delete(Integer id) {
		AtendimentoAnexo preCadastroAnexo = findById(id);
		repository.delete(preCadastroAnexo); 
	}
}
