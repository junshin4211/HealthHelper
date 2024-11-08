package com.example.healthhelper.dietary.util.gson

import com.example.healthhelper.dietary.util.gson.deserializer.JsonDeserializerForSqTime
import com.example.healthhelper.dietary.util.gson.deserializer.JsonDeserializerForSqlDate
import com.example.healthhelper.dietary.util.gson.serializer.JsonSerializerForSqlDate
import com.example.healthhelper.dietary.util.gson.serializer.JsonSerializerForSqlTime
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.sql.Date
import java.sql.Time

object GsonForSqlDateAndSqlTime {
    var gson: Gson = GsonBuilder()
        .registerTypeAdapter(Date::class.java, JsonDeserializerForSqlDate.dateDeserializer)
        .registerTypeAdapter(Time::class.java, JsonDeserializerForSqTime.timeDeserializer)
        .registerTypeAdapter(Date::class.java, JsonSerializerForSqlDate.dateDeserializer)
        .registerTypeAdapter(Time::class.java, JsonSerializerForSqlTime.timeDeserializer)
        .create()
}
