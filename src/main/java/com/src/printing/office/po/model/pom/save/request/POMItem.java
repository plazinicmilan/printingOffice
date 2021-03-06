package com.src.printing.office.po.model.pom.save.request;

import com.src.printing.office.po.model.POGenericType;

public class POMItem {

	private int no;
	private POGenericType material;
	private Double quantity;
	private Double pricePerUnit;
	private Double price;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public POGenericType getMaterial() {
		return material;
	}

	public void setMaterial(POGenericType material) {
		this.material = material;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
