package com.raymon.frame.web.view;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampTypeAdapter implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp> {

	private final ThreadLocal<DateFormat> tdf = new ThreadLocal<DateFormat>();
	
	public JsonElement serialize(Timestamp src, Type typeOfSrc,
			JsonSerializationContext context) {
		DateFormat df = this.getDateFormat();
		String dfStr = df.format(new Date(src.getTime()));
		return new JsonPrimitive(dfStr);
	}

	public Timestamp deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) {
		try {
			DateFormat df = this.getDateFormat();
			Date date = df.parse(json.getAsString());
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new JsonParseException(e);
		}
	}

	private DateFormat getDateFormat(){
		if(tdf.get() == null) tdf.set(new SimpleDateFormat("HH:mm:ss"));
		return tdf.get();
	}

}
