package com.src.printing.office.po.model.deliveryNote.getOneForReceipt.response;

public class DNItemForReceipt {

	private long dnID;
	private String dnNumber;
	private double quantity;
	private String itemName;
	private String measureUnit;
	private Double dnPricePerUnit;

	public long getDnID() {
		return dnID;
	}

	public void setDnID(long dnID) {
		this.dnID = dnID;
	}

	public String getDnNumber() {
		return dnNumber;
	}

	public void setDnNumber(String dnNumber) {
		this.dnNumber = dnNumber;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	public Double getDnPricePerUnit() {
		return dnPricePerUnit;
	}

	public void setDnPricePerUnit(Double dnPricePerUnit) {
		this.dnPricePerUnit = dnPricePerUnit;
	}

}
