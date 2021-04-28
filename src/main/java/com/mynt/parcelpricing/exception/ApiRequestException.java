package com.mynt.parcelpricing.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiRequestException extends RuntimeException{

	private static final long serialVersionUID = 9220022645730647917L;
	private HttpStatus httpStatus;

	public ApiRequestException(String message) {
		super(message);
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public ApiRequestException(String message, HttpStatus httpStatusCode) {
		super(message);
		this.httpStatus = httpStatusCode;
	}

	public ApiRequestException(String message, Throwable throwable) {
		super(message, throwable);
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public ApiRequestException(String message, Throwable throwable, HttpStatus httpStatusCode) {
		super(message, throwable);
		this.httpStatus = httpStatusCode;
	}
}
