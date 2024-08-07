package com.service;

import java.security.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.model.LoginBody;
import com.api.model.RegistrationBody;
import com.auth0.jwt.algorithms.Algorithm;
import com.exception.EmailFailureException;
import com.exception.UserAlreadyExitsException;
import com.exception.UserNotVerifiedException;
import com.model.LocalUser;
import com.model.VerificationToken;
import com.model.Dao.LocalUserDao;
import com.model.Dao.VerificationTokenDAO;

import jakarta.transaction.Transactional;

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
	private EmailService emailservice;
	
	@Autowired
	private EncryptionService encryptionservice;
	@Autowired
	private VerificationTokenDAO verificationTokenDAO;

	public LocalUser registeruser(RegistrationBody registrationbody) throws UserAlreadyExitsException, EmailFailureException{
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
	
		VerificationToken verificationtoken = createverificationtoken(user);
		emailservice.sendVerificationEmail(verificationtoken);
		verificationTokenDAO.save(verificationtoken);
		return user;
		
	}
	
	private VerificationToken createverificationtoken(LocalUser user) {
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(jwtservice.genrateVerificationJWT(user));
		verificationToken.setCreatedTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
		verificationToken.setUser(user);
		user.getVerificationtoken().add(verificationToken);
		return verificationToken;
	}
	
	
	
	public String loginuser(LoginBody loginbody) throws UserNotVerifiedException, EmailFailureException {
		Optional<LocalUser> opuser = localuserdao.findByUsernameIgnoreCase(loginbody.getUsername());
		if(opuser.isPresent()) {
			LocalUser user = opuser.get();
			if(EncryptionService.verifypassword(loginbody.getPassword(), user.getPassword())) {
				if(user.getEmailverified()) {
				return jwtservice.genrateJWT(user);
				}else {
					List<VerificationToken> verificationtoken = user.getVerificationtoken();
					
					boolean resend = verificationtoken.size() == 0 || 
							verificationtoken.get(0).getCreatedTimestamp().before(new java.sql.Timestamp(System.currentTimeMillis()-(60*60*1000)));
					if(resend) {
						VerificationToken token = createverificationtoken(user);
						verificationTokenDAO.save(token);
						emailservice.sendVerificationEmail(token);
					}
					
					throw new UserNotVerifiedException(resend);
				}
			}
		}
		return null;
	}
	
	@Transactional
	public boolean verifyuser1(String token) {
		Optional<VerificationToken> optoken = verificationTokenDAO.findByToken(token);
		if(optoken.isPresent()) {
			VerificationToken verificationToken = optoken.get();
			LocalUser user = verificationToken.getUser();
			if(!user.getEmailverified()) {
				user.setEmailverified(true);
				localuserdao.save(user);
				verificationTokenDAO.deleteByUser(user);
				return true;
			}
	}return false;
	}
}
