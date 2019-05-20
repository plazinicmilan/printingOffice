package com.src.printing.office.po.model.finishedProduct.transfer;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class TransferFPRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long woID;
	@NotNull
	private Long fpID;

	public Long getWoID() {
		return woID;
	}

	public void setWoID(Long woID) {
		this.woID = woID;
	}

	public Long getFpID() {
		return fpID;
	}

	public void setFpID(Long fpID) {
		this.fpID = fpID;
	}

}
