package com.api.model;

import org.springframework.stereotype.Component;

@Component
public class LoginBody {
	
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
}
