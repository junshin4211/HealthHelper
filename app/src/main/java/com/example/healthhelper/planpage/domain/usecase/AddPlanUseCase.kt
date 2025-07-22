package com.example.healthhelper.planpage.domain.usecase

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.example.healthhelper.R
import com.example.healthhelper.planpage.data.model.AddPlanModel
import com.example.healthhelper.planpage.domain.model.DateRangeTitle
import com.example.healthhelper.planpage.domain.model.NutritionType
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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
 * @return onSetNutritionGram 將換算的公克數呼叫函式回去 */
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
    if ((fatgoal + carbongoal + proteingoal).toDouble() != 1.0) {
        return R.string.invalidNutritionGoal
    }
    if (Caloriesgoal < 0) {
        return R.string.invalidCaloriesGoal
    }

    return null
}
