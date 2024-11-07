package com.example.healthhelper.dietary.util.gson.deserializer

import com.example.healthhelper.dietary.util.gson.constant.SqlTimePattern
import com.google.gson.JsonDeserializer
import com.google.gson.JsonParseException
import java.sql.Time
import java.text.ParseException
import java.text.SimpleDateFormat

object JsonDeserializerForSqTime {
    @JvmField
	var timeDeserializer: JsonDeserializer<Time> = JsonDeserializer { json, typeOfT, context ->
        val timeFormat = SimpleDateFormat(SqlTimePattern.sqlTimePattern)
        try {
            return@JsonDeserializer Time(timeFormat.parse(json.asString).time)
        } catch (e: ParseException) {
            throw JsonParseException(e)
        }
    }
}
