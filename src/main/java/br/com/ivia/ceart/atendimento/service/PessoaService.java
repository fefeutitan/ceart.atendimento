package br.com.ivia.ceart.atendimento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import br.com.ivia.ceart.atendimento.model.Pessoa;
import br.com.ivia.ceart.atendimento.repository.PessoaRepository;
import br.com.ivia.ceart.atendimento.util.exception.OpcaoAtendimentoException;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;
    
    public List<Pessoa> findAll() {
        List<Pessoa> pessoas = Lists.newArrayList(repository.findAll());
        if (pessoas == null || pessoas.isEmpty()) {
            throw new OpcaoAtendimentoException("Erro ao procurar as pessoas.");
        }
        return pessoas;
    }
}
