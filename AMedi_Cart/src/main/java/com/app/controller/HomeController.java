package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.service.Product.IProductService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/home")
@Slf4j
public class HomeController {

	@Autowired
	private IProductService prod;

	@GetMapping("/loaddb")
	public ResponseEntity<?> hello() {
		log.info("in home Controller ");
		prod.loadDatabase();
		return new ResponseEntity<>("hello world ", HttpStatus.OK);

	}

}
