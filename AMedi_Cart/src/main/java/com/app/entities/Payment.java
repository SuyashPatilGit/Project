package com.app.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Payment extends BaseEntity {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate paymentDate;

	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;

	@OneToOne
	@JoinColumn(name = "order_id")
	private Orders order;

	@Override
	public String toString() {
		return "Payment [paymentDate=" + paymentDate + ", paymentStatus=" + paymentStatus + ", paymentTyep="
				+ paymentType + "]";
	}

}
