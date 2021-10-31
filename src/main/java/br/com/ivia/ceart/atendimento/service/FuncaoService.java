package br.com.ivia.ceart.atendimento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import br.com.ivia.ceart.atendimento.model.Funcao;
import br.com.ivia.ceart.atendimento.repository.FuncaoRepository;
import br.com.ivia.ceart.atendimento.util.exception.OpcaoAtendimentoException;

@Service
public class FuncaoService {

    @Autowired
    private FuncaoRepository repository;
    
    public List<Funcao> findAll() {
        List<Funcao> funcoes = Lists.newArrayList(repository.findAll());
        if (funcoes == null || funcoes.isEmpty()) {
            throw new OpcaoAtendimentoException("Erro ao procurar as funções.");
        }
        return funcoes;
    }
}
