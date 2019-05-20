package com.src.printing.office.po.model.order.search;

import java.io.Serializable;
import java.util.Calendar;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.src.printing.office.serializer.CalendarSerializer;

public class OrderSearchResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String user;
	private String acceptedUser;
	@JsonSerialize(using = CalendarSerializer.class)
	private Calendar date;
	private String customer;
	private String jobTitle;
	private Integer circulation;
	private String description;

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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Integer getCirculation() {
		return circulation;
	}

	public void setCirculation(Integer circulation) {
		this.circulation = circulation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAcceptedUser() {
		return acceptedUser;
	}

	public void setAcceptedUser(String acceptedUser) {
		this.acceptedUser = acceptedUser;
	}

}
