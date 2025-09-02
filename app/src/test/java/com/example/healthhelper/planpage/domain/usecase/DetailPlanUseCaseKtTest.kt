package com.example.healthhelper.planpage.domain.usecase

import com.example.healthhelper.planpage.data.model.DiaryNutritionModel
import com.example.healthhelper.planpage.data.model.PlanModel
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * 對 DetailPlanUseCase.kt 中的 CalculatePlanAchieveRate 函式進行單元測試。
 *
 * 假設：傳入此函式的 diaryList 已經由 Repository 或 ViewModel 根據計畫日期範圍預先篩選過。
 */
class CalculatePlanAchieveRateTest {

    // 測試案例 1: 正常情況
    @Test
    fun `calculatePlanAchieveRate - 當傳入篩選過的日記列表時，應返回正確比率`() {
        // 準備 (Arrange)
        // 一個從 5/20 到 5/26 的計畫，共7天
        val plan = PlanModel(
            userDietPlanId = 1,
            startDateTime = "2024-05-20T00:00:00",
            endDateTime = "2024-05-26T23:59:59",
            categoryId = 1, categoryName = "Test", finishstate = 0,
            fatgoal = 50f, carbongoal = 200f, proteingoal = 100f,
            Caloriesgoal = 2000f // 目標2000大卡
        )

        // 模擬 Repository 已篩選過的日記列表 (不包含 5/19 的日記)
        val filteredDiaryList = listOf(
            createDummyDiary(1, "2024-05-20T10:00:00", 2100f), // 達標
            createDummyDiary(2, "2024-05-21T10:00:00", 1800f), // 未達標
            createDummyDiary(3, "2024-05-22T10:00:00", 2000f), // 達標
            createDummyDiary(4, "2024-05-23T10:00:00", 1950f), // 未達標
            createDummyDiary(5, "2024-05-24T10:00:00", 2500f)  // 達標
        )

        // 預期結果: 3天達標 / 7天總計畫長度
        val expectedRate = 3f / 7f

        // 執行 (Act)
        val actualRate = CalculatePlanAchieveRate(plan, filteredDiaryList)

        // 斷言 (Assert)
        assertEquals(expectedRate, actualRate, 0.001f)
    }

    // 測試案例 2: 邊界情況 - 沒有任何日記達標
    @Test
    fun `calculatePlanAchieveRate - 沒有任何日記達標，應返回0`() {
        // 準備
        val plan = PlanModel(
            userDietPlanId = 2,
            startDateTime = "2024-06-01T00:00:00",
            endDateTime = "2024-06-03T23:59:59", // 3天計畫
            categoryId = 1, categoryName = "Test", finishstate = 0,
            fatgoal = 50f, carbongoal = 200f, proteingoal = 100f,
            Caloriesgoal = 2500f
        )
        val diaryList = listOf(
            createDummyDiary(7, "2024-06-01T10:00:00", 1800f),
            createDummyDiary(8, "2024-06-02T10:00:00", 2499f)
        )
        val expectedRate = 0f

        // 執行
        val actualRate = CalculatePlanAchieveRate(plan, diaryList)

        // 斷言
        assertEquals(expectedRate, actualRate, 0.001f)
    }

    // 測試案例 3: 邊界情況 - 計畫天數為0
    @Test
    fun `calculatePlanAchieveRate - 計畫天數小於1天，應返回0以避免除以零`() {
        // 準備
        // 一個當天開始當天結束的計畫，根據我們的算法，天數為 1
        val plan = PlanModel(
            userDietPlanId = 3,
            startDateTime = "2024-07-01T10:00:00",
            endDateTime = "2024-07-01T20:00:00",
            categoryId = 1, categoryName = "Test", finishstate = 0,
            fatgoal = 50f, carbongoal = 200f, proteingoal = 100f,
            Caloriesgoal = 2000f
        )
        val diaryList = listOf(createDummyDiary(9, "2024-07-01T15:00:00", 2100f))

        // 預期結果: 1天達標 / 1天總計畫長度
        val expectedRate = 1f / 1f

        // 執行
        val actualRate = CalculatePlanAchieveRate(plan, diaryList)

        // 斷言
        assertEquals(expectedRate, actualRate, 0.001f)
    }

    // 測試案例 4: 邊界情況 - 目標熱量為0
    @Test
    fun `calculatePlanAchieveRate - 當目標熱量為0時，應返回0`() {
        // 準備
        val plan = PlanModel(
            userDietPlanId = 4,
            startDateTime = "2024-08-01T00:00:00",
            endDateTime = "2024-08-03T23:59:59",
            categoryId = 1, categoryName = "Test", finishstate = 0,
            fatgoal = 50f, carbongoal = 200f, proteingoal = 100f,
            Caloriesgoal = 0f // 目標熱量為0
        )
        val diaryList = listOf(
            createDummyDiary(10, "2024-08-01T10:00:00", 1800f)
        )
        val expectedRate = 0f

        // 執行
        val actualRate = CalculatePlanAchieveRate(plan, diaryList)

        // 斷言
        assertEquals(expectedRate, actualRate, 0.001f)
    }

    // 輔助函式，用來快速建立測試用的日記物件
    private fun createDummyDiary(id: Int, date: String, calories: Float): DiaryNutritionModel {
        return DiaryNutritionModel(
            diaryId = id, userId = 1, createDate = date, totalFat = 0f, totalCarbon = 0f,
            totalFiber = 0f, totalSugar = 0f, totalSodium = 0f, totalProtein = 0f,
            totalCalories = calories
        )
    }
}
