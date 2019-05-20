package com.src.printing.office.po.model.customer.save.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.src.printing.office.po.model.POGenericType;

public class CustomerSaveRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	@NotBlank
	private String name;
	private String pib;
	private String pdv;
	private boolean customerType;
	private String contact;
	private String address;
	private String city;
	private POGenericType belongsTo;

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

	public boolean isCustomerType() {
		return customerType;
	}

	public void setCustomerType(boolean customerType) {
		this.customerType = customerType;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public POGenericType getBelongsTo() {
		return belongsTo;
	}

	public void setBelongsTo(POGenericType belongsTo) {
		this.belongsTo = belongsTo;
	}

}
