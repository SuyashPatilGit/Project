package com.app.service.Product;

import java.util.List;

import com.app.dto.ProductDto;

public interface IProductService {

	ProductDto addProduct(ProductDto product);

	String deleteProductById(int product_Id);

	ProductDto updateProduct(ProductDto updateProduct);

	List<ProductDto> getAllProduct();

	ProductDto getProductById(int productId);
	
	String loadDatabase();
}
