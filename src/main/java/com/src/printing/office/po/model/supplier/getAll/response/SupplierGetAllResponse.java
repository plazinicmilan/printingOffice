package com.src.printing.office.po.model.supplier.getAll.response;

import java.io.Serializable;

public class SupplierGetAllResponse implements Serializable, Comparable<SupplierGetAllResponse> {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;

	public SupplierGetAllResponse(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(SupplierGetAllResponse o) {
		return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
	}

}
