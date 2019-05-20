package com.src.printing.office.po.model.material.getAll.response;

import java.io.Serializable;

public class MaterialResponse implements Serializable, Comparable<MaterialResponse> {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;

	public MaterialResponse() {
		super();
	}

	public MaterialResponse(long id, String naziv) {
		super();
		this.id = id;
		this.name = naziv;
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

	@Override
	public int compareTo(MaterialResponse mr) {
		return this.name.toLowerCase().compareTo(mr.name.toLowerCase());
	}

}
