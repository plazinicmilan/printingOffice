package com.src.printing.office.po.model.pom.search.response;

import java.io.Serializable;
import java.util.Calendar;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.src.printing.office.serializer.CalendarSerializer;

public class SearchPOMResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String supplier;
	@JsonSerialize(using = CalendarSerializer.class)
	private Calendar datePOM;
	private String pomNumber;
	private Double totalAmount;
	private String user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public Calendar getDatePOM() {
		return datePOM;
	}

	public void setDatePOM(Calendar datePOM) {
		this.datePOM = datePOM;
	}

	public String getPomNumber() {
		return pomNumber;
	}

	public void setPomNumber(String pomNumber) {
		this.pomNumber = pomNumber;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
