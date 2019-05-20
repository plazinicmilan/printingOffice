package com.src.printing.office.po.model.supplier.search.response;

import java.io.Serializable;

public class SearchSupplierResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String pib;
	private String pdv;
	private Boolean supplierType;
	private String contact;
	private String address;

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

	public String getPib() {
		return pib;
	}

	public void setPib(String pib) {
		this.pib = pib;
	}

	public String getPdv() {
		return pdv;
	}

	public void setPdv(String pdv) {
		this.pdv = pdv;
	}

	public Boolean getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(Boolean supplierType) {
		this.supplierType = supplierType;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
