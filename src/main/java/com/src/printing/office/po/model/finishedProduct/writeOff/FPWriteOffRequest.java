package com.src.printing.office.po.model.finishedProduct.writeOff;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class FPWriteOffRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long userID;
	@NotNull
	private Long fpID;
	@NotNull
	private Integer quantity;
	@NotNull
	private String reason;

	public Long getFpID() {
		return fpID;
	}

	public void setFpID(Long fpID) {
		this.fpID = fpID;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
