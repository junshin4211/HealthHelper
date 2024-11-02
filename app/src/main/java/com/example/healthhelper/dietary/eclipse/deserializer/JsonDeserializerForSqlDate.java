package com.example.healthhelper.dietary.eclipse.deserializer;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import web.dietdiary.constant.SqlDatePattern;

public class JsonDeserializerForSqlDate {
	public static JsonDeserializer<Date> dateDeserializer = new JsonDeserializer<Date>() {
		@Override
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			SimpleDateFormat dateFormat = new SimpleDateFormat(SqlDatePattern.sqlDatePattern);
			try {
				return new Date(dateFormat.parse(json.getAsString()).getTime());
			} catch (ParseException e) {
				throw new JsonParseException(e);
			}
		}
	};
}
