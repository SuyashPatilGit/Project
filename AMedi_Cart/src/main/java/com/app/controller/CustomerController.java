package com.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.custom_exceptions.UserHandlingException;
import com.app.dto.UpdatePasswordDto;
import com.app.dto.UserDto;
import com.app.service.user.IUserService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/customer")
@Slf4j
public class CustomerController {

	@Autowired
	private IUserService userService;

	// Add method to update existing customer
	@PutMapping("/update")
	public ResponseEntity<?> updateCustomer(@RequestBody @Valid UserDto updateCustomer) {
		log.info("In Customer controller : updateCustomer for " + updateCustomer.getEmail() + ", role:  "
				+ updateCustomer.getRole());
		return new ResponseEntity<>(userService.updateUser(updateCustomer), HttpStatus.OK);
	}

	// add a method to update password
	@PutMapping("/updatepassword")
	public ResponseEntity<?> updatePassword(@RequestBody @Valid UpdatePasswordDto update) {
		log.info("In Customer controller : updatePassword for  " + update.getEmail());
		boolean check = userService.updatePassword(update.getEmail(), update.getOldPassword(), update.getNewPassword());
		if (check == true) {
			log.info("password updated");
			return new ResponseEntity<>("password updated", HttpStatus.OK);
		} else {
			log.error("password NOT updated");
			return new ResponseEntity<>("password  NOT updated", HttpStatus.OK);
		}
	}

	// checking method to confirm working of error handling
	@GetMapping("/error")
	public void throwNewError() {
		throw new UserHandlingException("hello its working ...Good job");
	}

	// Add a request handling method to get customer by customer id

	@GetMapping("/{id}")
	public ResponseEntity<?> getCustomerByHisId(@PathVariable("id") int id) {
		log.info("In Customer Controller :getCustomerById");
		UserDto customer = userService.getCustomerId(id);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

}
