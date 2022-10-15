package com.app.service.Order;

import java.util.List;

import com.app.custom_exceptions.OrderNotFoundException;
import com.app.entities.Address;
import com.app.entities.Orders;
import com.app.entities.PaymentType;
import com.app.entities.User;
import com.razorpay.RazorpayException;

public interface IOrderService {

	Orders getOrders(Integer orderId);

	void deleteOrder(Integer orderId) throws OrderNotFoundException;

	String createOrderByUserCart(int user, Address address, PaymentType paymentMethod) throws RazorpayException;

	List<Orders> getOrderList(int Userid);
	
	

}
