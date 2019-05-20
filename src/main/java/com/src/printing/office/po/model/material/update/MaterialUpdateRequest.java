package com.src.printing.office.po.model.material.update;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.src.printing.office.po.model.POGenericType;

public class MaterialUpdateRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long id;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
