package br.com.ivia.ceart.atendimento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import br.com.ivia.ceart.atendimento.model.TipoAtendimento;
import br.com.ivia.ceart.atendimento.repository.TipoAtendimentoRepository;
import br.com.ivia.ceart.atendimento.util.exception.TipoAtendimentoException;

@Service
public class TipoAtendimentoService {

    @Autowired
    private TipoAtendimentoRepository repository;
    
    public List<TipoAtendimento> findAll() {
        List<TipoAtendimento> tipos = Lists.newArrayList(repository.findAll());
        if (tipos == null || tipos.isEmpty()) {
            throw new TipoAtendimentoException("Erro ao procurar os tipos de atendimentos.");
        }
        return tipos;
    }
}
