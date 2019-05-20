package com.src.printing.office.deserializer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@Configuration
public class DateDeserializer extends StdDeserializer<Date> {

	@Value("${date.format}")
	private String dateFormat;

	private static final long serialVersionUID = 1L;

	public DateDeserializer() {
		this(null);
	}

	protected DateDeserializer(JavaType valueType) {
		super(valueType);
	}

	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) {

		if (p == null || p.toString().trim().isEmpty())
			return null;

		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		try {
			return formatter.parse(p.toString());
		} catch (ParseException e) {
			return null;
		}
	}

}
