package com.main.neosoft.controller;

import com.main.neosoft.entity.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class LoginController {
	
	 @Autowired
	 private AuthenticationManager authenticationManager;

	
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody UserCredentials userCredentials){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userCredentials.getUsername(), userCredentials.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!!", HttpStatus.OK);
    }
}
