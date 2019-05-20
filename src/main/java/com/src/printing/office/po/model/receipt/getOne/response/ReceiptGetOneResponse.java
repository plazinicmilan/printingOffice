package com.src.printing.office.po.model.receipt.getOne.response;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.receipt.save.request.ReceiptSaveItem;
import com.src.printing.office.serializer.CalendarSerializer;

public class ReceiptGetOneResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private Long userID;
	@JsonSerialize(using = CalendarSerializer.class)
	private Calendar date;
	@JsonSerialize(using = CalendarSerializer.class)
	private Calendar datePayment;
	private String receiptNumber;
	private String receiptType;
	private POGenericType customer;
	private Double totalPDV;
	private Double totalWithoutPDV;
	private Double totalAmount;
	private Boolean pdvIncluded;
	private String note;
	private List<ReceiptSaveItem> receiptItems;

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public POGenericType getCustomer() {
		return customer;
	}

	public void setCustomer(POGenericType customer) {
		this.customer = customer;
	}

	public Double getTotalPDV() {
		return totalPDV;
	}

	public void setTotalPDV(Double totalPDV) {
		this.totalPDV = totalPDV;
	}

	public Double getTotalWithoutPDV() {
		return totalWithoutPDV;
	}

	public void setTotalWithoutPDV(Double totalWithoutPDV) {
		this.totalWithoutPDV = totalWithoutPDV;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<ReceiptSaveItem> getReceiptItems() {
		return receiptItems;
	}

	public void setReceiptItems(List<ReceiptSaveItem> receiptItems) {
		this.receiptItems = receiptItems;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Calendar getDatePayment() {
		return datePayment;
	}

	public void setDatePayment(Calendar datePayment) {
		this.datePayment = datePayment;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	public Boolean getPdvIncluded() {
		return pdvIncluded;
	}

	public void setPdvIncluded(Boolean pdvIncluded) {
		this.pdvIncluded = pdvIncluded;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
