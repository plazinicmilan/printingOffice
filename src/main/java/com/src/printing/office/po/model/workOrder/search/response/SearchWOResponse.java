package com.src.printing.office.po.model.workOrder.search.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.serializer.CalendarSerializer;

public class SearchWOResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String woNumber;
	@JsonSerialize(using = CalendarSerializer.class)
	private Calendar dateWO;
	private String customer;
	private String user;
	private String status;
	private String jobTitle;
	private Integer circulation;
	private String circulationMeasureUnit;
	private List<POGenericType> dnList;
	private List<POGenericType> receiptList;
	private String finishUser;
	private Boolean oldWO;
	private Integer insertedFP;

	public SearchWOResponse() {
		dnList = new ArrayList<>();
		receiptList = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWoNumber() {
		return woNumber;
	}

	public void setWoNumber(String woNumber) {
		this.woNumber = woNumber;
	}

	public Calendar getDateWO() {
		return dateWO;
	}

	public void setDateWO(Calendar dateWO) {
		this.dateWO = dateWO;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getCirculationMeasureUnit() {
		return circulationMeasureUnit;
	}

	public void setCirculationMeasureUnit(String circulationMeasureUnit) {
		this.circulationMeasureUnit = circulationMeasureUnit;
	}

	public List<POGenericType> getDnList() {
		return dnList;
	}

	public void setDnList(List<POGenericType> dnList) {
		this.dnList = dnList;
	}

	public List<POGenericType> getReceiptList() {
		return receiptList;
	}

	public void setReceiptList(List<POGenericType> receiptList) {
		this.receiptList = receiptList;
	}

	public String getFinishUser() {
		return finishUser;
	}

	public void setFinishUser(String finishUser) {
		this.finishUser = finishUser;
	}

	public Boolean getOldWO() {
		return oldWO;
	}

	public void setOldWO(Boolean oldWO) {
		this.oldWO = oldWO;
	}

	public Integer getInsertedFP() {
		return insertedFP;
	}

	public void setInsertedFP(Integer insertedFP) {
		this.insertedFP = insertedFP;
	}

}
