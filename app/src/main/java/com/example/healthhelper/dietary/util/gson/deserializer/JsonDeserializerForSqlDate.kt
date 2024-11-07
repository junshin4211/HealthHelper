package com.example.healthhelper.dietary.util.gson.deserializer

import com.example.healthhelper.dietary.util.gson.constant.SqlDatePattern
import com.google.gson.JsonDeserializer
import com.google.gson.JsonParseException
import java.sql.Date
import java.text.ParseException
import java.text.SimpleDateFormat

object JsonDeserializerForSqlDate {
    @JvmField
	var dateDeserializer: JsonDeserializer<Date> = JsonDeserializer { json, typeOfT, context ->
        val dateFormat = SimpleDateFormat(SqlDatePattern.sqlDatePattern)
        try {
            return@JsonDeserializer Date(dateFormat.parse(json.asString).time)
        } catch (e: ParseException) {
            throw JsonParseException(e)
        }
    }
}
