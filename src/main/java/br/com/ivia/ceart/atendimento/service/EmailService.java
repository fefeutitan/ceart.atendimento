package br.com.ivia.ceart.atendimento.service;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.ivia.ceart.atendimento.model.Atendimento;

public interface EmailService {

	void sendOrderConfirmationEmail(Atendimento obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Atendimento obj);
	
	void sendHtmlEmail(MimeMessage msg);

}
