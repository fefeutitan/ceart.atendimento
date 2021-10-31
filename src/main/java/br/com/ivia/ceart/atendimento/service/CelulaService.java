package br.com.ivia.ceart.atendimento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import br.com.ivia.ceart.atendimento.model.Celula;
import br.com.ivia.ceart.atendimento.repository.CelulaRepository;
import br.com.ivia.ceart.atendimento.util.exception.OpcaoAtendimentoException;

@Service
public class CelulaService {

    @Autowired
    private CelulaRepository repository;
    
    public List<Celula> findAll() {
        List<Celula> celulas = Lists.newArrayList(repository.findAll());
        if (celulas == null || celulas.isEmpty()) {
            throw new OpcaoAtendimentoException("Erro ao procurar as celulas.");
        }
        return celulas;
    }
}
