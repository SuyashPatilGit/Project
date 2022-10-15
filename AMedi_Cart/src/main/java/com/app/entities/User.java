package com.app.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.format.annotation.DateTimeFormat;

import com.app.dto.UserRole;

import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
@Table(name = "user")
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "user_id")
	private Integer id;

	@Column(length = 40, unique = true)
	private String email;

	@Column(length = 40, name = "first_name")
	private String firstName;

	@Column(length = 40, name = "last_name")
	private String lastName;

	@Column(length = 350)
	private String password;

	@Column(name = "Date_Of_Birth")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate DOB;

	@Column(name = "mobile_no", unique = true)
	private String mobNo;

	@ReadOnlyProperty
	@Column(name = "Reg_Date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate regDate;

	@Column(length = 50)
	private String city;

	@Column(length = 40)
	private String state;

	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Column(length = 400)
	private String imagePath;

	// No-agrs constructor
	public User() {
		super();
		this.regDate = LocalDate.now();
	}

	

	public User(String email, String firstName, String lastName, String password, LocalDate dOB, String mobNo,
			LocalDate regDate, String city, String state, UserRole role) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		DOB = dOB;
		this.mobNo = mobNo;
		this.regDate = regDate;
		this.city = city;
		this.state = state;
		this.role = role;
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", DOB="
				+ DOB + ", mobNo=" + mobNo + ", regDate=" + regDate + ", city=" + city + ", state=" + state + ", role="
				+ role + "]";
	}

}
