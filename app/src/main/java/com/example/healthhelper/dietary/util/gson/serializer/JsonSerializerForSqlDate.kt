package com.example.healthhelper.dietary.util.gson.serializer

import com.example.healthhelper.dietary.util.gson.constant.SqlDatePattern
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import java.sql.Date
import java.text.SimpleDateFormat

object JsonSerializerForSqlDate {
    @JvmField
	var dateDeserializer: JsonSerializer<Date> = JsonSerializer { arg0, arg1, arg2 ->
        val dateFormat = SimpleDateFormat(SqlDatePattern.sqlDatePattern)
        JsonPrimitive(dateFormat.format(arg0).toString())
    }
}
