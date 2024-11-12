package com.example.healthhelper.plan.usecase

import android.util.Log
import com.example.healthhelper.plan.CateGoryId
import com.example.healthhelper.plan.NutritionGoals
import com.example.healthhelper.plan.PlanPage
import com.example.healthhelper.plan.model.DiaryNutritionModel
import com.example.healthhelper.plan.model.PlanModel
import com.example.healthhelper.plan.viewmodel.ManagePlanVM
import com.example.healthhelper.plan.viewmodel.PlanVM
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class PlanUCImpl : PlanUC {
    override fun dateTimeFormat(datetime: Any?): String {
        val output = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        output.timeZone = java.util.TimeZone.getTimeZone("Asia/Taipei") // 設定輸出時區為台北時區

        return try {
            val date = when (datetime) {
                is Timestamp -> java.sql.Date(datetime.time) // Convert Timestamp to java.sql.Date
                is Date -> datetime // java.util.Date can directly be used
                is LocalDate -> Date.from(datetime.atStartOfDay(ZoneId.of("Asia/Taipei")).toInstant()) // Convert LocalDate to java.util.Date
                is LocalDateTime -> Date.from(datetime.atZone(ZoneId.of("Asia/Taipei")).toInstant()) // Convert LocalDateTime to java.util.Date
                is ZonedDateTime -> Date.from(datetime.withZoneSameInstant(ZoneId.of("Asia/Taipei")).toInstant()) // Convert ZonedDateTime to java.util.Date
                is Long -> java.sql.Date(datetime) // Convert timestamp in milliseconds to java.sql.Date
                is String -> {
                    // 多種輸入格式處理
                    val inputDateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
                    inputDateTimeFormat.timeZone = java.util.TimeZone.getTimeZone("Asia/Taipei")

                    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    inputDateFormat.timeZone = java.util.TimeZone.getTimeZone("Asia/Taipei")

                    val apiDateFormat = SimpleDateFormat("MMM d, yyyy, h:mm:ss a", Locale.US)
                    apiDateFormat.timeZone = java.util.TimeZone.getTimeZone("Asia/Taipei")

                    // 嘗試使用多種格式解析日期字串
                    try {
                        inputDateTimeFormat.parse(datetime)
                    } catch (e1: Exception) {
                        try {
                            inputDateFormat.parse(datetime)
                        } catch (e2: Exception) {
                            apiDateFormat.parse(datetime)
                        }
                    }
                }
                null -> return "9999-99-99"
                else -> return "9999-99-99"
            }

            // Format output as "yyyy-MM-dd"
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
    override fun InitialDefaultGoal(planName: PlanPage,
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

    override fun customPlanInitial(planName: PlanPage, onSetCateId: (cateId: Int) -> Unit) {
        val cateId = CateGoryId.getCateId(planName)
        if (cateId != null) {
            onSetCateId(cateId)
        }
    }

    override fun fetchSingle(planVM: PlanVM) {
        planVM.getPlan()
        planVM.getCompletePlan()
    }

    override fun fetchList(managePlanVM: ManagePlanVM) {
        managePlanVM.getPlanList()
        managePlanVM.getCompletePlanList()
    }

    override fun percentToGram(nutrition: String ,calorie: Float,percent: Float ,onSetGram: (grams:Float) -> Unit) {
        if(calorie >= 1200f)
        {
            when(nutrition)
            {
                "carb","protein" ->{
                    val Cals = calorie * (percent/100)
                    onSetGram(Cals/4)
                }
                "fat" ->{
                    val Cals = calorie * (percent/100)
                    onSetGram(Cals/9)
                }

            }
        }
    }

    override fun formatToOneF(value: Float): String {
        return String.format(Locale.US,"%.1f", value)
    }

    override fun calculateTotalNutrition(nutritionList: List<DiaryNutritionModel>): DiaryNutritionModel {
        // 初始化累加變數
        var totalFat = 0.0f
        var totalCarbon = 0.0f
        var totalFiber = 0.0f
        var totalSugar = 0.0f
        var totalSodium = 0.0f
        var totalProtein = 0.0f
        var totalCalories = 0.0f

        // 遍歷列表中的每一個 DiaryNutritionModel
        nutritionList.forEach { item ->
            totalFat += item.totalFat
            totalCarbon += item.totalCarbon
            totalFiber += item.totalFiber
            totalSugar += item.totalSugar
            totalSodium += item.totalSodium
            totalProtein += item.totalProtein
            totalCalories += item.totalCalories
        }

        // 返回新的 DiaryNutritionModel，包含加總結果
        return DiaryNutritionModel(
            totalFat = totalFat,
            totalCarbon = totalCarbon,
            totalFiber = totalFiber,
            totalSugar = totalSugar,
            totalSodium = totalSodium,
            totalProtein = totalProtein,
            totalCalories = totalCalories
        )
    }

    override fun averageNutrition(size: Int, goal: Float, totalcurrent: Float): String {
        val averagecurrent = totalcurrent / size
        val percentage = (averagecurrent / goal) * 100
        val formattedPercentage = "%.1f".format(percentage)
        return formattedPercentage
    }

    override fun isTimeAfterToday(timeStamp: Timestamp): Boolean {
        // 使用台北時區
        val taipeiZoneId = ZoneId.of("Asia/Taipei")

        // 取得台北當前的當地時間
        val currentDateTimeInTaipei = LocalDateTime.now(taipeiZoneId)

        // 將 Timestamp 轉換為 LocalDateTime
        val timestampDateTime = timeStamp.toInstant()
            .atZone(taipeiZoneId)
            .toLocalDateTime()

        // 比較當地時間是否大於傳進來的 Timestamp
        return currentDateTimeInTaipei.isAfter(timestampDateTime)
    }

    override fun dayNutrition(goal: Float, totalcurrent: Float): String {
        val percentage = (totalcurrent / goal) * 100
        val formattedPercentage = "%.1f".format(percentage)
        return formattedPercentage
    }

}