package com.src.printing.office.po.model.deliveryNote.getOneForReceipt.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DNGetOneForReceiptResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<DNItemForReceipt> dnItems;

	public DNGetOneForReceiptResponse() {
		super();
		dnItems = new ArrayList<>();
	}

	public List<DNItemForReceipt> getDnItems() {
		return dnItems;
	}

	public void setDnItems(List<DNItemForReceipt> dnItems) {
		this.dnItems = dnItems;
	}

}
