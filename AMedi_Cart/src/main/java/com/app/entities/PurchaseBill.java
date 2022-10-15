package com.app.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;

@Entity
@Getter
@Table(name = "purchase_bill")
public class PurchaseBill extends BaseEntity {

	private double totalAmount;

	@ReadOnlyProperty
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate billDate;

	@OneToOne
	private Supplier supplier;

	@OneToOne
	private Product product;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate manfDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate expDate;

	@Column(length = 30)
	private String batchNo;

	private int quantity;

	private double unitPrice;

	public PurchaseBill(double totalAmount, Supplier supplier) {
		super();
		this.billDate = LocalDate.now();
		this.supplier = supplier;

	}

	public PurchaseBill() {
		super();
		this.billDate = LocalDate.now();

	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = (this.unitPrice * this.quantity);
	}

	public void setBillDate(LocalDate billDate) {
		this.billDate = billDate;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setManfDate(LocalDate manfDate) {
		this.manfDate = manfDate;
	}

	public void setExpDate(LocalDate expDate) {
		this.expDate = expDate;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

}
