package com.src.printing.office.po.model.order.accept;

import java.io.Serializable;

public class OrderAcceptRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private long orderID;
	private long acceptedUserID;

	public long getOrderID() {
		return orderID;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

	public long getAcceptedUserID() {
		return acceptedUserID;
	}

	public void setAcceptedUserID(long acceptedUserID) {
		this.acceptedUserID = acceptedUserID;
	}

}
