package com.src.printing.office.po.model.pom.getOne.response;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.serializer.CalendarSerializer;

public class POMGetOneResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonSerialize(using = CalendarSerializer.class)
	private Calendar date;
	private String pomNumber;
	private POGenericType supplier;
	private Double totalAmount;
	private List<POMItem> pomItems;

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getPomNumber() {
		return pomNumber;
	}

	public void setPomNumber(String pomNumber) {
		this.pomNumber = pomNumber;
	}

	public POGenericType getSupplier() {
		return supplier;
	}

	public void setSupplier(POGenericType supplier) {
		this.supplier = supplier;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<POMItem> getPomItems() {
		return pomItems;
	}

	public void setPomItems(List<POMItem> pomItems) {
		this.pomItems = pomItems;
	}

}
