package com.api.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.model.LoginBody;
import com.api.model.LoginResponse;
import com.api.model.RegistrationBody;
import com.model.LocalUser;
import com.service.UserService;

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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
    }
    @PostMapping("/login")
    public ResponseEntity loginUser(@Valid @RequestBody LoginBody loginbody ) {
    	String jwt = userservice.loginuser(loginbody);
    	if(jwt == null) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    	}else {
    		LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            return ResponseEntity.ok(response);
    		
    	}
    }
    @GetMapping("/me")
    public LocalUser getLoggedInUseProfile(@AuthenticationPrincipal LocalUser user){
    	return user;
    	
    	
    }
		
}

