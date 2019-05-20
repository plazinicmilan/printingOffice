package com.src.printing.office.po.model.customer.search.request;

import java.io.Serializable;

public class SearchCustomerRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
