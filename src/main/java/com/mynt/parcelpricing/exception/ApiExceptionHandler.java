package com.mynt.parcelpricing.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ApiExceptionHandler {

	// custom exception handling exceptions
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<?> customValidationException(MethodArgumentNotValidException maEx) {
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Validation Error");
		List<ErrorDetails> subErrors =  new ArrayList<>();
		
		for(FieldError err: maEx.getBindingResult().getFieldErrors()) {
			subErrors.add(new ApiValidationError(err.getField(),
					err.getRejectedValue(), 
					err.getDefaultMessage()));
		}
		
		error.setSubErrors(subErrors);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}
	
	// handle specific exceptions
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(ApiRequestException.class)
	ResponseEntity<?> handleSpecificException(ApiRequestException apiEx, WebRequest req) {
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST, apiEx.getMessage());
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);

	}

	// handle global exceptions
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(Exception.class)
	ResponseEntity<?> handleGlobalException(Exception ex, WebRequest req) {
		ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
