package com.app.cartTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.app.entities.CartItem;
import com.app.entities.Product;
import com.app.entities.User;
import com.app.repositary.CartItemRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ShoppingCartUnitTest {

	@Autowired
	private TestEntityManager testEntityManger;

	@Autowired
	private CartItemRepository cartRepository;

	// J-UNIT test to test Add One Cart Item
	@Test
	public void testAddOneCartItem() {
		Product product = testEntityManger.find(Product.class, 1);
		User customer = testEntityManger.find(User.class, 2);

		CartItem cartItem = new CartItem();
		cartItem.setUser(customer);
		cartItem.setProduct(product);
		cartItem.setQuantity(1);

		CartItem savedCartItems = cartRepository.save(cartItem);

		assertTrue(savedCartItems.getId() > 0);
	}
}