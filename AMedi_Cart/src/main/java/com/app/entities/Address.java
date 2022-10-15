package com.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Address")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseEntity {

	@Column(length = 10)
	private int pinCode;

	@Column(length = 30)
	private String city;

	@Column(length = 50)
	private String state;

	@Column(length = 100)
	private String landMark;

	@Column(length = 200)
	private String addressLine;

	@Override
	public String toString() {
		return "Address [pinCode=" + pinCode + ", city=" + city + ", state=" + state + ", landMark=" + landMark
				+ ", addressLine=" + addressLine + "]";
	}

}
