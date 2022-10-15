package com.app.dto;

import com.app.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderDto {

	private int supplierId;

	private Product product;

	private String notes;

	private int quantity;
}
