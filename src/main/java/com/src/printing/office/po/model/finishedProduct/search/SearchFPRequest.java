package com.src.printing.office.po.model.finishedProduct.search;

import java.io.Serializable;

import com.src.printing.office.po.model.POGenericType;

public class SearchFPRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private POGenericType customer;

	public POGenericType getCustomer() {
		return customer;
	}

	public void setCustomer(POGenericType customer) {
		this.customer = customer;
	}

}
