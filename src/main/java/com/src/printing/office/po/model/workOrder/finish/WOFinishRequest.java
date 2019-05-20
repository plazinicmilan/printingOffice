package com.src.printing.office.po.model.workOrder.finish;

import java.io.Serializable;

public class WOFinishRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private long woID;
	private long userID;

	public long getWoID() {
		return woID;
	}

	public void setWoID(long woID) {
		this.woID = woID;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

}
