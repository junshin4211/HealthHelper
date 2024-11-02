package com.example.healthhelper.dietary.eclipse.deserializer;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import web.dietdiary.constant.SqlTimePattern;

public class JsonDeserializerForSqTime {
	public static JsonDeserializer<Time> timeDeserializer = new JsonDeserializer<Time>() {
		@Override
		public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			SimpleDateFormat timeFormat = new SimpleDateFormat(SqlTimePattern.sqlTimePattern);
			try {
				return new Time(timeFormat.parse(json.getAsString()).getTime());
			} catch (ParseException e) {
				throw new JsonParseException(e);
			}
		}
	};
}
