package com.src.printing.office.po.model.receipt.update;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.src.printing.office.deserializer.CalendarDeserializer;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.receipt.save.request.ReceiptSaveItem;

public class ReceiptUpdateRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long id;
	@NotNull
	private Long userID;
	@NotNull
	@JsonDeserialize(using = CalendarDeserializer.class)
	private Calendar date;
	@NotNull
	@JsonDeserialize(using = CalendarDeserializer.class)
	private Calendar datePayment;
	@NotNull
	private String receiptNumber;
	@NotNull
	private String receiptType;
	@NotNull
	private POGenericType customer;
	@NotNull
	private Double totalPDV;
	@NotNull
	private Double totalWithoutPDV;
	@NotNull
	private Double totalAmount;
	@NotNull
	private Boolean pdvIncluded;
	private String note;
	@NotNull
	private List<ReceiptSaveItem> receiptItems;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
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
