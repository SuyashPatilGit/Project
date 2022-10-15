package com.app.dto;

import com.app.entities.Address;
import com.app.entities.PaymentType;
import com.app.entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderByCartDto {
	private int id;
	private Address address;
	private PaymentType paymentType;
}
