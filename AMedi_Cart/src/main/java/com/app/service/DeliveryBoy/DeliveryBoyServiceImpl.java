package com.app.service.DeliveryBoy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.OrderNotFoundException;
import com.app.custom_exceptions.UserHandlingException;
import com.app.dto.ChangeOrderStatusDto;
import com.app.dto.DeliveryBoyDto;
import com.app.entities.DeliveryBoy;
import com.app.entities.Orders;
import com.app.entities.User;
import com.app.repositary.IDeliveryBoyRepositary;
import com.app.service.Order.IOrderService;
import com.app.service.emailServices.IEmailSendingService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class DeliveryBoyServiceImpl implements IDeliveryBoyService {

	// Dependency injection
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private IDeliveryBoyRepositary deliveryBoyDao;

	// password encoder
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private IEmailSendingService emailService;

	@Autowired
	private IOrderService orderService;

	Random random = new Random(1000);

	@Override
	public List<DeliveryBoyDto> getAllDeliveryBoy() {
		log.info("In DeliveryBoy service implimentation : getAllDeliveryBoy ");
		List<DeliveryBoy> deliveryBoy = deliveryBoyDao.findAll();
		List<DeliveryBoyDto> listDB = new ArrayList<DeliveryBoyDto>();
		for (DeliveryBoy dboy : deliveryBoy) {
			listDB.add(mapper.map(dboy, DeliveryBoyDto.class));
		}
		return listDB;
	}

	@Override
	public DeliveryBoyDto getDeliveryBoyById(int deliveryBoy_Id) {
		log.info("In DeliveryBoy service implimentation : getDeliveryBoyById ");
		if (deliveryBoyDao.existsById(deliveryBoy_Id)) {
			Optional<DeliveryBoy> deliveryBoy = deliveryBoyDao.findById(deliveryBoy_Id);
			return mapper.map(deliveryBoy, DeliveryBoyDto.class);
		} else
			throw new UserHandlingException("invalid DeliveryBoy ID", HttpStatus.FORBIDDEN);
	}

	@Override
	public String deleteDeliveryBoyDetails(int id) {
		log.info("In DeliveryBoy service implimentation : deleteDeliveryBoyDetails ");
		String mesg = "Deletion of DeliveryBoy details failed!!!!!!!!!!!";

		if (deliveryBoyDao.existsById(id)) {
			deliveryBoyDao.deleteById(id);
			mesg = "DeliveryBoy details deleted successfully , for Customer id :" + id;
		}

		return mesg;
	}

	@Override
	public DeliveryBoyDto updateDeliveryBoy(DeliveryBoyDto updateDBoy) {
		log.info("In DeliveryBoy service implimentation : updateDeliveryBoy ");
		DeliveryBoy deliveryBoy = deliveryBoyDao.findDeliveryBoyByEmail(updateDBoy.getEmail());

		if (deliveryBoy != null) {
			deliveryBoy.setEmail(updateDBoy.getEmail());
			deliveryBoy.setFirstName(updateDBoy.getFirstName());
			deliveryBoy.setLastName(updateDBoy.getLastName());
			deliveryBoy.setDOB(updateDBoy.getDOB());
			deliveryBoy.setMobNo(updateDBoy.getMobNo());
			deliveryBoy.setState(updateDBoy.getState());
			deliveryBoy.setCity(updateDBoy.getCity());
			DeliveryBoy persistent = deliveryBoyDao.save(deliveryBoy);
			log.info("updated Customer" + deliveryBoy.toString());
			return mapper.map(persistent, DeliveryBoyDto.class);

		} else
			// we have to throw an new exception for invalid email id
			throw new UserHandlingException("invalid Customer Details ", HttpStatus.FORBIDDEN);
	}

	@Override
	public boolean updatePassword(String email, String oldPassword, String newPassword) {
		log.info("In DeliveryBoy service implimentation : updatePassword ");
		DeliveryBoyDto deliveryBoy = authenticateDeliveryBoy(email, oldPassword);
		if (deliveryBoy != null) {
			log.info(deliveryBoy.toString());
			deliveryBoy.setPassword(encoder.encode(newPassword));
			return true;
		}
		return false;
	}

	@Override
	public DeliveryBoyDto saveDeliveryBoy(DeliveryBoyDto deliveryBoyDto) {
		log.info("In DeliveryBoy service implimentation : saveDeliveryBoy ");
		// map dto --> entity
		DeliveryBoy deliveryBoy = mapper.map(deliveryBoyDto, DeliveryBoy.class);
		deliveryBoy.setPassword(encoder.encode(deliveryBoy.getPassword()));
		DeliveryBoy persistent = deliveryBoyDao.save(deliveryBoy);

		// map entity --> dto
		return mapper.map(persistent, DeliveryBoyDto.class);
	}

	@Override
	public DeliveryBoyDto authenticateDeliveryBoy(String email, String pass) {
		log.info("In DeliveryBoy service implimentation : authenticateDeliveryBoy ");
		DeliveryBoy deliveryBoy = deliveryBoyDao.findByEmailAndPassword(email, pass)
				.orElseThrow(() -> new RuntimeException("Invalid email or password"));

		return mapper.map(deliveryBoy, DeliveryBoyDto.class);
	}

	// method to change order status ==SHIPPED
	@Override
	public ChangeOrderStatusDto changeStatusShipped(int orderId) throws OrderNotFoundException {
		log.info("In delivery boy service implimentation: changeStatusShipped ");
		Orders orders = orderService.getOrders(orderId);
		User user = orders.getUser();
		int OTP = random.nextInt(9999);
		// create subject and message for email
		String subject = "Delivery verification OTP from medikart";
		String msg = "<h1> Your order is shipped , Delivery  OTP is:" + OTP + " kindly share this  OTP"
				+ "with Delivery boy " + orders.getDeliverBoy().getFirstName() + orders.getDeliverBoy().getLastName()
				+ " </h1>";
		emailService.sendEmail(user.getEmail(), msg, subject);
		ChangeOrderStatusDto order = new ChangeOrderStatusDto();
		order.setOrder(orders);
		order.setOTP(OTP);
		log.info(order.toString());
		return order;
	}

}
