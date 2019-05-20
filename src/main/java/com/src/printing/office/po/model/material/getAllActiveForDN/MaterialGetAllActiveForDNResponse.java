package com.src.printing.office.po.model.material.getAllActiveForDN;

import java.io.Serializable;

import com.src.printing.office.po.model.POGenericType;

public class MaterialGetAllActiveForDNResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String originalName;
	private POGenericType measureUnit;
	private Integer quantity;

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

}
