package com.app.service.ShoppingCart;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.ShoppingCartException;
import com.app.custom_exceptions.UserHandlingException;
import com.app.entities.CartItem;
import com.app.entities.Product;
import com.app.entities.User;
import com.app.repositary.CartItemRepository;
import com.app.repositary.IProductRepositary;
import com.app.repositary.UserRepository;
import com.app.service.Product.IProductService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ShoppingCartService implements IShoppingCartService {

	// Dependency Injection

	@Autowired
	private CartItemRepository cartRepo;

	@Autowired
	private IProductRepositary productRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private IProductService prodService;

	// Add a method to add product in cart for a specified customer
	@Override
	public Integer addProduct(Integer productId, Integer quantity, Integer customerId) throws ShoppingCartException {
		Optional<User> OptionalCustomer = userRepo.findById(customerId);
		User customer = new User();
		if (OptionalCustomer != null) {
			customer = mapper.map(OptionalCustomer, User.class);
		} else {
			throw new UserHandlingException("NO cart details are found for given customer id");
		}
		log.info("In Shopping cart service : Add product to cart method");
		Integer updatedQuantity = quantity;
		Product product = mapper.map(prodService.getProductById(productId), Product.class);
		log.info("Product to add in cart" + product);
		CartItem cartItem = cartRepo.findByUserAndProduct(customer, product);

		if (cartItem != null) {
			updatedQuantity = cartItem.getQuantity() + quantity;
			if (updatedQuantity < 0) {
				throw new UserHandlingException("Invalid quanity");
			}
			if (updatedQuantity > 5) {
				throw new ShoppingCartException("Could not add more " + quantity + " item(s)"
						+ " because there's already " + cartItem.getQuantity() + " item(s) "
						+ "in your shopping cart. Maximum allowed quantity is 5.");
			}
		} else {
			cartItem = new CartItem();
			cartItem.setUser(customer);
			cartItem.setProduct(product);
		}

		cartItem.setQuantity(updatedQuantity);

		cartRepo.save(cartItem);

		return updatedQuantity;
	}

	// Add a method to get list of cart Items
	@Override
	public List<CartItem> listCartItems(Integer customerId) {
		log.info("In shopping cart service : list cart items method");
		Optional<User> OptionalCustomer = userRepo.findById(customerId);
		if (OptionalCustomer != null) {
			User customer = mapper.map(OptionalCustomer, User.class);
			return cartRepo.findByUser(customer);
		} else
			throw new UserHandlingException("Invalid customer details");
	}

	// Add a method to update Quanitiy of product in cart
	@Override
	public float updateQuantity(Integer productId, Integer quantity, User customer) {
		log.info("In shopping cart service : updateQuantity");
		cartRepo.updateQuantity(quantity, customer.getId(), productId);
		Product product = productRepo.findById(productId).get();
		float subtotal = (float) (product.getUnitPrice() * quantity);
		return subtotal;
	}

	// Add a method to remove products from cart
	@Override
	public void removeProduct(Integer productId, Integer customerId) {
		log.info("In shopping cart service : removeProduct");
		Optional<User> OptionalCustomer = userRepo.findById(customerId);
		User customer = new User();
		if (OptionalCustomer != null) {
			customer = mapper.map(OptionalCustomer, User.class);
		} else {
			throw new UserHandlingException("Invalid Customer Details");
		}
		cartRepo.deleteByUserAndProduct(customer.getId(), productId);
	}

	// Add a method to remove cart if customer deletes his account
	@Override
	public void deleteByUser(Integer customerId) {
		Optional<User> OptionalCustomer = userRepo.findById(customerId);
		if (OptionalCustomer != null) {
			User customer = mapper.map(OptionalCustomer, User.class);
			cartRepo.deleteByUser(customer.getId());
		} else
			throw new UserHandlingException("No cart details for given cutsomer id " + customerId);
	}

	// METHOD to get Total amount
	@Override
	public double getTotalAmount(Integer customerId) {
		double totalAmount = 0.00;
		log.info("In shopping cart service : getTotalAmount");
		List<CartItem> cartItems = listCartItems(customerId);
		for (CartItem cartItem : cartItems) {
			totalAmount = totalAmount + cartItem.getSubtotal();
		}
		return totalAmount;
	}
}
