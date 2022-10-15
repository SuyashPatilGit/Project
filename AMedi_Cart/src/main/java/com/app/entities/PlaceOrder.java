package com.app.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PlaceOrder extends BaseEntity {

	private int quantity;

	@OneToOne
	private Product product;

	private String notes;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate orderDate;

	@Enumerated(EnumType.STRING)
	private PlaceOrderStatus status;

	@Enumerated(EnumType.STRING)
	@Column(name = "supplier_status")
	private SupplierStatus supplierStatus;

	@OneToOne
	private Supplier supplier;

	public PlaceOrder() {
		super();
		this.orderDate = LocalDate.now();
		this.status = PlaceOrderStatus.ORDER_PLACED;
		this.supplierStatus = SupplierStatus.PENDING;
	}

	public PlaceOrder(int quantity, Product product, String notes, Supplier supplier) {
		super();
		this.quantity = quantity;
		this.product = product;
		this.notes = notes;
		this.orderDate = LocalDate.now();
		this.status = PlaceOrderStatus.ORDER_PLACED;
		this.supplierStatus = SupplierStatus.PENDING;
		this.supplier = supplier;
	}

	@Override
	public String toString() {
		return "PlaceOrder [quantity=" + quantity + ", notes=" + notes + ", orderDate=" + orderDate + ", status="
				+ status + ", supplierStatus=" + supplierStatus + "]";
	}

}
