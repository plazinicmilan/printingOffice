package com.src.printing.office.po.model.finishedProduct.transfer;

import java.io.Serializable;

public class TransferFPResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String status;

	public TransferFPResponse(String status) {
		super();
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
