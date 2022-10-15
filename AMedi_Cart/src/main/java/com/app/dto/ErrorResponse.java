package com.app.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ErrorResponse {

	private String message;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;

	/*
	 * private int code;
	 * 
	 * private String status;
	 */

	/*
	 * public ErrorResponse(String message, LocalDateTime timestamp) { super();
	 * this.message = message; this.timestamp = timestamp; }
	 * 
	 * public ErrorResponse(String message, LocalDateTime timestamp, String status)
	 * { super(); this.message = message; this.timestamp = timestamp; this.status =
	 * status; }
	 */

}
