package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.custom_exceptions.ShoppingCartException;
import com.app.dto.AddProductDto;
import com.app.dto.RemoveCartItemsDto;
import com.app.entities.CartItem;
import com.app.service.ShoppingCart.IShoppingCartService;

import lombok.extern.slf4j.Slf4j;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/cart")
@Slf4j
public class ShoppingCartController {

	// dependency injection
	@Autowired
	private IShoppingCartService cartService;

	// Add a request handling method for get list of cart items
	@GetMapping("/{id}")
	public ResponseEntity<?> getCartItemsList(@PathVariable("id") int id) {
		log.info("In Shopping Cart controller : Get cart item list");
		List<CartItem> cartitems = cartService.listCartItems(id);
		return new ResponseEntity<>(cartitems, HttpStatus.OK);
	}

	// Add a request handling method to product In cart for speified customer
	@PostMapping("/add")
	public ResponseEntity<?> addProductInCart(@RequestBody AddProductDto prodDto) throws ShoppingCartException {
		log.info("In Shopping Cart controller : addProductInCart");
		cartService.addProduct(prodDto.getProductId(), prodDto.getQuantity(), prodDto.getCustomerId());
		return new ResponseEntity<>("Added sucessfully", HttpStatus.OK);

	}

	// Add a request handling method to delete items from cart for a specified
	// customer
	@DeleteMapping("/remove")
	public ResponseEntity<?> removeItemsFromCart(@RequestBody RemoveCartItemsDto info) {
		log.info("In Shopping Cart controller : removeItemsFromCart");
		cartService.removeProduct(info.getProductId(), info.getCustomerId());
		return new ResponseEntity<>("item removed", HttpStatus.OK);

	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<?> emptyCart(@PathVariable int id) {
		log.info("In Shopping Cart controller : empty cart");
		cartService.deleteByUser(id);
		return new ResponseEntity<>("item deleted ", HttpStatus.OK);
	}

	// Add amethod to get total amount of cart

	@GetMapping("/total/{id}")
	public ResponseEntity<?> getTotalCartAmount(@PathVariable int id) {
		double amount = cartService.getTotalAmount(id);
		return new ResponseEntity<>(amount, HttpStatus.OK);
	}
}
