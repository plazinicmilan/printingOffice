package com.src.printing.office.exception;

public class ApiException extends Exception {

	private static final long serialVersionUID = 1L;

	private ApiError apiError;

	public ApiException(ApiError apiError) {
		super();
		this.apiError = apiError;
	}

	public ApiError getApiError() {
		return apiError;
	}

	public void setApiError(ApiError apiError) {
		this.apiError = apiError;
	}
	
}
