package com.rahul.service;

import com.rahul.dto.LoginRequest;
import com.rahul.dto.RegistrationRequest;
import com.rahul.dto.RegistrationResponse;

public interface UserService {
	
	RegistrationResponse register(RegistrationRequest registrationRequest);
	
	void verify(String email,String otp);
	
	void login(LoginRequest loginRequest);
	
	

}
