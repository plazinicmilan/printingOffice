package com.src.printing.office.po.model.deliveryNote.update.request;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.src.printing.office.deserializer.CalendarDeserializer;
import com.src.printing.office.po.model.POGenericType;

public class DNUpdateRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long id;
	@NotNull
	@JsonDeserialize(using = CalendarDeserializer.class)
	private Calendar date;
	@NotNull
	private POGenericType customer;
	@NotNull
	private List<DNUpdateItem> deliveryNoteItems;
	@NotNull
	private Long userID;
	private boolean internal;
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public POGenericType getCustomer() {
		return customer;
	}

	public void setCustomer(POGenericType customer) {
		this.customer = customer;
	}

	public List<DNUpdateItem> getDeliveryNoteItems() {
		return deliveryNoteItems;
	}

	public void setDeliveryNoteItems(List<DNUpdateItem> deliveryNoteItems) {
		this.deliveryNoteItems = deliveryNoteItems;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
