package com.rahul.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
	@NotBlank(message = "username cannot be blank, its a manadatory field")
	private String username;
	private String password;
	 @Email(message = "invalid email id")
	private String email;

}
