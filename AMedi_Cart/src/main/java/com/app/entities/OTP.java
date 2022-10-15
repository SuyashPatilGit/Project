package com.app.entities;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OTP {

	@Id
	@Column(length = 50)
	private String email;

	private int otp;

	private LocalTime timeStamp;

	public OTP(String email, int otp) {
		super();
		this.email = email;
		this.otp = otp;
		this.timeStamp = LocalTime.now();
	}

	public OTP() {
		super();
		this.timeStamp = LocalTime.now();
	}

}
