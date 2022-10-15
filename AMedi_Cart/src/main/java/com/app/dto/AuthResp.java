package com.app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AuthResp {

	public AuthResp(String message, String jwt, Object object) {
		super();
		this.message = message;
		this.jwt = jwt;
		this.user = object;
	}

	private String message;
	private String jwt;
	private Object user;

}
