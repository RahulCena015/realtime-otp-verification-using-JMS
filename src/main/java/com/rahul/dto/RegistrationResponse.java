package com.rahul.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationResponse {
	
	private String username;
	private String email;
	private String message;
	

}
