package com.example.healthhelper.dietary.util.gson.serializer

import com.example.healthhelper.dietary.util.gson.constant.SqlTimePattern
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import java.sql.Time
import java.text.SimpleDateFormat

object JsonSerializerForSqlTime {
    @JvmField
	var timeDeserializer: JsonSerializer<Time> = JsonSerializer { arg0, arg1, arg2 ->
        val timeFormat = SimpleDateFormat(SqlTimePattern.sqlTimePattern)
        JsonPrimitive(timeFormat.format(arg0).toString())
    }
}
