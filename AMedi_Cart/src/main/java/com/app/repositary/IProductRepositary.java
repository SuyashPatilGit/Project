package com.app.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.entities.Product;

@Repository
public interface IProductRepositary extends JpaRepository<Product, Integer> {

	// Add a method to increment stock (stock= stock + new supplied quantity)
	@Query("update Product p set p.stock=p.stock+?1 where p.id=?2")
	@Modifying // MANDATORY for DML
	int incrementStock(int addQty, int prodId);

	// Add a method to decrement stock (stock= stock - quantity purchased)
	@Query("update Product p set p.stock=p.stock-?1 where p.id=?2")
	@Modifying // MANDATORY for DML
	int decrementStock(int decQty, int prodId);

	// Add a method check product equality on basis of product name
	@Query("select p from Product p where p.productName=?1")
	Product checkProduct(String productName);

	/*
	 * // Find Product By id Optional<Product> findById(Integer id);
	 * 
	 * // Find product By name Product findByProductName(String product_name);
	 * 
	 * // find product By generic name Product findByGenericName(String
	 * generic_name);
	 * 
	 * // find product by category List<Product>
	 * findByProductCategory(ProductCategory category);
	 */
}
