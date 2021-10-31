package br.com.ivia.ceart.atendimento.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.com.ivia.ceart.atendimento.service.TipoAtendimentoService;
import br.com.ivia.ceart.atendimento.to.MessageTO;

@RestController
@RequestMapping(path = "tipoatendimento")
public class TipoAtendimentoRestController {
	
    @Autowired
    private TipoAtendimentoService service;
    
	@RequestMapping("")
	public String index(){
		return "RestController: Index - TipoAtendimento";
	}
	
    @GetMapping(path = "findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageTO> findAll() {
        return new ResponseEntity<>(new MessageTO("Tipos de atendimentos encontrados", true, service.findAll()), HttpStatus.OK);
    }
}
