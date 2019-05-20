package com.src.printing.office.deserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@Configuration
public class CalendarDeserializer extends StdDeserializer<Calendar> {

	@Value("${date.format}")
	private String dateFormat;

	private static final long serialVersionUID = 1L;

	public CalendarDeserializer() {
		this(null);
	}

	protected CalendarDeserializer(JavaType valueType) {
		super(valueType);
	}

	@Override
	public Calendar deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		if (p == null || p.toString().trim().isEmpty())
			return null;

		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(formatter.parse(p.getValueAsString()));
			return c;
		} catch (ParseException e) {
			return null;
		}

	}

}
