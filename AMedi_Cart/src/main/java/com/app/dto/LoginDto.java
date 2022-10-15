package com.app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

	@NotBlank
	@Email(message = "Invalid Email")
	private String email;

	// for de-serial only
	@JsonProperty(access = Access.WRITE_ONLY)
	private String Password;
}
