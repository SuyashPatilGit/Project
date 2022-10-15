package com.app.service.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.UserHandlingException;
import com.app.dataUtils.DataUtils;
import com.app.dto.ProductDto;
import com.app.entities.Product;
import com.app.repositary.IProductRepositary;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ProductServiceImpl implements IProductService {

//Dependency Injection
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private IProductRepositary productdao;

	// Add a method to Add product
	@Override
	public ProductDto addProduct(ProductDto productDto) {
		log.info("In Product Service Implimentation : addProduct");
		log.info(" prod dto" + productDto);
		Product existingProd = productdao.checkProduct(productDto.getProductName());

		if (existingProd != null) {
			log.info("Existing Product :" + existingProd.toString());
			throw new UserHandlingException("Product Already exist");
			/*
			 * productdao.incrementStock(productDto.getStock(), productDto.getId()); Product
			 * aftersave = productdao.save(existingProd); return mapper.map(aftersave,
			 * ProductDto.class);
			 */
		} else {
			Product prod = mapper.map(productDto, Product.class);
			Product perProd = productdao.save(prod);
			return mapper.map(perProd, ProductDto.class);
		}

	}

	// Add a method to delete product
	@Override
	public String deleteProductById(int product_Id) {
		log.info("In Product service implimentation : deleteProductById ");
		String mesg = "Deletion of Product details failed!!!!!!!!!!!";

		if (productdao.existsById(product_Id)) {
			productdao.deleteById(product_Id);
			mesg = "Product details deleted successfully , for Supplier id :" + product_Id;
		}

		return mesg;
	}

	// Add a method to update existing product
	@Override
	public ProductDto updateProduct(ProductDto updateProduct) {
		log.info("In Product service implimentation : updateProduct ");

		Product existingProd = productdao.checkProduct(updateProduct.getProductName());
		log.info("existingProd :" + existingProd);

		if (existingProd != null) {
			existingProd.setProductName(updateProduct.getProductName());
			existingProd.setCompanyName(updateProduct.getCompanyName());
			existingProd.setGenericName(updateProduct.getGenericName());
			existingProd.setProductCategory(updateProduct.getProductCategory());
			existingProd.setProdDetails(updateProduct.getProdDetails());
			existingProd.setRx(updateProduct.getRx());
			existingProd.setStock(updateProduct.getStock());
			existingProd.setUnitPrice(updateProduct.getUnitPrice());
			Product persistent = productdao.save(existingProd);
			log.info("updated product" + existingProd.toString());
			return mapper.map(persistent, ProductDto.class);

		} else
			throw new UserHandlingException("invalid Product Details ");
		// we have to throw an new exception for invalid email id

	}

	// Add a method to get list of all Product

	@Override
	public List<ProductDto> getAllProduct() {
		log.info("In Supplier service implimentation : getAllSupplier ");
		List<Product> productList = productdao.findAll();
		List<ProductDto> products = new ArrayList<ProductDto>();
		for (Product prod : productList) {
			products.add(mapper.map(prod, ProductDto.class));
		}
		return products;
	}

	// Add a method to get product by its id
	@Override
	public ProductDto getProductById(int productId) {
		log.info("In Product service implimentation : getCustomerById ");

		if (productdao.existsById(productId)) {
			Optional<Product> product = productdao.findById(productId);
			return mapper.map(product, ProductDto.class);
		} else
			throw new UserHandlingException("invalid product ID");

	}

	// method to load products from xscl sheet
	@Override
	public String loadDatabase() {
		List<Product> listOfProducts = DataUtils.getListsOfProducts();
		for (Product product : listOfProducts) {
			productdao.save(product);
		}
		return "Congratulations All Products has been  SAVED Successfully";

		// return productRepo.loadDatabase(listOfProducts);
	}

}
