package com.src.printing.office.po.model.workOrder.getLastNumber;

import java.io.Serializable;

public class WorkOrderGetLastNumberResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String woNumber;

	public WorkOrderGetLastNumberResponse(String woNumber) {
		super();
		this.woNumber = woNumber;
	}

	public String getWoNumber() {
		return woNumber;
	}

	public void setWoNumber(String woNumber) {
		this.woNumber = woNumber;
	}

}
