package com.example.healthhelper.planpage.domain.usecase

import com.example.healthhelper.planpage.data.model.DiaryNutritionModel
import com.example.healthhelper.planpage.data.model.PlanModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * 計算飲食計畫的持續總天數。
 *
 * 這個函式會解析包含時間的日期字串，但僅比較日期部分以計算完整的天數差距。
 * 例如，從 "2024-05-20T22:00:00" 到 "2024-05-21T01:00:00" 會被計算為 1 天。
 *
 * @param plan 包含開始和結束日期時間的飲食計畫模型。
 * @return 回傳一個代表總天數的 `Long` 整數。
 */
private fun calculatePlanDurationInDays(plan: PlanModel): Long {
    // 假設日期時間格式為 "yyyy-MM-dd'T'HH:mm:ss" 或類似的 ISO 標準格式
    // 如果格式不同，需要相應地調整 DateTimeFormatter
    // 使用 LocalDateTime 來解析包含時間的字串
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    val startDate = LocalDateTime.parse(plan.startDateTime, formatter).toLocalDate()
    val endDate = LocalDateTime.parse(plan.endDateTime, formatter).toLocalDate()

    // 計算兩個日期之間的完整天數，回傳值已是 Long (整數)
    return ChronoUnit.DAYS.between(startDate, endDate) + 1
}


fun CalculatePlanAchieveRate(
    plan: PlanModel,
    diaryList: List<DiaryNutritionModel>
): Float {
    // 計算兩個日期之間的天數 (回傳值為 Long，即整數)
    val days = calculatePlanDurationInDays(plan)
    if (days <= 0) {
        // 如果計畫天數為0或負數，直接返回0，避免除以零的錯誤
        return 0f
    }

    val targetCalories = if (plan.Caloriesgoal <= 0) {
        // 如果目標熱量小於等於0，則視為無效目標，返回0
        return 0f
    } else {
        plan.Caloriesgoal
    }

    val planAchieveCount = diaryList.count{
        it.totalCalories >= targetCalories
    }

    return planAchieveCount.toFloat() / days
}
