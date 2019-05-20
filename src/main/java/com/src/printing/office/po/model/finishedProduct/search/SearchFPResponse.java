package com.src.printing.office.po.model.finishedProduct.search;

import java.io.Serializable;

public class SearchFPResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String customerName;
	private String woNumber;
	private String jobTitle;
	private Integer quantity;
	private String quantityMeasureUnit;
	private String supplierName;
	private Double pricePerUnit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getWoNumber() {
		return woNumber;
	}

	public void setWoNumber(String woNumber) {
		this.woNumber = woNumber;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getQuantityMeasureUnit() {
		return quantityMeasureUnit;
	}

	public void setQuantityMeasureUnit(String quantityMeasureUnit) {
		this.quantityMeasureUnit = quantityMeasureUnit;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

}
