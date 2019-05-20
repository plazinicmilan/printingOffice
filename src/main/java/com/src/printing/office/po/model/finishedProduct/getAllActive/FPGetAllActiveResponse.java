package com.src.printing.office.po.model.finishedProduct.getAllActive;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.src.printing.office.po.model.POGenericType;

public class FPGetAllActiveResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String originalName;
	private POGenericType measureUnit;
	private Integer quantity;
	private List<Long> woIDList;
	private Double pricePerUnit;

	public FPGetAllActiveResponse() {
		super();
		woIDList = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
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

	public List<Long> getWoIDList() {
		return woIDList;
	}

	public void setWoIDList(List<Long> woIDList) {
		this.woIDList = woIDList;
	}

	public Double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

}
