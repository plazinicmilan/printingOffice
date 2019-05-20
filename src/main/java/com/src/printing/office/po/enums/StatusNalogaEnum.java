package com.src.printing.office.po.enums;

public enum StatusNalogaEnum {

	U_IZRADI(1), NAPRAVLJEN(2);

	private long id;

	private StatusNalogaEnum(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
}
