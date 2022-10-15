package com.app.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

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

	// for de-serial only
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@Past(message = "Date of birth  must be in past")
	private LocalDate DOB;

	@Digits(message = "Number should contain 10 digits.", fraction = 0, integer = 10)
	private String mobNo;

	private String city;

	private String state;

	@NotEmpty(message = "at least 1 role should be chosen")
	private Set<UserRole> roles = new HashSet<>();

}
