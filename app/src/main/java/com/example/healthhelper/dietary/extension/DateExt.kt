package com.example.healthhelper.dietary.extension

import java.util.Date

fun String.convertToTimeMills():Long = Date(this).time