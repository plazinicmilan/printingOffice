package com.src.printing.office.po.model.material.getOne.response;

import java.io.Serializable;

import com.src.printing.office.po.model.POGenericType;

public class MaterialGetOneResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private Double limit;
	private Double quantity;
	private POGenericType measureUnit;
	private POGenericType supplier;
	private String maker;
	private Double price;
	private Double packageQuantity;
	private POGenericType packageMeasureUnit;
	private String materialType;
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

	public POGenericType getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(POGenericType measureUnit) {
		this.measureUnit = measureUnit;
	}

	public POGenericType getSupplier() {
		return supplier;
	}

	public void setSupplier(POGenericType supplier) {
		this.supplier = supplier;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPackageQuantity() {
		return packageQuantity;
	}

	public void setPackageQuantity(Double packageQuantity) {
		this.packageQuantity = packageQuantity;
	}

	public POGenericType getPackageMeasureUnit() {
		return packageMeasureUnit;
	}

	public void setPackageMeasureUnit(POGenericType packageMeasureUnit) {
		this.packageMeasureUnit = packageMeasureUnit;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public Boolean getInactive() {
		return inactive;
	}

	public void setInactive(Boolean inactive) {
		this.inactive = inactive;
	}

}
