package com.app.service.Supplier;

import java.util.List;

import com.app.dto.DispatchOrderDTO;
import com.app.dto.SupplierDto;
import com.app.entities.PlaceOrder;

public interface ISupplierService {

	List<SupplierDto> getAllSupplier();

	SupplierDto getSupplierById(int supplier_Id);

	String deleteSupplierById(int supplier_Id);

	SupplierDto saveSupplier(SupplierDto suppDto);

	SupplierDto updateSupplier(SupplierDto updateSupplier);

	// method to check order placed by admin
	List<PlaceOrder> seePlacedOrders(int supplierId);

	// method to dispatch order placed by admin
	void dispatchOrder(DispatchOrderDTO dispatchOrder);

}
