package com.src.printing.office.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@Configuration
public class CalendarSerializer extends StdSerializer<Calendar> {

	@Value("${date.format}")
	private String dateFormat;

	public CalendarSerializer() {
		this(null);
	}

	protected CalendarSerializer(Class<Calendar> t) {
		super(t);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void serialize(Calendar value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeString(new SimpleDateFormat(dateFormat).format(value.getTime()));
	}

}
