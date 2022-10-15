package com.app.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AddressDto {

	private int pinCode;

	@NotBlank(message = "City is required")
	private String city;

	@NotBlank(message = "state is required")
	private String state;

	@NotBlank(message = "landmark is required")
	private String landMark;

	@NotBlank(message = "house no is required")
	private String addressLine;

	@Override
	public String toString() {
		return "Address [pinCode=" + pinCode + ", city=" + city + ", state=" + state + ", landMark=" + landMark
				+ ", addressLine=" + addressLine + "]";
	}

}
