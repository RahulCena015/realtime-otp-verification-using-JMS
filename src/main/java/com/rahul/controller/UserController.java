package com.rahul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.dto.LoginRequest;
import com.rahul.dto.RegistrationRequest;
import com.rahul.dto.RegistrationResponse;
import com.rahul.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<RegistrationResponse> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest){
		RegistrationResponse registrationResponse = userService.register(registrationRequest);
		return new ResponseEntity<>( registrationResponse,HttpStatus.CREATED);
	}
	
	@PostMapping("/verify")
	public ResponseEntity<?> verifyUser(@RequestParam String email,@RequestParam String otp){
		try {
			userService.verify(email, otp);
			return new ResponseEntity<>("User verified successfully",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
		userService.login(loginRequest);
		return new ResponseEntity<>("user logined successfully",HttpStatus.OK);
		
	}

}
