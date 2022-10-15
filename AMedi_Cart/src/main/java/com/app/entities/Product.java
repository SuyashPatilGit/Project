package com.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product extends BaseEntity {

	@Column(length = 50, unique = true)
	private String productName;

	@Column(length = 50)
	private String genericName;

	@Column(length = 40)
	private String companyName;

	@Column(length = 500)
	private String prodDetails;

	@Column(length = 150)
	private String imageUrl;

	@Column(length = 500)
	private String packgDetails;

	@Enumerated(EnumType.STRING)
	private ProductCategory productCategory;

	// prescription needed or not
	@Enumerated(EnumType.STRING)
	private Rx rx;

	// private byte[] prodImage;
	private double unitPrice;

	// @Min(value = 1)
	private int stock;

	public Product(Integer productId) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Product [productName=" + productName + ", genericName=" + genericName + ", companyName=" + companyName
				+ ", prodDetails=" + prodDetails + ", imageUrl=" + imageUrl + ", packgDetails=" + packgDetails
				+ ", productCategory=" + productCategory + ", rx=" + rx + ", unitPrice=" + unitPrice + ", stock="
				+ stock + "]";
	}

}
