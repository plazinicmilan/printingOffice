package com.src.printing.office.po.model.material.getAllCritical.response;

import java.io.Serializable;

public class MaterialGetAllCriticalResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private Double limit;
	private Double quantity;
	private String measureUnit;
	private String maker;
	private Double packageQuantity;
	private String packageMeasureUnit;
	private String materialType;

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

	public Double getLimit() {
		return limit;
	}

	public void setLimit(Double limit) {
		this.limit = limit;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	public Double getPackageQuantity() {
		return packageQuantity;
	}

	public void setPackageQuantity(Double packageQuantity) {
		this.packageQuantity = packageQuantity;
	}

	public String getPackageMeasureUnit() {
		return packageMeasureUnit;
	}

	public void setPackageMeasureUnit(String packageMeasureUnit) {
		this.packageMeasureUnit = packageMeasureUnit;
	}

}
