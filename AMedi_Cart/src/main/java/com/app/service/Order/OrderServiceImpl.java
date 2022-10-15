package com.app.service.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.OrderNotFoundException;
import com.app.custom_exceptions.UserHandlingException;
import com.app.dto.UserDto;
import com.app.entities.Address;
import com.app.entities.CartItem;
import com.app.entities.OrderDetails;
import com.app.entities.OrderStatus;
import com.app.entities.Orders;
import com.app.entities.Payment;
import com.app.entities.PaymentStatus;
import com.app.entities.PaymentType;
import com.app.entities.Product;
import com.app.entities.User;
import com.app.repositary.CartItemRepository;
import com.app.repositary.IOrederRepository;
import com.app.repositary.IPaymentRepositary;
import com.app.service.user.IUserService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private IOrederRepository orderRepo;

	@Autowired
	private CartItemRepository cartRepo;

	@Autowired
	private IUserService userService;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private IPaymentRepositary paymentRepo;

	// Method to find order By order id

	@Override
	public Orders getOrders(Integer id) {
		log.info("In order Service method : get order by id ");
		return orderRepo.findById(id).get();
	}

	// Method to delete order BY order ID
	@Override
	public void deleteOrder(Integer id) throws OrderNotFoundException {

		log.info("In Order service implimentation : delete Order By Id method");
		Long count = orderRepo.countById(id);
		log.info("Count of order: " + count);
		if (count == null || count == 0) {
			throw new OrderNotFoundException("Could not find any orders with ID " + id);
		}

		orderRepo.deleteById(id);
	}

	// Method to place order for a specified customer by his cart items

	@Override
	public String createOrderByUserCart(int userId, Address address, PaymentType paymentMethod)
			throws RazorpayException {
		log.info("In Order service implimentation : createOrder By Customer cart");
		Orders newOrder = new Orders();
		UserDto cust = userService.getUserById(userId);
		User customer = mapper.map(cust, User.class);
		List<CartItem> cartItems = cartRepo.findByUser(customer);
		if (paymentMethod.equals(PaymentType.ONLINE)) {
			newOrder.setOrderStatus(OrderStatus.PROCESSING);
		} else {
			newOrder.setOrderStatus(OrderStatus.PROCESSING);
		}
		newOrder.setUser(customer);
		newOrder.setDeliveryAddress(address);
		Set<OrderDetails> orderDetails = newOrder.getOrderDetails();
		double totalAmount = 0;
		// place order for products in cart
		for (CartItem cartItem : cartItems) {
			Product product = cartItem.getProduct();
			// Decrease the stock of product
			decreaseStock(product, cartItem.getQuantity());
			OrderDetails orderDetail = new OrderDetails();
			orderDetail.setOrder(newOrder);
			orderDetail.setProduct(product);
			orderDetail.setProductName(product.getProductName());
			orderDetail.setQuantity(cartItem.getQuantity());
			orderDetail.setUnitPrice(product.getUnitPrice());
			orderDetail.setSubtotal(product.getUnitPrice() * cartItem.getQuantity());
			totalAmount = totalAmount + orderDetail.getSubtotal();
			orderDetails.add(orderDetail);

			// now delete the entires from cart
			cartRepo.deleteByUserAndProduct(customer.getId(), product.getId());
		}

		// set payment details
		Payment payment = new Payment();
		payment.setOrder(newOrder);
		payment.setPaymentStatus(PaymentStatus.PAID);
		payment.setPaymentType(paymentMethod.ONLINE);
		payment.setPaymentDate(LocalDate.now());
		paymentRepo.save(payment);
		log.info(payment.toString());
		double amt = totalAmount;

		// set razor pay details
		var client = new RazorpayClient("rzp_test_DIePDruxfVbZye", "IOfRw9z3m2HRtNthiqMZ7ruN");
		Random rnd = new Random();
		int txn = rnd.nextInt(999999999);

		JSONObject ob = new JSONObject();
		ob.put("amount", amt * 100);
		ob.put("currency", "INR");
		ob.put("receipt", "txn_" + txn);

		com.razorpay.Order rz_order = client.Orders.create(ob);
		System.out.println(rz_order);

		String id = rz_order.get("id");
		newOrder.setRazorPayOrderId(id);
		newOrder.setTotalAmount(totalAmount);
		orderRepo.save(newOrder);
		return rz_order.toString();

	}

	// Method to return order

	public void returnOrder(Integer orderId, User customer) throws OrderNotFoundException {
		log.info("In order service implimentaion : returnOrder");
		Orders order = orderRepo.findByIdAndUser(orderId, customer);
		if (order == null || order.getOrderStatus() == OrderStatus.RETURN_REQUESTED) {
			throw new OrderNotFoundException("Order not found or order already request to return");
		} else {
			order.setOrderStatus(OrderStatus.RETURN_REQUESTED);
		}
	}

	// Method to decrease the product stock
	public void decreaseStock(Product product, int quantity) {
		log.info("In order service implimentaion : decearese stock method");
		int oldQuantity = product.getStock();
		if (oldQuantity > 0 && oldQuantity > quantity) {
			int updatedQuantity = (oldQuantity - quantity);
			product.setStock(updatedQuantity);
			log.info("After placed order : " + product);
		} else {
			throw new UserHandlingException(
					"Out of stock .........for product" + product.getProductName() + "availble stock : " + oldQuantity);
		}
	}

	// Method to increase the product stock after Returning product
	public void increaseStock(Product product, int quantity) {
		log.info("In order service implimentaion : increase stock method");
		int oldQuantity = product.getStock();
		int updatedQuantity = (oldQuantity + quantity);
		product.setStock(updatedQuantity);
		log.info("After placed order : " + product);
	}

	@Override
	public List<Orders> getOrderList(int Userid) {
		log.info("In order service implimentaion : get list of orders by userID");
		UserDto existUser = userService.getUserById(Userid);
		return orderRepo.findByUser(mapper.map(existUser, User.class));
	}
}
