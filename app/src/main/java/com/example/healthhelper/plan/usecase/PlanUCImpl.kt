package com.example.healthhelper.plan.usecase

import android.util.Log
import com.example.healthhelper.plan.NutritionGoals
import com.example.healthhelper.plan.PlanPage
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale

class PlanUCImpl : PlanUC {
    override fun dateTimeFormat(datetime: Any?): String {
        val output = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        return try {
            val date = when (datetime) {
                is Date -> datetime
                is LocalDate -> Date.from(datetime.atStartOfDay(ZoneId.systemDefault()).toInstant())
                is LocalDateTime -> Date.from(datetime.atZone(ZoneId.systemDefault()).toInstant())
                is String -> {
                    // 嘗試解析字串為 LocalDateTime 或 LocalDate
                    val inputDateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
                    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                    try {
                        inputDateTimeFormat.parse(datetime)
                    } catch (e: Exception) {
                        inputDateFormat.parse(datetime)
                    }
                }
                null -> return "9999-99-99"
                else -> return "9999-99-99"
            }

            // 將日期格式化為 "yyyy-MM-dd"
            output.format(date)
        } catch (e: Exception) {
            Log.d("tag_DateTimeformat", "DateTimeformat: $e")
            "9999-99-99"
        }
    }

    override fun stringToTimeStamp(datetime: LocalDateTime): Timestamp? {
        return try {
            Timestamp.valueOf(datetime.toString())

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 定義函數，將目標值通過 onSet 回調傳遞出去
    override fun setGoals(planName: PlanPage, onSet: (fat: Float, carb: Float, protein: Float) -> Unit) {
        val goals = NutritionGoals.getGoals(planName)
        if (goals != null) {
            val (fatGoal, carbGoal, proteinGoal) = goals
            onSet(fatGoal, carbGoal, proteinGoal)
        }
    }



}