package com.example.healthhelper.planpage.domain.usecase

import android.util.Log
import androidx.annotation.StringRes
import com.example.healthhelper.R
import com.example.healthhelper.planpage.domain.model.DateRangeTitle
import com.example.healthhelper.planpage.domain.model.NutritionType
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

/** 轉換日期格式成 yyyy-MM-dd
 * @return 回傳字串 失敗為空字串*/
fun formatMillisToDateString(millis: Long?, zoneId: ZoneId = ZoneId.systemDefault()): String {
    if (millis == null) return ""
    return Instant.ofEpochMilli(millis)
        .atZone(zoneId)
        .toLocalDate()
        .format(DateTimeFormatter.ISO_LOCAL_DATE)
}

/** 轉換日期格式成 yyyy-MM-dd HH:mm:ss
 * @return 回傳為字串 , null 表示轉換失敗
 */
fun formatMillisToISO(millis: Long?): String? {
    if (millis == null) return null
    val zoneId = ZoneId.systemDefault()
    return Instant.ofEpochMilli(millis)
        .atZone(zoneId)
        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}

/** 根據 DateRangeTitle 轉換成天數
 * @param dateRange  e.x. DateRangeTitle.AWeek
 * @return 回傳一組時間
 */
fun calculateDateMillisRange(
    dateRange: DateRangeTitle,
    startDateReference: LocalDate = LocalDate.now()
): Pair<Long, Long>? { // 返回 Pair<StartMillis, EndMillis>?
    val currentTime = LocalTime.now(ZoneId.systemDefault())

    val startMillis = ZonedDateTime.of(startDateReference,currentTime, ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    val endDateReference: LocalDate = when (dateRange) {
        DateRangeTitle.AWeek -> startDateReference.plusWeeks(1).minusDays(1) // 一周後的前一天
        DateRangeTitle.HalfMonth -> startDateReference.plusDays(14) // 大約半個月
        DateRangeTitle.AMonth -> startDateReference.plusMonths(1).minusDays(1)
        DateRangeTitle.ThreeMonth -> startDateReference.plusMonths(3).minusDays(1)
        DateRangeTitle.SixMonth -> startDateReference.plusMonths(6).minusDays(1)
        else -> return null
        // 添加其他 case
    }

    val endMillis = ZonedDateTime.of(endDateReference,
        LocalTime.of(23, 59, 59),
        ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    return Pair(startMillis, endMillis)
}

/** 透過卡路里及選擇的計畫轉換轉換個個營養素所需公克數
 * @param calories 每日消耗的卡路里
 * @param plan 選擇的計畫 e.x. DietPlanType.HIGH_PROTEIN
 * @return onSetNutritionGram 呼叫回掉函數,將換算的公克數傳遞 */
fun calculateNutritionGrams(
    calories: Float,
    @StringRes plan: Int,
    onSetNutritionGram: (fatGram: Float, carbGram: Float, proteinGram: Float) -> Unit
) {
    val goals = NutritionGoal.getGoals(plan)

    if (goals != null) {

        val (fatGoal, carbGoal, proteinGoal) = goals
        val fatGram = calories * fatGoal
        val carbGram = calories * carbGoal
        val proteinGram = calories * proteinGoal

        onSetNutritionGram(fatGram, carbGram, proteinGram)
    }
}

/** 透過選擇的計畫及營養素類別轉換成每日所需營養素之比例
 * @param plan 選擇的計畫 e.x. DietPlanType.HIGH_PROTEIN
 * @param nutritionType 營養素
 * @return 回傳 Int 失敗為 null */
fun calculateNutritionGoals(
    @StringRes plan: Int,
    nutritionType: NutritionType,
): Int? {
    val goals = NutritionGoal.getGoals(plan) ?: return null

    return when (nutritionType) {
        NutritionType.FAT -> (goals.first * 100).toInt()
        NutritionType.CARBOHYDRATE -> (goals.second * 100).toInt()
        NutritionType.PROTEIN -> (goals.third * 100).toInt()
    }
}

/** 透過選擇的計畫及營養素類別轉換成每日所需營養素之比例
 */
fun calculateNutrition(
    nutritionType: NutritionType,
    changedValue: Float,
    calories: Int,
    carbPercent: Float,
    proteinPercent: Float,
    fatPercent: Float,
    onsetGoal: (fatGoal: Float, carbGoal: Float, proteinGoal: Float) -> Unit,
    onSetGram: (fatGram: Float, carbGram: Float, proteinGram: Float) -> Unit
){
    // 1. 標準化輸入百分比。由於changedValue是10的倍數，roundToInt影響不大，但保留是好的。
    // coerceIn確保它在0-100。
    val userTargetPercent = changedValue.roundToInt().coerceIn(0, 100)

    // 將當前百分比也四捨五入到最近的整數（不一定是10的倍數，但這是起點）
    var carbP = carbPercent.roundToInt().coerceIn(0, 100)
    var proteinP = proteinPercent.roundToInt().coerceIn(0, 100)
    var fatP = fatPercent.roundToInt().coerceIn(0, 100)

    // 2. 根據用戶操作更新對應的整數百分比
    when (nutritionType) {
        NutritionType.CARBOHYDRATE -> {
            carbP = userTargetPercent
            // 碳水變動，蛋白質和脂肪嘗試平均分配剩餘
            val remainingForPAndF = (100 - carbP).coerceAtLeast(0)
            // 由於目標是整數，可以這樣分配。如果希望結果更傾向於10的倍數，可以後續調整。
            proteinP = (remainingForPAndF / 2f).roundToInt()
            fatP = 100 - carbP - proteinP
            Log.d("AddPlanUseCase","Enter Carb carbP: $carbP, proteinP: $proteinP, fatP: $fatP")
        }
        NutritionType.PROTEIN -> {
            // carbP 使用 currentCarbPercent 的四捨五入值，保持不變
            proteinP = userTargetPercent.coerceIn(0, (100 - carbP).coerceAtLeast(0))
            fatP = (100 - carbP - proteinP).coerceAtLeast(0)
            Log.d("AddPlanUseCase","Enter Protein carbP: $carbP, proteinP: $proteinP, fatP: $fatP")
        }
        NutritionType.FAT -> {
            // carbP 使用 currentCarbPercent 的四捨五入值，保持不變
            fatP = userTargetPercent.coerceIn(0, (100 - carbP).coerceAtLeast(0))
            proteinP = (100 - carbP - fatP).coerceAtLeast(0)
            Log.d("AddPlanUseCase","Enter Fat carbP: $carbP, proteinP: $proteinP, fatP: $fatP")
        }
    }

    val carbGram = ((carbP.toFloat() / 100f) * calories) / 4f
    val proteinGram = ((proteinP.toFloat() / 100) * calories) / 4f
    val fatGram = ((fatP.toFloat() / 100) * calories) / 9f
    Log.d("AddPlanUseCase","current calories: $calories")
    Log.d("AddPlanUseCase","Enter carbGram: $carbGram, proteinGram: $proteinGram, fatGram: $fatGram")

    onSetGram(fatGram, carbGram, proteinGram)
    Log.d("AddPlanUseCase","Enter fatP: $fatP, carbP: $carbP, proteinP: $proteinP")
    onsetGoal(fatP.toFloat(), carbP.toFloat(), proteinP.toFloat())
}

fun validationPlanMessage(
    userId: Int,
    startDateTime: Long?,
    endDateTime: Long?,
    categoryId: Int,
    finishstate: Int,
    fatgoal: Float,
    carbongoal: Float,
    proteingoal: Float,
    Caloriesgoal: Float
): Int?{
    if (userId < 0) {
        return  R.string.invalidUserId
    }
    // 日期選擇檢查
    if (startDateTime == null || endDateTime == null) {
        return R.string.invalidDateTime
    }
    if (startDateTime >= endDateTime) {
        return R.string.invalidDateTime2
    }
    if (categoryId < 0) {
        return R.string.invalidCategoryId
    }
    if (finishstate != 0) {
        return R.string.invalidFinishState
    }
    if (fatgoal < 0 || carbongoal < 0 || proteingoal < 0) {
        return R.string.invalidNutritionGoal
    }
    if ((fatgoal + carbongoal + proteingoal) != 100f) {
        return R.string.invalidNutritionGoal
    }
    if (Caloriesgoal < 0) {
        return R.string.invalidCaloriesGoal
    }

    return null
}
