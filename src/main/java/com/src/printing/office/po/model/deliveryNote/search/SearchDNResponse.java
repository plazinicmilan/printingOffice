package com.src.printing.office.po.model.deliveryNote.search;

import java.io.Serializable;
import java.util.Calendar;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.src.printing.office.serializer.CalendarSerializer;

public class SearchDNResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String dnNumber;
	@JsonSerialize(using = CalendarSerializer.class)
	private Calendar dateDN;
	private String jobTitle;
	private String customer;
	private String user;
	private Boolean receipted;
	private Boolean internal;
	private String finishUser;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDnNumber() {
		return dnNumber;
	}

	public void setDnNumber(String dnNumber) {
		this.dnNumber = dnNumber;
	}

	public Calendar getDateDN() {
		return dateDN;
	}

	public void setDateDN(Calendar dateDN) {
		this.dateDN = dateDN;
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Boolean getReceipted() {
		return receipted;
	}

	public void setReceipted(Boolean receipted) {
		this.receipted = receipted;
	}

	public Boolean getInternal() {
		return internal;
	}

	public void setInternal(Boolean internal) {
		this.internal = internal;
	}

	public String getFinishUser() {
		return finishUser;
	}

	public void setFinishUser(String finishUser) {
		this.finishUser = finishUser;
	}

}
