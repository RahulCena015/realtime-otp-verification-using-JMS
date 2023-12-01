package com.rahul.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationRequest {
	
	private String username;
	private String password;
	private String email;

}
