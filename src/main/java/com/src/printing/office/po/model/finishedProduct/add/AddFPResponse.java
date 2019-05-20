package com.src.printing.office.po.model.finishedProduct.add;

import java.io.Serializable;

public class AddFPResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String status;

	public AddFPResponse(String status) {
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
