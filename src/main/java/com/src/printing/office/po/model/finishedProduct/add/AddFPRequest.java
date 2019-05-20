package com.src.printing.office.po.model.finishedProduct.add;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class AddFPRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long woID;
	@NotNull
	private Integer quantity;
	private String fpName;

	public Long getWoID() {
		return woID;
	}

	public void setWoID(Long woID) {
		this.woID = woID;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getFpName() {
		return fpName;
	}

	public void setFpName(String fpName) {
		this.fpName = fpName;
	}

}
