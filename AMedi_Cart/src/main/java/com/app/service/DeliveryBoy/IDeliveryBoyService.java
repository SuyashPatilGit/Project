package com.app.service.DeliveryBoy;

import java.util.List;

import com.app.custom_exceptions.OrderNotFoundException;
import com.app.dto.ChangeOrderStatusDto;
import com.app.dto.DeliveryBoyDto;

public interface IDeliveryBoyService {

	List<DeliveryBoyDto> getAllDeliveryBoy();

	DeliveryBoyDto getDeliveryBoyById(int deliveryBoy_Id);

	String deleteDeliveryBoyDetails(int id);

	DeliveryBoyDto updateDeliveryBoy(DeliveryBoyDto deliveryBoy);

	boolean updatePassword(String email, String oldPassword, String newPassword);

	DeliveryBoyDto saveDeliveryBoy(DeliveryBoyDto deliveryBoy);

	DeliveryBoyDto authenticateDeliveryBoy(String email, String pass);

	ChangeOrderStatusDto changeStatusShipped(int orderId) throws OrderNotFoundException;
}
