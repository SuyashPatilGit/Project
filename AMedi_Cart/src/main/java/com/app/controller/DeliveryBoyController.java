package com.app.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.custom_exceptions.OrderNotFoundException;
import com.app.dto.ChangeOrderStatusDto;
import com.app.entities.OrderStatus;
import com.app.entities.Orders;
import com.app.repositary.IOrederRepository;
import com.app.service.DeliveryBoy.IDeliveryBoyService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/deliboy")
@Slf4j
public class DeliveryBoyController {

	@Autowired
	private IDeliveryBoyService deliveryBoyService;

	@Autowired
	private IOrederRepository orderRepo;

	// Add a reuest handling method to see all assign orders for a speicific
	// delivery boy

	// add a method to change delivery status for deliveryed order

	@GetMapping("/shipped")
	public ResponseEntity<?> orderDeliveredSentOTP(@RequestParam("id") int orderId, HttpSession session)
			throws OrderNotFoundException {
		log.info("In Delivery boy Controller : orderDeliveredSentOTP");
		ChangeOrderStatusDto order = deliveryBoyService.changeStatusShipped(orderId);
		session.setAttribute("DB_OTP", order.getOTP());
		session.setAttribute("orders", order.getOrder());
		return new ResponseEntity<>("OTP is sent to customer email", HttpStatus.OK);

	}

	@PostMapping("/shipped")
	public ResponseEntity<?> orderDeliverd(@RequestBody int otp, HttpSession session) {
		log.info("In Delivery boy Controller : orderDelivered");
		int OTP = (int) session.getAttribute("DB_OTP");
		if (otp == OTP) {
			Orders order = (Orders) session.getAttribute("orders");
			order.setOrderStatus(OrderStatus.DELIVERED);
			orderRepo.save(order);
			log.info(order.toString());
			return new ResponseEntity<>("Your Order is delivered", HttpStatus.OK);
		}
		return new ResponseEntity<>("Wrong OTP", HttpStatus.FORBIDDEN);

	}
}
