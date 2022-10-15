package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.DeliveryBoyDto;
import com.app.dto.PlaceOrderDto;
import com.app.dto.SupplierDto;
import com.app.dto.UpdatePriceDto;
import com.app.dto.UserDto;
import com.app.service.DeliveryBoy.IDeliveryBoyService;
import com.app.service.Supplier.ISupplierService;
import com.app.service.admin.IAdminService;
import com.app.service.user.IUserService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
@Slf4j
public class AdminController {

	@Autowired
	private IUserService userService;

	@Autowired
	private ISupplierService suplService;

	@Autowired
	private IAdminService adminService;

	@Autowired
	IDeliveryBoyService deliveryBoyService;

	// --------request handling methods related to customer-----------

	// Add a method to get all customer list
	@GetMapping("/customers")
	public ResponseEntity<?> getCustomerList() {
		List<UserDto> listCustomer = userService.getAllCustomer();
		log.info("list :" + listCustomer.toString());
		return new ResponseEntity<>(listCustomer, HttpStatus.OK);
	}

	// ---------request handling methods related to supplier----------
	// Add a method to get all customer list
	@GetMapping("/suppliers")
	public ResponseEntity<?> getSupplierList() {
		List<SupplierDto> listSupplier = suplService.getAllSupplier();
		log.info("list :" + listSupplier.toString());
		return new ResponseEntity<>(listSupplier, HttpStatus.OK);
	}

	// ---------request handling methods related to delivery BOY----------

	// Add a method to get all customer list
	@GetMapping("/deliveryboys")
	public ResponseEntity<?> getDeliveryBoysList() {
		List<DeliveryBoyDto> listDBoy = deliveryBoyService.getAllDeliveryBoy();
		log.info("list :" + listDBoy.toString());
		return new ResponseEntity<>(listDBoy, HttpStatus.OK);
	}

	// Add request handling method to place order to supplier

	@PostMapping("/placeorder")
	public ResponseEntity<?> generateOrder(@RequestBody PlaceOrderDto placeOrder) {
		String msg = adminService.placeOrder(placeOrder);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	// Add a method to update price to product
	@PostMapping("/updateprice")
	public ResponseEntity<?> updatePrice(@RequestBody UpdatePriceDto updatePrice) {

		adminService.UpdatePrice(updatePrice);
		return new ResponseEntity<>("ok", HttpStatus.OK);
	}
}
