package com.src.printing.office.po.model.deliveryNote.getOne.response;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.serializer.CalendarSerializer;

public class DNGetOneResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	@JsonSerialize(using = CalendarSerializer.class)
	private Calendar date;
	private String dnNumber;
	private POGenericType customer;
	private String user;
	private String description;
	private List<DNItem> deliveryNoteItems;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getDnNumber() {
		return dnNumber;
	}

	public void setDnNumber(String dnNumber) {
		this.dnNumber = dnNumber;
	}

	public POGenericType getCustomer() {
		return customer;
	}

	public void setCustomer(POGenericType customer) {
		this.customer = customer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<DNItem> getDeliveryNoteItems() {
		return deliveryNoteItems;
	}

	public void setDeliveryNoteItems(List<DNItem> deliveryNoteItems) {
		this.deliveryNoteItems = deliveryNoteItems;
	}

}
