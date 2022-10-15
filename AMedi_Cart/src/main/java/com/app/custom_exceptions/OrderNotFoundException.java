package com.app.custom_exceptions;

@SuppressWarnings("serial")
public class OrderNotFoundException extends Exception {

	public OrderNotFoundException(String message) {
		super(message);
	}

}
