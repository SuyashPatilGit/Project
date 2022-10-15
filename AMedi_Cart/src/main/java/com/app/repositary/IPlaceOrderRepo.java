package com.app.repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.PlaceOrder;
import com.app.entities.Supplier;

@Repository
public interface IPlaceOrderRepo extends JpaRepository<PlaceOrder, Integer> {

	List<PlaceOrder> findBySupplier(Supplier supplier);

}
