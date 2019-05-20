package com.src.printing.office.po.model.deliveryNote.finish;

import java.io.Serializable;

public class DNFinishRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private long dnID;
	private long userID;

	public long getDnID() {
		return dnID;
	}

	public void setDnID(long dnID) {
		this.dnID = dnID;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

}
