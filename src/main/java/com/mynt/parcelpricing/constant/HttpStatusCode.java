package com.mynt.parcelpricing.constant;

import com.mynt.parcelpricing.exception.ApiRequestException;

public enum HttpStatusCode {
	OK(200), BAD_REQUEST(400), 
	UNAUTHORIZED(401), 
	FORBIDDEN(403), 
	SERVER_DOWN(404), 
	PRECONDITION_FAILED(412),
	RESOURCE_NOT_FOUND(444), 
	LOGIN_WARNING(461), 
	TEMP_LOCKED_OUT(462), 
	PERMANENT_LOCKED_OUT(463),
	CHANGE_PASSWORD_PREVPASS_USED(464), 
	SUSPENDED(465), 
	DISABLED(466), 
	INVALID_OTP(467), 
	EXPIRED_OTP(468),
	NO_NOTIFICATION_DETAILS(469), 
	NOTIFICATION_FAILURE(470), 
	USER_UNREGISTERED(472),
	USER_EXPIRED(473), 
	USER_ALREADY_REGISTERED(474), 
	INVALID_USERNAME(475), 
	INTERNAL_SERVER_ERROR(500);

	private int value;

	HttpStatusCode(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public static HttpStatusCode fromKey(int statusCode) throws ApiRequestException {
		for (HttpStatusCode httpStatusCode : values()) {
			if (httpStatusCode.value == statusCode) {
				return httpStatusCode;
			}
		}
		throw new ApiRequestException("Undefined Enum Value");
	}
}
