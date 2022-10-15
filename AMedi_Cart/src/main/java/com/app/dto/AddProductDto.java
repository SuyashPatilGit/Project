package com.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddProductDto {
	private Integer productId;
	private Integer customerId;
	private Integer quantity;
}
