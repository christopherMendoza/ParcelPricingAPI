package com.mynt.parcelpricing.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

public abstract class ErrorDetails {

}

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
class ApiValidationError extends ErrorDetails {
	private String field;
	private Object rejectedValue;
	private String message;

	ApiValidationError(String message) {
		this.message = message;
	}
}
