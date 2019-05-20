package com.src.printing.office.po.model.finishedProduct.getSelected.response;

import java.util.List;

import com.src.printing.office.po.model.POGenericType;

public class FPItem {

	private Long id;
	private String itemName;
	private POGenericType measureUnit;
	private Integer quantity;
	private Double pricePerUnit;
	private List<Long> woIDList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public POGenericType getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(POGenericType measureUnit) {
		this.measureUnit = measureUnit;
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

	public List<Long> getWoIDList() {
		return woIDList;
	}

	public void setWoIDList(List<Long> woIDList) {
		this.woIDList = woIDList;
	}

}
