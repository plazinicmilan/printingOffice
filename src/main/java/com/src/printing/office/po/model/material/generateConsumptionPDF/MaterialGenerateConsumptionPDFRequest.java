package com.src.printing.office.po.model.material.generateConsumptionPDF;

import java.io.Serializable;
import java.util.Calendar;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.src.printing.office.deserializer.CalendarDeserializer;

public class MaterialGenerateConsumptionPDFRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonDeserialize(using = CalendarDeserializer.class)
	private Calendar dateFrom;
	@JsonDeserialize(using = CalendarDeserializer.class)
	private Calendar dateTo;

	public Calendar getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Calendar dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Calendar getDateTo() {
		return dateTo;
	}

	public void setDateTo(Calendar dateTo) {
		this.dateTo = dateTo;
	}

}
