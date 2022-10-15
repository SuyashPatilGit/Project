package com.app.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.app.dto.UserRole;

import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue(value = "deliveryBoy")
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "deliveryBoy")
@Entity
@Getter
@Setter
public class DeliveryBoy extends User {

	@Column(length = 40)
	private String licenseNO;

	// No-Agrs constructor
	public DeliveryBoy() {
		super();

	}

	// TO-string
	@Override
	public String toString() {
		return "DeliveryBoy [licenseNO=" + licenseNO + ", toString()=" + super.toString() + "]";
	}

	public DeliveryBoy(String email, String firstName, String lastName, String password, LocalDate dOB, String mobNo,
			LocalDate regDate, String city, String state, UserRole role) {
		super(email, firstName, lastName, password, dOB, mobNo, regDate, city, state, role);
		// TODO Auto-generated constructor stub
	}

}
