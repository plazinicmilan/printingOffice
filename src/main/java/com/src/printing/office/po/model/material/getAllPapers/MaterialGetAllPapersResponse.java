package com.src.printing.office.po.model.material.getAllPapers;

import java.io.Serializable;

public class MaterialGetAllPapersResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private boolean quantityExists;

	public MaterialGetAllPapersResponse() {
		super();
	}

	public MaterialGetAllPapersResponse(long id, String naziv, boolean quantityExists) {
		super();
		this.id = id;
		this.name = naziv;
		this.quantityExists = quantityExists;
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

	public boolean isQuantityExists() {
		return quantityExists;
	}

	public void setQuantityExists(boolean quantityExists) {
		this.quantityExists = quantityExists;
	}

}
