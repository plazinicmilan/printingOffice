package com.src.printing.office.po.model.receipt.search;

import java.io.Serializable;
import java.util.Calendar;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.src.printing.office.serializer.CalendarSerializer;

public class SearchReceiptResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String receiptNumber;
	@JsonSerialize(using = CalendarSerializer.class)
	private Calendar dateReceipt;
	private String customer;
	private String jobTitle;
	private String user;
	private Double total;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public Calendar getDateReceipt() {
		return dateReceipt;
	}

	public void setDateReceipt(Calendar dateReceipt) {
		this.dateReceipt = dateReceipt;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
