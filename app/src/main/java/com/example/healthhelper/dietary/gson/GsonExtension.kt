package com.example.healthhelper.dietary.gson

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

val gson = GsonBuilder().serializeNulls().create()

val jsonObject = JsonObject()

fun Class<*>.toJson():String =
    gson.toJson(this)

fun Any.toJson():String =
    gson.toJson(this)

fun String.fromJson(
    cls: Class<*>,
):Any =
    gson.fromJson(this,cls::class.java)
