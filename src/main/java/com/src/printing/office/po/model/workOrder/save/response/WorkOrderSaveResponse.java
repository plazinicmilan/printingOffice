package com.src.printing.office.po.model.workOrder.save.response;

import java.io.Serializable;

public class WorkOrderSaveResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String woNumber;

	public WorkOrderSaveResponse(long id, String woNumber) {
		super();
		this.id = id;
		this.woNumber = woNumber;
	}

	public String getWoNumber() {
		return woNumber;
	}

	public void setWoNumber(String woNumber) {
		this.woNumber = woNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
