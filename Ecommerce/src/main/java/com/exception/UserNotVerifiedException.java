package com.exception;

public class UserNotVerifiedException extends Exception{
	
	private boolean newemailsent;
	public UserNotVerifiedException(boolean newemailsent) {
		this.newemailsent = newemailsent;
	}
	public boolean isNewemailsent() {
		return newemailsent;
	}
}
