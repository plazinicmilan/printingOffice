package com.src.printing.office.po.model.customer.update.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.src.printing.office.po.model.POGenericType;

public class CustomerUpdateRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long id;
	private String name;
	private String pib;
	private String pdv;
	private boolean customerType;
	private String contact;
	private String address;
	private String city;
	private POGenericType belongsTo;
	private Boolean inactive;

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

	public Boolean getInactive() {
		return inactive;
	}

	public void setInactive(Boolean inactive) {
		this.inactive = inactive;
	}

}
