package com.src.printing.office.po.model.deliveryNote.save.response;

import java.io.Serializable;

public class DNSaveResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String dnNumber;

	public DNSaveResponse() {
		super();
	}

	public DNSaveResponse(long id, String dnNumber) {
		super();
		this.id = id;
		this.dnNumber = dnNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDnNumber() {
		return dnNumber;
	}

	public void setDnNumber(String dnNumber) {
		this.dnNumber = dnNumber;
	}

}
