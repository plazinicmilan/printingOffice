package com.src.printing.office.po.model.finishedProduct.getOne.response;

import java.io.Serializable;

import com.src.printing.office.po.model.POGenericType;

public class FPGetOneResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String itemName;
	private POGenericType measureUnit;
	private Integer quantity;
	private POGenericType customer;
	private Double pricePerUnit;

	public FPGetOneResponse() {
		super();
	}

	public POGenericType getCustomer() {
		return customer;
	}

	public void setCustomer(POGenericType customer) {
		this.customer = customer;
	}

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

}
