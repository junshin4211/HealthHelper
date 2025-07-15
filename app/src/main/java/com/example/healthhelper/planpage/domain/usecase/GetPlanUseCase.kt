package com.example.healthhelper.planpage.domain.usecase

import android.util.Log
import androidx.annotation.RequiresApi
import com.example.healthhelper.planpage.data.model.PlanModel
import com.example.healthhelper.planpage.ui.DateRangeTitle
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

//fun formatSpecificDateString(inputDateString: String): String? {
//    val inputFormatter = try {
//        DateTimeFormatter.ofPattern("MMM d, uuuu, h:mm:ss a", Locale.ENGLISH)
//    } catch (e: IllegalArgumentException) {
//        // Log.e("dateFormat", "Invalid input pattern", e) // 日誌記錄
//        return null // 如果模式本身有問題
//    }
//
//    // 定義目標輸出日期字符串的格式
//    val outputFormatter = try {
//        DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
//    } catch (e: IllegalArgumentException) {
//        // Log.e("dateFormat", "Invalid output pattern", e) // 日誌記錄
//        return null
//    }
//
//    return try {
//        // 1. 解析輸入字符串為 LocalDateTime 對象
//        val localDateTime = LocalDateTime.parse(inputDateString, inputFormatter)
//        // 2. 將 LocalDateTime 對象格式化為目標字符串
//        localDateTime.format(outputFormatter)
//    } catch (e: DateTimeParseException) {
//        // 如果輸入字符串無法被 inputFormatter 解析，則會拋出此異常
//        println("Error parsing date string: '$inputDateString'. ${e.message}")
//        // Log.e("dateFormat", "Error parsing date string: '$inputDateString'", e) // 日誌記錄
//        null // 或者可以拋出異常，或者返回一個錯誤提示字符串
//    } catch (e: Exception) {
//        // 其他潛在異常
//        println("An unexpected error occurred during date formatting: ${e.message}")
//        // Log.e("dateFormat", "Unexpected error: '$inputDateString'", e)
//        null
//    }
//}