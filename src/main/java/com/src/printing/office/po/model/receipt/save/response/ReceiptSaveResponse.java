package com.src.printing.office.po.model.receipt.save.response;

import java.io.Serializable;

public class ReceiptSaveResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long receiptID;
	private String receiptNumber;

	public ReceiptSaveResponse() {
		super();
	}

	public ReceiptSaveResponse(long receiptID, String receiptNumber) {
		super();
		this.receiptID = receiptID;
		this.receiptNumber = receiptNumber;
	}

	public long getReceiptID() {
		return receiptID;
	}

	public void setReceiptID(long receiptID) {
		this.receiptID = receiptID;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

}
