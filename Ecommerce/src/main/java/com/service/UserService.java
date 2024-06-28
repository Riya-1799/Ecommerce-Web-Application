package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.model.LoginBody;
import com.api.model.RegistrationBody;
import com.auth0.jwt.algorithms.Algorithm;
import com.exception.UserAlreadyExitsException;
import com.model.LocalUser;
import com.model.Dao.LocalUserDao;

import jakarta.servlet.Registration;
import jakarta.validation.Valid;

@Service
public class UserService {
	
	@Autowired
	private LocalUserDao localuserdao;
	private int expiryInSeconds;
	private Algorithm algorithm;
	private static final String USERNAME_KEY = "USERNAME";
	@Autowired
	private JWTService jwtservice;
	
	@Autowired
	private EncryptionService encryptionservice;

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
		user.setPassword(encryptionservice.encryptPassword(registrationbody.getPassword()));
		user = localuserdao.save(user);
		
		return user;
		
	}
	public String loginuser(LoginBody loginbody) {
		Optional<LocalUser> opuser = localuserdao.findByUsernameIgnoreCase(loginbody.getUsername());
		if(opuser.isPresent()) {
			LocalUser user = opuser.get();
			if(EncryptionService.verifypassword(loginbody.getPassword(), user.getPassword())) {
				return jwtservice.genrateJWT(user);
			}
		}
		return null;
}
}
