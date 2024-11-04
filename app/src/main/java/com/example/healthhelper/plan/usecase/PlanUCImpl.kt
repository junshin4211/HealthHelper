package com.example.healthhelper.plan.usecase

import android.util.Log
import com.example.healthhelper.plan.CateGoryId
import com.example.healthhelper.plan.NutritionGoals
import com.example.healthhelper.plan.PlanPage
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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


            output.format(date)
        } catch (e: Exception) {
            Log.d("tag_DateTimeformat", "DateTimeformat: $e")
            "9999-99-99"
        }
    }

    override fun stringToTimeStamp(datetime: LocalDateTime): Timestamp? {
        return try {
            // 定義時間格式
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            // 將 LocalDateTime 格式化為 Timestamp 所需的字串
            val formattedDateTime = datetime.format(formatter)
            // 轉換為 Timestamp
            val timestamp = Timestamp.valueOf(formattedDateTime)
            Log.d("tag_stringToTimeStamp", "stringToTimeStamp: $timestamp")
            timestamp
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("tag_stringToTimeStamp", "stringToTimeStamp: $e")
            null
        }
    }

    // 定義函數，將目標值通過 onSet 回調傳遞出去
    override fun planInitial(planName: PlanPage,
                             onSetGoal: (fat: Float, carb: Float, protein: Float) -> Unit,
                             onSetCateId: (cateId: Int) -> Unit) {
        val goals = NutritionGoals.getGoals(planName)
        if (goals != null) {
            val (fatGoal, carbGoal, proteinGoal) = goals
            onSetGoal(fatGoal, carbGoal, proteinGoal)
        }

        val cateId = CateGoryId.getCateId(planName)
        if (cateId != null) {
            onSetCateId(cateId)
        }
    }

}