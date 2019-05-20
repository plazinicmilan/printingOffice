package com.src.printing.office.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@Configuration
public class DateSerializer extends StdSerializer<Date> {

	@Value("${date.format}")
	private String dateFormat;

	public DateSerializer() {
		this(null);
	}

	protected DateSerializer(Class<Date> t) {
		super(t);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {

		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

		gen.writeString(formatter.format(value));
	}

}
