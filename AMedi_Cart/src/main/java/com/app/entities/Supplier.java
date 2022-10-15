package com.app.entities;

import java.time.LocalDate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.app.dto.UserRole;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "supplier")
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "supplier")
public class Supplier extends User {

	private String GSTNO;

	public Supplier() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Supplier [GSTNO=" + GSTNO + ", toString()=" + super.toString() + "]";
	}

	public Supplier(String email, String firstName, String lastName, String password, LocalDate dOB, String mobNo,
			LocalDate regDate, String city, String state, UserRole role) {
		super(email, firstName, lastName, password, dOB, mobNo, regDate, city, state, role);
		// TODO Auto-generated constructor stub
	}

}
