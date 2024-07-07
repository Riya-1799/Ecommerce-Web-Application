package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.exception.EmailFailureException;
import com.model.VerificationToken;

@Service
public class EmailService {
	
	@Value("${email.from}")
	private String fromAddress;
	
	@Autowired
	private JavaMailSender javamailsender;
	@Value("S{app.frontend.url}")
	private String url;
	
	private SimpleMailMessage makemailmessage() {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(fromAddress);
		return simpleMailMessage;
	}
	
	public void sendVerificationEmail(VerificationToken verificationToken) throws EmailFailureException {
		SimpleMailMessage message = makemailmessage();
		message.setTo(verificationToken.getUser().getEmail());
		message.setSubject("Verify Your Email");
		message.setText("Please follow the link below to verify your email to your acoount.\n"
				+ url + "/auth/verify?token=" + verificationToken.getToken());
		try {
			javamailsender.send(message);
		}catch(MailException ex) {
			throw new EmailFailureException();		}
	}


	

}
