package com.example.healthhelper.dietary.gson

import com.google.gson.Gson

val gson = Gson()

fun Class<*>.toJson():String =
    gson.toJson(this)

fun Any.toJson():String =
    gson.toJson(this)

fun String.fromJson(
    cls: Class<*>,
):Any =
    gson.fromJson(this,cls::class.java)
