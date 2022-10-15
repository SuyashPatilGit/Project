package com.app.service.OTPservice;

import com.app.entities.OTP;

public interface OTPService {

	// set Otp
	boolean setOTP(OTP otp);

	// getOtp
	OTP getOTP(String email);

	boolean deleteById(String email);
}
