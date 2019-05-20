package com.src.printing.office.po.model.deliveryNote.getOne.response;

import java.util.List;

import com.src.printing.office.po.model.POGenericType;

public class DNItem {

	private Long dnItemID;
	private Long id;
	private int no;
	private String itemName;
	private POGenericType unitOfMeasure;
	private Integer quantity;
	private Double pricePerUnit;
	private Double price;
	private Integer gratis;
	private List<Long> woIDList;
	private String commonName;
	private Boolean paper;

	public Long getDnItemID() {
		return dnItemID;
	}

	public void setDnItemID(Long dnItemID) {
		this.dnItemID = dnItemID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public POGenericType getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(POGenericType unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getGratis() {
		return gratis;
	}

	public void setGratis(Integer gratis) {
		this.gratis = gratis;
	}

	public List<Long> getWoIDList() {
		return woIDList;
	}

	public void setWoIDList(List<Long> woIDList) {
		this.woIDList = woIDList;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public Boolean getPaper() {
		return paper;
	}

	public void setPaper(Boolean paper) {
		this.paper = paper;
	}

}
