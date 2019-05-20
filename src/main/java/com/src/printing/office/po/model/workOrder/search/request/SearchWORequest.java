package com.src.printing.office.po.model.workOrder.search.request;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.src.printing.office.deserializer.CalendarDeserializer;

public class SearchWORequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonDeserialize(using = CalendarDeserializer.class)
	private Calendar dateFrom;
	@JsonDeserialize(using = CalendarDeserializer.class)
	private Calendar dateTo;
	private Customer customer;
	private String woNumber;
	private List<StatusWO> statusList;
	private boolean oldWO;

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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getWoNumber() {
		return woNumber;
	}

	public void setWoNumber(String woNumber) {
		this.woNumber = woNumber;
	}

	public List<StatusWO> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<StatusWO> statusList) {
		this.statusList = statusList;
	}

	public boolean isOldWO() {
		return oldWO;
	}

	public void setOldWO(boolean oldWO) {
		this.oldWO = oldWO;
	}

}
