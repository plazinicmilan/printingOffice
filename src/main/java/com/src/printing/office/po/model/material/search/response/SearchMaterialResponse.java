package com.src.printing.office.po.model.material.search.response;

import java.io.Serializable;

public class SearchMaterialResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String maker;
	private String supplier;
	private Double limit;
	private Double quantity;
	private String measureUnit;
	private Double packageQuantity;
	private String packageMeasureUnit;
	private Boolean inactive;

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

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
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

	public Boolean getInactive() {
		return inactive;
	}

	public void setInactive(Boolean inactive) {
		this.inactive = inactive;
	}

}
