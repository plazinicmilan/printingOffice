package com.src.printing.office.po.model.user.search.request;

import java.io.Serializable;

import com.src.printing.office.po.model.POGenericType;

public class SearchUserRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private POGenericType user;

	public POGenericType getUser() {
		return user;
	}

	public void setUser(POGenericType user) {
		this.user = user;
	}

}
