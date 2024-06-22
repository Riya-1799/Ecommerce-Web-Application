package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.model.RegistrationBody;
import com.exception.UserAlreadyExitsException;
import com.model.LocalUser;
import com.model.Dao.LocalUserDao;

import jakarta.servlet.Registration;
import jakarta.validation.Valid;

@Service
public class UserService {
	
	@Autowired
	private LocalUserDao localuserdao;

	public LocalUser registeruser(RegistrationBody registrationbody) throws UserAlreadyExitsException{
		if (localuserdao.findByEmailIgnoreCase(registrationbody.getEmail()).isPresent() ||
		localuserdao.findByUsernameIgnoreCase(registrationbody.getUsername()).isPresent()){
			throw new UserAlreadyExitsException("User Already Exists");
			
		}
		LocalUser user = new LocalUser();
		user.setEmail( registrationbody.getEmail());
		user.setFirstname(registrationbody.getFirstname());
		user.setLastname(registrationbody.getLastname());
		user.setUsername(registrationbody.getUsername());
		user.setPassword(registrationbody.getPassword());
		user = localuserdao.save(user);
		
		return user;
		
	}
}
