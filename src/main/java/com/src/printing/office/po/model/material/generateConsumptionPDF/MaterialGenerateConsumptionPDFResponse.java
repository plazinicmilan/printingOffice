package com.src.printing.office.po.model.material.generateConsumptionPDF;

public class MaterialGenerateConsumptionPDFResponse implements Comparable<MaterialGenerateConsumptionPDFResponse> {

	private Long id;
	private String name;
	private String maker;
	private String measureUnit;
	private Long quantity;

	public MaterialGenerateConsumptionPDFResponse(Long id, String name, String maker, String measureUnit,
			Long quantity) {
		super();
		this.id = id;
		this.name = name;
		this.maker = maker;
		this.measureUnit = measureUnit;
		this.quantity = quantity;
	}

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

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MaterialGenerateConsumptionPDFResponse other = (MaterialGenerateConsumptionPDFResponse) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(MaterialGenerateConsumptionPDFResponse o) {
		return this.name.compareTo(o.name) == 0 ? this.id.compareTo(o.id) : this.name.compareTo(o.name);
	}

}
