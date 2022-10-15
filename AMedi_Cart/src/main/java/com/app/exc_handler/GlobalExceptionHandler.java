package com.app.exc_handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.app.custom_exceptions.OrderNotFoundException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.custom_exceptions.ShoppingCartException;
import com.app.custom_exceptions.UserHandlingException;
import com.app.dto.ErrorResponse;

@ControllerAdvice // mandatory : to tell SC following class contains centralized exc handler
					// method/s
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
		System.out.println("in handle run time exc " + e);
		ErrorResponse resp = new ErrorResponse("Adding User failed plz Retry!!!!!", LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
	}

	// How to tell SC : follo. method acts as the exc handling method
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleExcetpion(Exception e) {
		System.out.println("In handle user exception ....." + e);
		ErrorResponse resp = new ErrorResponse(e.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
	}

	// Globally handle customer Exception
	@ExceptionHandler(UserHandlingException.class)
	public ResponseEntity<?> handleUserHandlingException(UserHandlingException e) {
		System.out.println("In handle user exception ..... " + e);
		ErrorResponse resp = new ErrorResponse(e.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentialsException(RuntimeException e) {
		System.out.println("In handle BadCredentialsException exc " + e);
		ErrorResponse resp = new ErrorResponse("Wrong Credentials: " + e.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(RuntimeException e) {
		System.out.println("in handle IllegalArgumentException exc " + e);
		ErrorResponse resp = new ErrorResponse(e.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException o) {
		System.out.println("In order not found exception ..... " + o);
		ErrorResponse resp = new ErrorResponse(o.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
	}

	@ExceptionHandler(ShoppingCartException.class)
	public ResponseEntity<?> handleShoppingCartException(ShoppingCartException o) {
		System.out.println("In Shopping Cart exception ..... " + o);
		ErrorResponse resp = new ErrorResponse(o.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException o) {
		System.out.println("In Resource Not Found exception ..... " + o);
		ErrorResponse resp = new ErrorResponse(o.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
	}

}
