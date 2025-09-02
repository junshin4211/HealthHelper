package com.example.healthhelper.planpage.domain.usecase

import android.util.Log
import com.example.healthhelper.planpage.data.model.PlanModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

//分類已完成或未完成計畫並排序最新日期
fun filterAndSortPlan(
    planList: List<PlanModel>,
    isFinish: Boolean
):List<PlanModel> {
    val tag = "filterAndSortPlan"
    Log.d(tag, "$isFinish")
    return if (!isFinish) {
         val list = planList.filter { it.finishstate == 0 }.sortedBy { it.startDateTime }
        Log.d(tag, "分類未完成計畫: $list")
        list
    } else {
        val list = planList.filter { it.finishstate == 1 }
        val listWithGoal = planList.filter { it.finishstate == 2 }

        val completedList = (list + listWithGoal).sortedBy { it.startDateTime }
        Log.d(tag, "分類已完成計畫: $list")
        completedList
    }
}

// 您提供的泛型擴展函數
fun <T, R> T.dateFormat(transform: (T) -> R): R {
    return transform(this)
}

fun transformDate(dateTimeString: String):String{
    val formattedDate = dateTimeString.dateFormat { inputStr ->

        val inputFormatter = DateTimeFormatter.ofPattern("MMM d, uuuu, h:mm:ss a",
            Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd",
            Locale.ENGLISH)
        try {
            val localDateTime = LocalDateTime.parse(inputStr, inputFormatter)
            localDateTime.format(outputFormatter)
        } catch (e: DateTimeParseException) {
            "Invalid DateTime" // 或者返回 null，則 R 類型需要是 String?
        }
    }
    return formattedDate
}

fun transformDateTimeToDate(dateTimeString: String): String? {
    val formattedDate = dateTimeString.dateFormat{ inputStr ->
        val inputFormatter = DateTimeFormatter.ofPattern("MMM d, uuuu, h:mm:ss a",
            Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ISO_LOCAL_DATE // "yyyy-MM-dd"

        try {
            val localDate = LocalDate.parse(inputStr, inputFormatter)
            localDate.format(outputFormatter)
        }catch (e: DateTimeParseException){
            null
        }
    }
    return formattedDate
}