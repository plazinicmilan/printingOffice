package com.src.printing.office.exception;

import com.src.printing.office.enums.ApiErrorMessage;
import com.src.printing.office.enums.ApiErrorSeverity;

public class ApiError {

	private String errorCode;
	private String errorDesc;
	private ApiErrorSeverity severity;

	public ApiError(ApiErrorMessage message, String errorObject) {
		super();

		switch (message) {

		case NOT_FOUND:
			setErrorAtributes("-404", "Data not found for provided input: " + errorObject, ApiErrorSeverity.ERROR);
			break;
		case MANDATORY:
			setErrorAtributes("-300", "You have to provide mandatory input data: " + errorObject,
					ApiErrorSeverity.ERROR);
			break;
		case VALIDATION:
			setErrorAtributes("-300", errorObject, ApiErrorSeverity.ERROR);
			break;
		case EXISTS:
			setErrorAtributes("-310", errorObject + " already exists in database.", ApiErrorSeverity.ERROR);
			break;
		case FINISHED_PRODUCTS_NOT_ENOUGH:
			setErrorAtributes("-320", "There is not enough finished products in the stock: " + errorObject,
					ApiErrorSeverity.ERROR);
			break;
		case MATERIAL_NOT_ENOUGH:
			setErrorAtributes("-321", "There is not enough materials in the stock: " + errorObject,
					ApiErrorSeverity.ERROR);
			break;
		case RECEIPT_TYPE_DONT_EXISTS:
			setErrorAtributes("-330", "Receipt type number + " + errorObject + " can't be found in the database",
					ApiErrorSeverity.ERROR);
			break;
		case DEFAULT_ERROR:
			setErrorAtributes("-502", "Unexpected error happened: " + errorObject, ApiErrorSeverity.ERROR);
			break;
		default:
			break;
		}
	}

	private void setErrorAtributes(String errorCode, String errorDesc, ApiErrorSeverity errorSeverity) {
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
		this.severity = errorSeverity;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public ApiErrorSeverity getSeverity() {
		return severity;
	}

	public void setSeverity(ApiErrorSeverity severity) {
		this.severity = severity;
	}

}
