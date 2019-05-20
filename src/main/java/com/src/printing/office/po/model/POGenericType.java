package com.src.printing.office.po.model;

import java.io.Serializable;

public class POGenericType implements Serializable, Comparable<POGenericType> {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;

	public POGenericType() {
		super();
	}

	public POGenericType(long id, String name) {
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

	@Override
	public int compareTo(POGenericType o) {
		return this.name.toLowerCase().compareTo(o.name.toLowerCase());
	}

}
