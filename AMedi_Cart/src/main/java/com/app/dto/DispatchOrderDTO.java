package com.app.dto;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.Past;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DispatchOrderDTO {

	private int supplierId;

	private int orderId;

	private int productId;

	private int quantity;

	@Past(message = "Manufacturing date must be in past")
	private LocalDate manfDate;

	@Future(message = "Expiration date must be in future")
	private LocalDate expDate;

	private int unitPrice;

	private String batchNo;
}
