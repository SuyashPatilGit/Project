package com.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.app.dto.ProductDto;
import com.app.service.Product.IProductService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {
	@Autowired
	private IProductService prodService;

	// Add a request handling method to Add new product
	@PostMapping("/add")
	public ResponseEntity<?> addProdcut(@RequestBody ProductDto productdto) {
		log.info("In product Controller : addProdcut");
		ProductDto savedProd = prodService.addProduct(productdto);
		return new ResponseEntity<>(savedProd, HttpStatus.OK);

	}

	// Add a request Handling method to delete a product by Product id
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/delete") // can use ANY name for a path var.
	public String deleteProductDetails(@RequestParam int id) {
		log.info("in delete Product " + id);
		return prodService.deleteProductById(id);
	}

	// Add a method to get all product list
	@GetMapping("/view")
	public ResponseEntity<?> getProductList() {
		log.info("In Product controller : getProductList");
		List<ProductDto> listProduct = prodService.getAllProduct();
		log.info("list :" + listProduct.toString());
		return new ResponseEntity<>(listProduct, HttpStatus.OK);
	}

	// Add method to update existing Product
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductDto updateprod) {
		log.info("In Product controller : updateProduct");
		return new ResponseEntity<>(prodService.updateProduct(updateprod), HttpStatus.OK);
	}

	// Add a method to get product details by Product Id
	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") int id) {
		log.info("In product Controller : getProduct by Id");
		ProductDto savedProduct = prodService.getProductById(id);
		System.out.println(savedProduct);
		return new ResponseEntity<>(savedProduct, HttpStatus.OK);
	}

}
