package com.src.printing.office.po.model.deliveryNote.update.request;

import java.util.List;

import javax.validation.constraints.NotNull;

public class DNUpdateItem {

	@NotNull
	private Long dnItemID;
	@NotNull
	private Long id;
	private int no;
	private String itemName;
	@NotNull
	private Long unitOfMeasureId;
	private Integer quantity;
	private Double pricePerUnit;
	private Double price;
	private boolean paper;
	private List<Long> woIDList;
	private Integer gratis;
	private String commonName;

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

	public Long getUnitOfMeasureId() {
		return unitOfMeasureId;
	}

	public void setUnitOfMeasureId(Long unitOfMeasureId) {
		this.unitOfMeasureId = unitOfMeasureId;
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

	public boolean isPaper() {
		return paper;
	}

	public void setPaper(boolean paper) {
		this.paper = paper;
	}

	public List<Long> getWoIDList() {
		return woIDList;
	}

	public void setWoIDList(List<Long> woIDList) {
		this.woIDList = woIDList;
	}

	public Integer getGratis() {
		return gratis;
	}

	public void setGratis(Integer gratis) {
		this.gratis = gratis;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

}
