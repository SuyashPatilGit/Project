package com.app.custom_exceptions;

@SuppressWarnings("serial")
public class ShoppingCartException extends Exception {

	public ShoppingCartException(String message) {
		super(message);
	}

}
