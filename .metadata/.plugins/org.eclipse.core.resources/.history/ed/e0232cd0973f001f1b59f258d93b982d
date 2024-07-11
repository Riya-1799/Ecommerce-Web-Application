package com.example.demo.service;

import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.api.model.RegistrationBody;
import com.exception.UserAlreadyExitsException;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.service.UserService;

import jakarta.transaction.Transactional;

@SpringBootTest
public class UserServiceTest {
	
	@RegisterExtension
	private static GreenMailExtension greenmailextension = new GreenMailExtension(ServerSetupTest.SMTP)
		.withConfiguration(GreenMailConfiguration.aConfig().withUser("springboot", "Riyu170899!"))
		.withPerMethodLifecycle(true);
	
	@Autowired
	private UserService userservice;
	
	@Test
	@Transactional
	public void testRegisterUser() throws MessagingException {
		RegistrationBody registrationBody = new RegistrationBody();
		registrationBody.setUsername("UserA");
		registrationBody.setEmail("UserServiceTest$testRegisterUser@junit.com");
		registrationBody.setFirstname("Firstname");
		registrationBody.setLastname("lastname");
		registrationBody.setPassword("Password-q!");
		assertThrows(UserAlreadyExitsException.class, () -> userservice.registeruser(registrationBody), 
				"username should already be in use.");
		registrationBody.setUsername("UserServiceTest$testRegisterUser");
		registrationBody.setEmail("UserA@junit.com");
		assertThrows(UserAlreadyExitsException.class, () -> userservice.registeruser(registrationBody), 
				"Email should already be in use.");
		registrationBody.setEmail("UserServiceTest$testRegisterUser@junit.com");
		assertDoesNotThrow(()->userservice.registeruser(registrationBody), "user should register successfully.");
		assertEquals("UserServiceTest$testRegisterUser@junit.com", greenmailextension.getReceivedMessages()[0].getRecipients(Message.RecipientType.TO)[0].toString());
		
	}

}
