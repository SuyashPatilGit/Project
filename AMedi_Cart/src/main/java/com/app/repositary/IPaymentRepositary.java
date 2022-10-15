package com.app.repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Payment;

public interface IPaymentRepositary extends JpaRepository<Payment, Integer> {

	//Get payment by order ID
	
}
