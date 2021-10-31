package br.com.ivia.ceart.atendimento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import br.com.ivia.ceart.atendimento.model.Perfil;
import br.com.ivia.ceart.atendimento.repository.PerfilRepository;
import br.com.ivia.ceart.atendimento.util.exception.OpcaoAtendimentoException;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository repository;
    
    public List<Perfil> findAll() {
        List<Perfil> perfis = Lists.newArrayList(repository.findAll());
        if (perfis == null || perfis.isEmpty()) {
            throw new OpcaoAtendimentoException("Erro ao procurar os perfis.");
        }
        return perfis;
    }
}
