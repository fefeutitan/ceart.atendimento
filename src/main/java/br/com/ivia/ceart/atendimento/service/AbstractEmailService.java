package br.com.ivia.ceart.atendimento.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.ivia.ceart.atendimento.model.Atendimento;
import br.com.ivia.ceart.atendimento.model.AtendimentoAnexo;
import br.com.ivia.ceart.atendimento.model.PessoaCelula;
import br.com.ivia.ceart.atendimento.util.exception.AtendimentoException;

public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}")
	private String sender;	
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	StorageService storageService;
	
	@Override
	public void sendOrderConfirmationEmail(Atendimento obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Atendimento obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		//será disparado um email automático para a usuária responsável cadastrada na célula 
		//para testar estou enviando para o solicitante
		sm.setTo(obj.getEmail());
		//com cópia para a Gestora do projeto.
		sm.setBcc(sender);
		sm.setFrom(sender);
		sm.setSubject("[Atendimento] " + obj.getAnoProtocolo() + "/" + obj.getNumeroProtocolo() 
					+ " Tipo: " + obj.getTipoAtendimento().getNome() 
					+ " Opção: " + obj.getOpcaoAtendimento().getNome());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}

	protected String htmlFromTemplatePedido(Atendimento obj) {
		Context context = new Context();
		context.setVariable("atendimento", obj);
		return templateEngine.process("email/confirmacaoAtendimento", context);		
	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Atendimento obj){
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(obj);
		}
	}

	protected MimeMessage prepareMimeMessageFromPedido(Atendimento obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		 		
		if(obj.getOpcaoAtendimento().getCelula() == null) {
			throw new AtendimentoException("A opção de atendimento não está vinculado a nenhuma celula!");
		}
		
		Date data = new Date();		
		List<String> emails = new ArrayList<String>();
		String emailCCO = obj.getOpcaoAtendimento().getCelula().getEmail_cc();
		
		for(PessoaCelula pessoaCelula : obj.getOpcaoAtendimento().getCelula().getPessoa()) {
			if((pessoaCelula.getDt_inicio() != null && pessoaCelula.getDt_fim() != null) 
					&& (pessoaCelula.getDt_inicio().before(data) && pessoaCelula.getDt_fim().after(data))
					&& (pessoaCelula.getResponsavel_email())) {				
				emails.add(pessoaCelula.getPessoa().getEmail());
			} else if((pessoaCelula.getDt_inicio() != null && pessoaCelula.getDt_fim() == null) 
					&& (pessoaCelula.getDt_inicio().before(data))
					&& (pessoaCelula.getResponsavel_email())) {
				emails.add(pessoaCelula.getPessoa().getEmail());
			}else {						
				emails.add(obj.getOpcaoAtendimento().getCelula().getEmail_cc());
			}
		}
		
		String emailTO = String.join(",", emails);
		
		mmh.setTo(emailTO);
		mmh.setCc(emailCCO);
		mmh.setFrom(sender);
		mmh.setSubject(obj.getAnoProtocolo() + "/" + obj.getNumeroProtocolo()
						+ " - " + obj.getOpcaoAtendimento().getTipoAtendimento().getNome() 
						+ " - " + obj.getOpcaoAtendimento().getNome());
		mmh.setSentDate(new Date(System.currentTimeMillis()));			
		mmh.setText(htmlFromTemplatePedido(obj),true);
		
		if(obj.getAnexo() != null) {
			for(AtendimentoAnexo anexo : obj.getAnexo()) {
				Resource file = storageService.loadFile(anexo.getNome());
				mmh.addAttachment(file.getFilename(), file);
			}
		}

		
		return mimeMessage;
	}

}
