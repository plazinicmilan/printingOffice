package com.src.printing.office.po.model.finishedProduct.getSelected.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.src.printing.office.po.model.POGenericType;

public class FPGetSelectedResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private POGenericType customer;
	private List<FPItem> fpItems;

	public FPGetSelectedResponse() {
		super();
		fpItems = new ArrayList<>();
	}

	public POGenericType getCustomer() {
		return customer;
	}

	public void setCustomer(POGenericType customer) {
		this.customer = customer;
	}

	public List<FPItem> getFpItems() {
		return fpItems;
	}

	public void setFpItems(List<FPItem> fpItems) {
		this.fpItems = fpItems;
	}

}
