package br.com.ivia.ceart.atendimento.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Perfil não encontrada.")
@Getter 
public class PerfilException extends RuntimeException  {

	private static final long serialVersionUID = 5963446697702952493L;
	
	public PerfilException(String message) {
	    super(message);
	}
}
