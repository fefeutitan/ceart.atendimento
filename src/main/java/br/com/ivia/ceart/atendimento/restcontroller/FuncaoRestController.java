package br.com.ivia.ceart.atendimento.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ivia.ceart.atendimento.service.FuncaoService;
import br.com.ivia.ceart.atendimento.to.MessageTO;

@RestController
@RequestMapping(path = "funcao")
public class FuncaoRestController {
	
    @Autowired
    private FuncaoService service;
    
	@RequestMapping("")
	public String index(){
		return "RestController: Index - Funcao";
	}
	
    @GetMapping(path = "findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageTO> findAll() {
        return new ResponseEntity<>(new MessageTO("Funções encontradas", true, service.findAll()), HttpStatus.OK);
    }
}
