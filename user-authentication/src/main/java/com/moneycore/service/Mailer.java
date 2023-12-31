package com.moneycore.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.moneycore.email.Mail;
 
@Service
public class Mailer {
	
	
	private JavaMailSender mailSender;
	
	@Autowired
	public Mailer(JavaMailSender javamailSender) {
		this.mailSender = javamailSender;
	}
	
	
	public void sendMail(Mail message, boolean isHtml) throws MessagingException {
		
		MimeMessage emailMessage = mailSender.createMimeMessage();
		MimeMessageHelper mailBuilder = new MimeMessageHelper(emailMessage, true);
			
		mailBuilder.setTo(message.getMailTo());
		mailBuilder.setFrom(message.getMailFrom());			
		mailBuilder.setText(message.getMailContent(), isHtml); // Second parameter indicates that this is HTML mail
		mailBuilder.setSubject(message.getMailSubject());
				
		mailSender.send(emailMessage);
			
	}
}