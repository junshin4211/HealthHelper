package com.example.healthhelper.planpage.domain.usecase

import androidx.annotation.StringRes
import com.example.healthhelper.R
import com.example.healthhelper.planpage.ui.DateRangeTitle
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

//轉換日期格式 yyyy-MM-dd
fun formatMillisToDateString(millis: Long?, zoneId: ZoneId = ZoneId.systemDefault()): String {
    if (millis == null) return ""
    return Instant.ofEpochMilli(millis)
        .atZone(zoneId)
        .toLocalDate()
        .format(DateTimeFormatter.ISO_LOCAL_DATE)
}

// 輔助函數：根據 DateRangeTitle 計算開始和結束日期的毫秒數
// startDateReference 通常是 LocalDate.now()
fun calculateDateMillisRange(
    dateRange: DateRangeTitle,
    startDateReference: LocalDate = LocalDate.now()
): Pair<Long, Long>? { // 返回 Pair<StartMillis, EndMillis>?
    val startMillis = startDateReference
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    val endDate: LocalDate = when (dateRange) {
        DateRangeTitle.AWeek -> startDateReference.plusWeeks(1).minusDays(1) // 一周後的前一天
        DateRangeTitle.HalfMonth -> startDateReference.plusDays(14) // 大約半個月
        DateRangeTitle.AMonth -> startDateReference.plusMonths(1).minusDays(1)
        DateRangeTitle.ThreeMonth -> startDateReference.plusMonths(3).minusDays(1)
        DateRangeTitle.SixMonth -> startDateReference.plusMonths(6).minusDays(1)
        else -> return null
        // 添加其他 case
    }

    val endMillis = endDate
        //.atStartOfDay(ZoneId.systemDefault())
        .atTime(23, 59, 59)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    return Pair(startMillis, endMillis)
}

//
fun calculateNutritionGrams(
    calories: Float,
    @StringRes plan: Int,
    onSetNutritionGram: (fatGram: Float, carbGram: Float, proteinGram: Float) -> Unit
){
    val goals = NutritionGoal.getGoals(plan)

    if(goals != null){

        val (fatGoal, carbGoal, proteinGoal) =goals
        val fatGram = calories * fatGoal
        val carbGram = calories * carbGoal
        val proteinGram = calories * proteinGoal

        onSetNutritionGram(fatGram, carbGram, proteinGram)
    }
}

fun calculateNutritionGoals(
    @StringRes plan: Int,
    @StringRes nutrition: Int,
):Float?{
    val goals = NutritionGoal.getGoals(plan) ?: return null

    return when(nutrition){
        R.string.fat -> goals.first
        R.string.carb -> goals.second
        R.string.protein -> goals.third
        else -> null}
}
