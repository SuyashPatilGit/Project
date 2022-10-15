package com.app.dto;

import com.app.entities.User;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewPasswordDto {

	@NotNull
	private String email;

	@NotNull
	private String newPassword;
}
