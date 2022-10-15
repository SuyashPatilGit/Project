package com.app.service.ShoppingCart;

import java.util.List;

import com.app.custom_exceptions.ShoppingCartException;
import com.app.entities.CartItem;
import com.app.entities.User;

public interface IShoppingCartService {

	Integer addProduct(Integer productId, Integer quantity, Integer customerId) throws ShoppingCartException;

	List<CartItem> listCartItems(Integer customerId);

	float updateQuantity(Integer productId, Integer quantity, User customer);

	double getTotalAmount(Integer customerId);

	void removeProduct(Integer productId, Integer customerId);

	void deleteByUser(Integer customerId);

}
