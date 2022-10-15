package com.app.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AuthRequest;
import com.app.dto.AuthResp;
import com.app.dto.DeliveryBoyDto;
import com.app.dto.SupplierDto;
import com.app.dto.UserDto;
import com.app.jwt_utils.JwtUtils;
import com.app.service.DeliveryBoy.IDeliveryBoyService;
import com.app.service.Supplier.ISupplierService;
import com.app.service.user.IUserService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class SignInSignUpController {
//dep : JWT utils : for generating JWT
	@Autowired
	private JwtUtils utils;
	// dep : Auth mgr
	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private IUserService userService;

	@Autowired
	private ISupplierService supplierService;

	@Autowired
	private IDeliveryBoyService deliveryBoyService;

	// add a method to authenticate user . Incase of success --send back token , o.w
	// send back err mesg
	@PostMapping("/signin")
	public ResponseEntity<?> validateUserCreateToken(@RequestBody @Valid AuthRequest request,BindingResult br) {
		// store incoming user details(not yet validated) into Authentication object
		// Authentication i/f ---> imple by UserNamePasswordAuthToken
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getEmail(),
				request.getPassword());
		log.info("auth token " + authToken);
		try {
			// authenticate the credentials
			Authentication authenticatedDetails = manager.authenticate(authToken);
			UserDto user = userService.getUserByEmail(authenticatedDetails.getName());

			// => auth succcess
			return ResponseEntity
					.ok(new AuthResp("Auth successful!", utils.generateJwtToken(authenticatedDetails), user));
		} catch (BadCredentialsException e) { // lab work : replace this by a method in global exc handler
			// send back err resp code
			System.out.println("err " + e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

	}
	/*
	 * @PostMapping("/customer") public ResponseEntity<?> customerLogi(@RequestBody
	 * LoginDto customer) {
	 * log.info("In Authentication Controller : customer login method"); CustomerDto
	 * authCustomer = customerService.authenticateCustomer(customer.getEmail(),
	 * customer.getPassword()); if (authCustomer != null) { return new
	 * ResponseEntity<>(authCustomer, HttpStatus.OK); } else { return new
	 * ResponseEntity<>("Invalid Email or Password", HttpStatus.FORBIDDEN); } }
	 */

	// add request handling method for user registration
	@PostMapping("/user/signup")
	public ResponseEntity<?> userRegistration(@RequestBody @Valid UserDto user,BindingResult br) {
		System.out.println("in reg user : user " + user + " roles " + user.getRole());// {....."roles" :
																						// [ROLE_USER,...]}
		// invoke service layer method , for saving : user info + associated roles info
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
	}

	// add request handling method for supplier registration
	@PostMapping("/supplier/signup")
	public ResponseEntity<?> supplierRegistration(@RequestBody @Valid SupplierDto supplier,BindingResult br) {
		System.out.println("in reg supplier : supplier " + supplier + " roles " + supplier.getRole());// {....."roles"
				if(br.hasErrors()) {
					return  ResponseEntity.badRequest().body(br.getAllErrors());// :
				}
		// [ROLE_USER,...]}
		// invoke service layer method , for saving : user info + associated roles info
		return ResponseEntity.status(HttpStatus.CREATED).body(supplierService.saveSupplier(supplier));
	}

	// add request handling method for user registration
	@PostMapping("/deliveryboy/signup")
	public ResponseEntity<?> deliveryBoyRegistration(@RequestBody @Valid DeliveryBoyDto deliveryBoy) {
		System.out.println("in reg deliveryBoy : deliveryBoy " + deliveryBoy + " roles " + deliveryBoy.getRole());// {....."roles"
																													// :
		// [ROLE_USER,...]}
		// invoke service layer method , for saving : user info + associated roles info
		return ResponseEntity.status(HttpStatus.CREATED).body(deliveryBoyService.saveDeliveryBoy(deliveryBoy));
	}
}
