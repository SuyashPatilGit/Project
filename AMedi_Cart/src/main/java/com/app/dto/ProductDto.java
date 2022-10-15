package com.app.dto;

import javax.validation.constraints.NotEmpty;

import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

import com.app.entities.ProductCategory;
import com.app.entities.Rx;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDto extends JpaRepositoriesAutoConfiguration {

	@JsonProperty(access = Access.READ_ONLY)
	private Integer id;

	@NotEmpty(message = "Product name must be supplied")
	private String productName;

	@NotEmpty(message = "Product generic  name must be supplied")
	private String genericName;

	@NotEmpty(message = "Product company name must be supplied")
	private String companyName;

	private ProductCategory productCategory;

	private Rx rx;

	private String prodDetails;

	private String packgDetails;

	private double unitPrice;

	private int stock;

	private String imageUrl;
}
