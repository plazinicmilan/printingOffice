package com.src.printing.office.po.model.receipt.search;

import java.io.Serializable;
import java.util.Calendar;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.src.printing.office.deserializer.CalendarDeserializer;
import com.src.printing.office.po.model.POGenericType;

public class SearchReceiptRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonDeserialize(using = CalendarDeserializer.class)
	private Calendar dateFrom;
	@JsonDeserialize(using = CalendarDeserializer.class)
	private Calendar dateTo;
	private POGenericType customer;
	private String receiptNumber;
	private String type;

	public Calendar getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Calendar dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Calendar getDateTo() {
		return dateTo;
	}

	public void setDateTo(Calendar dateTo) {
		this.dateTo = dateTo;
	}

	public POGenericType getCustomer() {
		return customer;
	}

	public void setCustomer(POGenericType customer) {
		this.customer = customer;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
