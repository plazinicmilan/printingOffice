package com.src.printing.office.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.src.printing.office.enums.ApiErrorMessage;


@ControllerAdvice
public class RestErrorHandler {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiError processValidationError(MethodArgumentNotValidException ex) {

		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();

		return processFieldErrors(fieldErrors.get(0));
	}

	private ApiError processFieldErrors(FieldError fieldError) {

		String errorMsg = "Atribute " + fieldError.getDefaultMessage() + ": " + fieldError.getField();

		ApiError error = new ApiError(ApiErrorMessage.VALIDATION, errorMsg);

		return error;
	}

}
