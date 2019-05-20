package com.src.printing.office.po.model.workOrder.getOne.response;

public class Material {

	private long id;
	private String name;

	public Material() {
		super();
	}

	public Material(long id, String name) {
		super();
		this.id = id;
		this.name = name;
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

}
