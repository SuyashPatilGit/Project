package com.app.repositary;

import java.util.List;

import com.app.entities.Product;

public interface ProductRepositary /* extends JpaRepository<Product, Long> */ {

	List<Product> getList();

	String loadDatabase(List<Product> listOfProducts);
}
