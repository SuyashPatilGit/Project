package com.app.service.admin;

import com.app.dto.PlaceOrderDto;
import com.app.dto.UpdatePriceDto;

public interface IAdminService {

	String placeOrder(PlaceOrderDto placeOrder);

	String UpdatePrice(UpdatePriceDto updatePrice);
}
