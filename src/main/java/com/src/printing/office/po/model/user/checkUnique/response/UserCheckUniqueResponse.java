package com.src.printing.office.po.model.user.checkUnique.response;

import java.io.Serializable;

public class UserCheckUniqueResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean notUnique;

	public UserCheckUniqueResponse(boolean notUnique) {
		super();
		this.notUnique = notUnique;
	}

	public boolean isNotUnique() {
		return notUnique;
	}

	public void setNotUnique(boolean notUnique) {
		this.notUnique = notUnique;
	}

}
