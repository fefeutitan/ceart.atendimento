package br.com.ivia.ceart.atendimento.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ivia.ceart.atendimento.model.Atendimento;
import br.com.ivia.ceart.atendimento.model.MotivoSituacao;
import br.com.ivia.ceart.atendimento.service.AtendimentoService;
import br.com.ivia.ceart.atendimento.service.MotivoSituacaoService;
import br.com.ivia.ceart.atendimento.to.AtendimentoTO;
import br.com.ivia.ceart.atendimento.to.MessageTO;
import br.com.ivia.ceart.atendimento.to.MotivoSituacaoTO;
import br.com.ivia.ceart.atendimento.util.enums.SituacaoAtual;

@RestController
@RequestMapping(path = "motivosituacao")
public class MotivoSituacaoRestController {

	@Autowired
	private MotivoSituacaoService service;


	@PostMapping(path = "findBySituacao", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageTO> findBySituacao(@RequestBody AtendimentoTO to) {
		return new ResponseEntity<>(
				new MessageTO("Opçoes de motivos encontrados", true, service.findBySituacao(to.getSituacao())),
				HttpStatus.OK);
	}
}
