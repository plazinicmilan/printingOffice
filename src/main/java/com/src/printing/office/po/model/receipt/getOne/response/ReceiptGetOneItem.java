package com.src.printing.office.po.model.receipt.getOne.response;

public class ReceiptGetOneItem {

	private Long dnID;
	private int no;
	private String itemName;
	private String unitOfMeasure;
	private Double quantity;
	private Double price;
	private Double pdv;
	private Double priceWithPDV;
	private Double total;
	private String deliveryNote;
	private String workOrder;
	private Integer circulation;

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

	public String getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}

	public Integer getCirculation() {
		return circulation;
	}

	public void setCirculation(Integer circulation) {
		this.circulation = circulation;
	}

}
