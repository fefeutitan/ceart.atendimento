package br.com.ivia.ceart.atendimento.util.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.ivia.ceart.atendimento.service.EmailService;
//import br.com.ivia.ceart.atendimento.service.MockEmailService;
import br.com.ivia.ceart.atendimento.service.SmtpEmailService;


@Configuration
public class EmailConfig {
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
		//return new MockEmailService();
		
	}

}
