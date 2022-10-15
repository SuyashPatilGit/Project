package com.app.service.Supplier;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.UserHandlingException;
import com.app.dto.DispatchOrderDTO;
import com.app.dto.ProductDto;
import com.app.dto.SupplierDto;
import com.app.entities.PlaceOrder;
import com.app.entities.Product;
import com.app.entities.PurchaseBill;
import com.app.entities.Supplier;
import com.app.entities.SupplierStatus;
import com.app.repositary.IPlaceOrderRepo;
import com.app.repositary.IPurchaseBillRepo;
import com.app.repositary.ISupplierRepositary;
import com.app.service.Product.IProductService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SupplierServiceImpl implements ISupplierService {

	// Dependency injection
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ISupplierRepositary supplierDao;

	// password encoder
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private IPlaceOrderRepo placeOrderRepo;

	@Autowired
	private IPurchaseBillRepo pucBillRepo;

	@Autowired
	private IProductService prodService;

	// Add a method to get list of supplier

	@Override
	public List<SupplierDto> getAllSupplier() {
		log.info("In Supplier service implimentation : getAllSupplier ");
		List<Supplier> supplierList = supplierDao.findAll();
		List<SupplierDto> supplier = new ArrayList<SupplierDto>();
		for (Supplier supp : supplierList) {
			supplier.add(mapper.map(supp, SupplierDto.class));
		}
		return supplier;
	}

	// add a method to get supplier by Supplier id

	@Override
	public SupplierDto getSupplierById(int Supplier_Id) {
		log.info("In Supplier service implimentation : getSupplierById ");
		Supplier supplier = supplierDao.findById(Supplier_Id)
				.orElseThrow(() -> new UserHandlingException("Invalid supplier Id"));
		return mapper.map(supplier, SupplierDto.class);
	}

	@Override
	public String deleteSupplierById(int supplier_Id) {
		log.info("In Supplier service implimentation : deleteCustomerById ");
		String mesg = "Deletion of Customer details failed!!!!!!!!!!!";

		if (supplierDao.existsById(supplier_Id)) {
			supplierDao.deleteById(supplier_Id);
			mesg = "Supplier details deleted successfully , for Supplier id :" + supplier_Id;
		}

		return mesg;
	}

	// Add a method to update existing supplier
	@Override
	public SupplierDto updateSupplier(SupplierDto updateSupplier) {
		log.info("In Supplier service implimentation : updateSupplier ");

		Supplier supplier = supplierDao.findSupplierByEmail(updateSupplier.getEmail());

		if (supplier != null) {
			supplier.setEmail(updateSupplier.getEmail());
			supplier.setFirstName(updateSupplier.getFirstName());
			supplier.setLastName(updateSupplier.getLastName());
			supplier.setDOB(updateSupplier.getDOB());
			supplier.setMobNo(updateSupplier.getMobNo());
			supplier.setState(updateSupplier.getState());
			supplier.setCity(updateSupplier.getCity());
			Supplier persistent = supplierDao.save(supplier);
			log.info("updated Supplier" + supplier.toString());
			return mapper.map(persistent, SupplierDto.class);

		} else
			// we have to throw an new exception for invalid email id
			throw new UserHandlingException("invalid Supplier Details ");
		// return saveSupplier(updateSupplier);
	}

	// to save supplier
	@Override
	public SupplierDto saveSupplier(SupplierDto suppDto) {
		log.info("In Supplier service implimentation : Save supplier ");
		// map dto --> entity

		Supplier supplier = mapper.map(suppDto, Supplier.class);
		supplier.setPassword(encoder.encode(supplier.getPassword()));
		Supplier persistent = supplierDao.save(supplier);

		// map entity --> dto
		return mapper.map(persistent, SupplierDto.class);
	}

	// method to List order placed by admin
	@Override
	public List<PlaceOrder> seePlacedOrders(int supplierId) {
		log.info("In Supplier service implimentation :see Placed Orders By admin");
		Supplier supplier = supplierDao.findById(supplierId)
				.orElseThrow(() -> new UserHandlingException("supplier Not found ....!!"));
		List<PlaceOrder> findBySupplier = placeOrderRepo.findBySupplier(supplier);
		return findBySupplier;

	}

	// Add a method to dispatch the orders placed by admin to supplier
	@Override
	public void dispatchOrder(DispatchOrderDTO dispatchOrder) {
		log.info("In Supplier service implimentation :dispatch Order Placed By admin");
		SupplierDto supplier = getSupplierById(dispatchOrder.getSupplierId());
		ProductDto product = prodService.getProductById(dispatchOrder.getProductId());
		PlaceOrder placeOrder = placeOrderRepo.findById(dispatchOrder.getOrderId())
				.orElseThrow(() -> new UserHandlingException("Invalid place order id"));
		// set values in purchase BILL
		PurchaseBill puchaseBill = new PurchaseBill();
		puchaseBill.setProduct(mapper.map(product, Product.class));
		puchaseBill.setSupplier(mapper.map(supplier, Supplier.class));
		puchaseBill.setManfDate(dispatchOrder.getManfDate());
		puchaseBill.setExpDate(dispatchOrder.getExpDate());
		puchaseBill.setQuantity(dispatchOrder.getQuantity());
		puchaseBill.setUnitPrice(dispatchOrder.getUnitPrice());
		puchaseBill.setUnitPrice(dispatchOrder.getUnitPrice());
		pucBillRepo.save(puchaseBill);
		placeOrder.setSupplierStatus(SupplierStatus.DISPATCHED);
		placeOrderRepo.save(placeOrder);

	}

}
