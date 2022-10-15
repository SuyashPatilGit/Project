package com.app.repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.OrderDetails;
import com.app.entities.Orders;

@Repository
public interface IOrderItemRepositary extends JpaRepository<OrderDetails, Integer> {

	List<OrderDetails> getByOrder(Orders orders);
	/*
	 * // Find list of order item by Customer order id List<OrderItems>
	 * findAllByCustomerOrderId(Integer CustomerOderId);
	 */
}
