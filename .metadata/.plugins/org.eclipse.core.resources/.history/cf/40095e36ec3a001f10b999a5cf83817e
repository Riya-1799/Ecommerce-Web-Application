package com.service;


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.api.model.LoginBody;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.model.LocalUser;
import com.model.Dao.LocalUserDao;

import jakarta.annotation.PostConstruct;

@Service
public class JWTService {
	
	@Value("${jwt.algorithm.key}")
	private String algorithmkey;
	@Value("${jwt.issuer}")
	private String issuer;
	@Value("${jwt.expiryInSeconds}")
	private int expiryInSeconds;
	private Algorithm algorithm;
	
	private static final String USERNAME_KEY = "USERNAME";
	private static final String EMAIL_KEY = "EMAIL";
	
	@Autowired
	private LocalUserDao localuserdao;
	
	@Autowired
	private LoginBody loginbody;
	
	@PostConstruct
	public void postConstruct() throws Exception, UnsupportedEncodingException {
		algorithm = Algorithm.HMAC256(algorithmkey);
	}
	
	public String genrateJWT(LocalUser user) {
		return JWT.create()
				.withClaim("USERNAME", user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()*(1000*expiryInSeconds)))
				.withIssuer(issuer)
				.sign(algorithm);
	}
	
	public String genrateVerificationJWT(LocalUser user) {
		return JWT.create()
				.withClaim("EMAIL", user.getEmail())
				.withExpiresAt(new Date(System.currentTimeMillis()*(1000*expiryInSeconds)))
				.withIssuer(issuer)
				.sign(algorithm);
	}
	public String getusername(String token) {
		return JWT.decode(token).getClaim(USERNAME_KEY).asString();
	}
	
	
	

}
