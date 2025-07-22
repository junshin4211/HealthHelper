package com.example.healthhelper.planpage.domain.usecase

import android.util.Log
import androidx.annotation.RequiresApi
import com.example.healthhelper.planpage.data.model.PlanModel
import com.example.healthhelper.planpage.domain.model.DateRangeTitle
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
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
         val list = planList.filter { it.finishstate == 0 }.sortedByDescending { it.startDateTime }
        Log.d(tag, "分類未完成計畫: $list")
        list
    } else {
         val list = planList.filterNot { it.finishstate == 0 }.sortedByDescending { it.startDateTime }
        Log.d(tag, "分類已完成計畫: $list")
        list
    }
}

// 您提供的泛型擴展函數
fun <T, R> T.dateFormat(transform: (T) -> R): R {
    return transform(this)
}

fun transformDate(dateString: String):String{
    val formattedDate = dateString.dateFormat { inputStr ->
        // 這裡的轉換邏輯與 formatSpecificDateString 內部類似
        val inputFormatter = DateTimeFormatter.ofPattern("MMM d, uuuu, h:mm:ss a",
            Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd",
            Locale.ENGLISH)
        try {
            val localDateTime = LocalDateTime.parse(inputStr, inputFormatter)
            localDateTime.format(outputFormatter)
        } catch (e: DateTimeParseException) {
            "Invalid Date" // 或者返回 null，則 R 類型需要是 String?
        }
    }
    return formattedDate
}