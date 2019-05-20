package com.src.printing.office.po.model.statusWO.getAll.response;

import java.io.Serializable;

public class StatusWOResponse implements Serializable, Comparable<StatusWOResponse> {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;

	public StatusWOResponse(long id, String name) {
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
	public int compareTo(StatusWOResponse statusWO) {
		return this.id > statusWO.getId() ? 1 : -1;
	}
}
