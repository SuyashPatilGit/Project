package com.app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDto {
	@NotBlank
	@Email(message = "Invalid Email")
	private String email;

	@JsonProperty(access = Access.WRITE_ONLY) // for de-serial only
	private String oldPassword;

	@JsonProperty(access = Access.WRITE_ONLY) // for de-serial only
	private String newPassword;
}
