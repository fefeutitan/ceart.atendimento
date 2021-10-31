package br.com.ivia.ceart.atendimento.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ivia.ceart.atendimento.service.OpcaoAtendimentoService;
import br.com.ivia.ceart.atendimento.to.MessageTO;
 
@RestController
@RequestMapping(path = "opcaoatendimento")
public class OpcaoAtendimentoRestController {
	
    @Autowired
    private OpcaoAtendimentoService service;
    
	@RequestMapping("")
	public String index(){
		return "RestController: Index - OpcaoAtendimento";
	}
	
    @GetMapping(path = "findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageTO> findAll() {
        return new ResponseEntity<>(new MessageTO("Opçãos de atendimentos encontrados", true, service.findAll()), HttpStatus.OK);
    }
    
    @GetMapping(path = "findByTipoId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageTO> findByTipoId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(new MessageTO("Opçãos de atendimentos encontrados", true, service.findByTipoId(id)), HttpStatus.OK);
    }
}
