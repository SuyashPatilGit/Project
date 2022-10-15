package com.app.repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.entities.CartItem;
import com.app.entities.Product;
import com.app.entities.User;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	public List<CartItem> findByUser(User user);

	public CartItem findByUserAndProduct(User customer, Product product);

	@Modifying
	@Query("UPDATE CartItem c SET c.quantity = ?1 WHERE c.user.id = ?2 AND c.product.id = ?3")
	public void updateQuantity(Integer quantity, Integer customerId, Integer productId);

	@Modifying
	@Query("DELETE FROM CartItem c WHERE c.user.id = ?1 AND c.product.id = ?2")
	public void deleteByUserAndProduct(Integer customerId, Integer productId);

	@Modifying
	@Query("DELETE CartItem c WHERE c.user.id = ?1")
	public void deleteByUser(Integer customerId);
}
