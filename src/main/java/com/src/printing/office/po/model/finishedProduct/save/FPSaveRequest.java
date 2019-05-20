package com.src.printing.office.po.model.finishedProduct.save;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.src.printing.office.po.model.POGenericType;

public class FPSaveRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private POGenericType customer;
	@NotNull
	private POGenericType supplier;
	@NotNull
	private POGenericType measureUnit;
	@NotNull
	@NotBlank
	@NotEmpty
	private String name;
	@NotNull
	private Integer quantity;
	@NotNull
	private Double pricePerUnit;

	public POGenericType getCustomer() {
		return customer;
	}

	public void setCustomer(POGenericType customer) {
		this.customer = customer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public POGenericType getSupplier() {
		return supplier;
	}

	public void setSupplier(POGenericType supplier) {
		this.supplier = supplier;
	}

	public POGenericType getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(POGenericType measureUnit) {
		this.measureUnit = measureUnit;
	}

	public Double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

}
