package com.src.printing.office.po.model.pom.save.request;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.src.printing.office.deserializer.CalendarDeserializer;
import com.src.printing.office.po.model.POGenericType;

public class POMSaveRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private long userID;
	@NotNull
	@JsonDeserialize(using = CalendarDeserializer.class)
	private Calendar date;
	private String pomNumber;
	@NotNull
	private POGenericType supplier;
	@NotNull
	private Double totalAmount;
	private List<POMItem> pomItems;

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

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
