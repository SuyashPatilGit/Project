package com.app.dto;

import java.time.LocalDate;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SupplierDto {

	@JsonProperty(access = Access.READ_ONLY)
	private Integer id;

	@NotBlank
	@Email(message = "Invalid Email")
	private String email;

	@NotEmpty(message = "First name must be supplied")
	@Length(min = 4, max = 30, message = "Invalid First name length")
	private String firstName;

	@NotBlank(message = "Last name must be supplied")
	private String lastName;

	@JsonProperty(access = Access.WRITE_ONLY) // for de-serial only
	private String password;

	@Past(message = "Date of birth  msut be in past")
	private LocalDate DOB;

	@Digits(message = "Number should contain 10 digits.", fraction = 0, integer = 10)
	private String mobNo;

	private String city;

	private String state;

	@NotNull
	private UserRole role;

	@NotBlank(message = "GST number must be supplied")
	private String GSTNO;

}
