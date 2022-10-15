package com.app.service.admin;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.PlaceOrderDto;
import com.app.dto.ProductDto;
import com.app.dto.SupplierDto;
import com.app.dto.UpdatePriceDto;
import com.app.entities.PlaceOrder;
import com.app.entities.Product;
import com.app.entities.Supplier;
import com.app.repositary.IPlaceOrderRepo;
import com.app.repositary.IProductRepositary;
import com.app.service.Product.IProductService;
import com.app.service.Supplier.ISupplierService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AdminServiceImpl implements IAdminService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ISupplierService supplierService;

	@Autowired
	private IPlaceOrderRepo placeOrderRepo;

	@Autowired
	private IProductService prodService;

	@Autowired
	private IProductRepositary productdao;

	@Override
	public String placeOrder(PlaceOrderDto placeOrder) {
		log.info("In admin service : Generate order to supplier by admin");
		String msg = "Order Genrated sucessfully";
		// first get the supplier
		int supplierId = placeOrder.getSupplierId();
		SupplierDto supplierById = supplierService.getSupplierById(supplierId);
		// map supplierDto--->supplier
		Supplier supplier = mapper.map(supplierById, Supplier.class);

		PlaceOrder newOrder = new PlaceOrder();
		newOrder.setProduct(placeOrder.getProduct());
		newOrder.setQuantity(placeOrder.getQuantity());
		newOrder.setSupplier(supplier);
		placeOrderRepo.save(newOrder);
		return msg;
	}

	@Override
	public String UpdatePrice(UpdatePriceDto updatePrice) {
		log.info("In admin service : UpdatePrice");
		String msg = "Unit price updated successfully";
		ProductDto productDto = prodService.getProductById(updatePrice.getProductId());
		Product product = mapper.map(productDto, Product.class);
		product.setUnitPrice(updatePrice.getUnitPrice());
		Product saveProduct = productdao.save(product);
		log.info(saveProduct.toString());
		return msg;
	}

}
