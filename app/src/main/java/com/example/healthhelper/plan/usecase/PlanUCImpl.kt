package com.example.healthhelper.plan.usecase

import java.text.SimpleDateFormat
import java.util.Locale

class PlanUCImpl : PlanUC {
    override fun DateTimeformat(datetime: Any?):String {
        val input = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.getDefault())
        val output = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            when(datetime)
            {
                is String -> "9999-99-99"
                null -> "9999-99-99"
                else -> {
                    val date = input.parse(datetime.toString()) ?: "9999-99-99"
                    val formattedDate = output.format(date)
                    formattedDate
                }
            }
        }catch (e:Exception){
            "9999-99-99"
        }
    }
}