package com.src.printing.office.po.model.receipt.save.request;

import java.util.List;

public class ReceiptSaveItem {

	private Long id;
	private Long dnID;
	private int no;
	private String itemName;
	private String unitOfMeasure;
	private Double quantity;
	private Double pdvRate;
	private Double price;
	private Double pdv;
	private Double priceWithPDV;
	private Double total;
	private String deliveryNote;
	private List<Long> woIDList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDnID() {
		return dnID;
	}

	public void setDnID(Long dnID) {
		this.dnID = dnID;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPdv() {
		return pdv;
	}

	public void setPdv(Double pdv) {
		this.pdv = pdv;
	}

	public Double getPriceWithPDV() {
		return priceWithPDV;
	}

	public void setPriceWithPDV(Double priceWithPDV) {
		this.priceWithPDV = priceWithPDV;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getDeliveryNote() {
		return deliveryNote;
	}

	public void setDeliveryNote(String deliveryNote) {
		this.deliveryNote = deliveryNote;
	}

	public Double getPdvRate() {
		return pdvRate;
	}

	public void setPdvRate(Double pdvRate) {
		this.pdvRate = pdvRate;
	}

	public List<Long> getWoIDList() {
		return woIDList;
	}

	public void setWoIDList(List<Long> woIDList) {
		this.woIDList = woIDList;
	}

}
