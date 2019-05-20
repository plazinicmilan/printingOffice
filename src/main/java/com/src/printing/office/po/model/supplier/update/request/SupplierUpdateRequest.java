package com.src.printing.office.po.model.supplier.update.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class SupplierUpdateRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long id;
	private String name;
	private boolean supplierType;
	private String pib;
	private String pdv;
	private String contact;
	private String address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSupplierType() {
		return supplierType;
	}

	public void setSupplierType(boolean supplierType) {
		this.supplierType = supplierType;
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
