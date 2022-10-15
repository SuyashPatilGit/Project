package com.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.custom_exceptions.UserHandlingException;
import com.app.dto.DispatchOrderDTO;
import com.app.dto.SupplierDto;
import com.app.entities.PlaceOrder;
import com.app.service.Supplier.ISupplierService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/supplier")
@Slf4j
public class SupplierController {
	@Autowired
	private ISupplierService iSupplierService;

	// Add a method to register new supplier
	@PostMapping("/register")
	public ResponseEntity<?> addSupplier(@RequestBody @Valid SupplierDto supplierDto) {
		log.info("In Supplier controller : addSupplier");
		return new ResponseEntity<>(iSupplierService.saveSupplier(supplierDto), HttpStatus.OK);
	}

	// Add method to update existing supplier
	@PutMapping("/update")
	public ResponseEntity<?> updateSupplier(@RequestBody @Valid SupplierDto updateSupplier) {
		log.info("In Supplier controller : updateSupplier");
		return new ResponseEntity<>(iSupplierService.updateSupplier(updateSupplier), HttpStatus.OK);
	}

	// Add a method to get all customer list
	@GetMapping("/suppliers")
	public ResponseEntity<?> getSupplierList() {
		log.info("In Supplier controller : getSupplierList");
		List<SupplierDto> listSupplier = iSupplierService.getAllSupplier();
		log.info("list :" + listSupplier.toString());
		return new ResponseEntity<>(listSupplier, HttpStatus.OK);
	}

	@DeleteMapping("/delete") // can use ANY name for a path var.
	public String deleteSupplierDetails(@RequestParam int id) {
		log.info("In  Supplier Controll DeleteSupplier " + id);
		return iSupplierService.deleteSupplierById(id);
	}

	@GetMapping("/error")
	public void throwNewError() {
		throw new UserHandlingException("hello its working ...Good job");
	}

	// Add a request handling method to get customer by customer id

	@GetMapping("/{id}")
	public ResponseEntity<?> getSupplierById(@PathVariable("id") int id) {
		log.info("In Supplier Controller :getSupplierById");
		SupplierDto supplierdto = iSupplierService.getSupplierById(id);
		return new ResponseEntity<>(supplierdto, HttpStatus.OK);
	}

	// add a request handling method to see all place order
	@GetMapping("/orders")
	public ResponseEntity<?> getListOfOrders(@PathVariable("id") int id) {
		log.info("In Supplier Controller :getListOfOrders");
		List<PlaceOrder> seePlacedOrders = iSupplierService.seePlacedOrders(id);
		return new ResponseEntity<>(seePlacedOrders, HttpStatus.OK);
	}

	// Add a request handling method to complete order sent by admin
	@PostMapping("/dispatch")
	public ResponseEntity<?> dispatchPlacedOrder(@RequestBody DispatchOrderDTO dispatchOrder) {
		log.info("In Supplier Controller : Dispatch PlacedOrder");
		iSupplierService.dispatchOrder(dispatchOrder);
		return new ResponseEntity<>("ok", HttpStatus.OK);
	}

}
