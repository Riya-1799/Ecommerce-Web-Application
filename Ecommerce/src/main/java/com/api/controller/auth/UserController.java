package com.api.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.model.LoginBody;
import com.api.model.LoginResponse;
import com.api.model.RegistrationBody;
import com.exception.EmailFailureException;
import com.exception.UserAlreadyExitsException;
import com.exception.UserNotVerifiedException;
import com.model.LocalUser;
import com.service.UserService;

import io.micrometer.core.ipc.http.HttpSender.Response;
import jakarta.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userservice;

    @PostMapping("/auth/register")
    public ResponseEntity<Void> adduser(@Valid @RequestBody RegistrationBody registrationBody) {
    	try {
			userservice.registeruser(registrationBody);
			return ResponseEntity.ok().build();
		} catch (UserAlreadyExitsException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}catch(EmailFailureException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
    }
    @PostMapping("/login")
    public ResponseEntity loginUser(@Valid @RequestBody LoginBody loginbody ) {
    	String jwt = null;
		try {
			jwt = userservice.loginuser(loginbody);
		} catch (UserNotVerifiedException e) {
			LoginResponse loginResponse = new LoginResponse();
			loginResponse.setSuccess(false);
			String reason = "USER_NOT_VERIFIED";
			if(e.isNewemailsent()) {
				reason += "EMAIL_RESENT";
			}
			loginResponse.setFailureReason(reason);
			return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(loginResponse);
		} catch (EmailFailureException e) {
			return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    	if(jwt == null) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    	}else {
    		LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            response.setSuccess(true);
            return ResponseEntity.ok(response);
    		
    	}
    }
    @GetMapping("/me")
    public LocalUser getLoggedInUseProfile(@AuthenticationPrincipal LocalUser user){
    	return user;
    	
    	
    }
    
    @PostMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam String token) {
    	if(userservice.verifyuser1(token)) {
    		return ResponseEntity.ok().build();
    	}else {
    		return ResponseEntity.status(HttpStatus.CONFLICT).build();
    	}
    }
		
}

