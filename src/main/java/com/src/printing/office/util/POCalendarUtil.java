package com.src.printing.office.util;

import java.util.Calendar;

import org.springframework.context.annotation.Configuration;

@Configuration
public class POCalendarUtil {

	public Calendar getCalendarBeginningOfTheDay(Calendar dateFrom) {

		dateFrom.set(Calendar.HOUR_OF_DAY, 0);
		dateFrom.set(Calendar.MINUTE, 0);
		dateFrom.set(Calendar.SECOND, 0);
		dateFrom.set(Calendar.MILLISECOND, 0);

		return dateFrom;
	}

	public Calendar getCalendarEndOfTheDay(Calendar dateTo) {

		dateTo.set(Calendar.HOUR_OF_DAY, 23);
		dateTo.set(Calendar.MINUTE, 59);
		dateTo.set(Calendar.SECOND, 59);
		dateTo.set(Calendar.MILLISECOND, 999);

		return dateTo;
	}

}
