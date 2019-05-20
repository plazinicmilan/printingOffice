package com.src.printing.office.po.model.customer.getAll.response;

import java.io.Serializable;

import com.src.printing.office.po.model.POGenericType;

public class CustomerGetAllActiveResponse implements Serializable, Comparable<CustomerGetAllActiveResponse> {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private POGenericType customerReceipt;

	public CustomerGetAllActiveResponse() {
		super();
	}

	public CustomerGetAllActiveResponse(long id, String name, POGenericType customerReceipt) {
		super();
		this.id = id;
		this.name = name;
		this.customerReceipt = customerReceipt;
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

	public POGenericType getCustomerReceipt() {
		return customerReceipt;
	}

	public void setCustomerReceipt(POGenericType customerReceipt) {
		this.customerReceipt = customerReceipt;
	}

	@Override
	public int compareTo(CustomerGetAllActiveResponse cr) {
		return this.name.toLowerCase().compareTo(cr.getName().toLowerCase());
	}

}
