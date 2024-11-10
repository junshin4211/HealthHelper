package com.example.healthhelper.dietary.servlet.url

import com.example.healthhelper.web.serverUrl

object DietDiaryUrl {

    val listAvailableFoodNameUrl: String
        get() = "${serverUrl}/dietDiary/food/listAvailableFoodsName"

    val insertDietDiaryUrl: String
        get() = "${serverUrl}/dietDiary/insert/insertDietDiary"

    val tryToInsertDiaryDescriptionUrl: String
        get() = "${serverUrl}/dietDiary/diaryDescription/tryToInsert"

    val selectDiaryDescriptionUrl: String
        get() = "${serverUrl}/dietDiary/diaryDescription/query"

    val updateDiaryInfoUrl: String
        get() = "${serverUrl}/dietDiary/update/updateDietDiary"

    val updateMealCategoryIDUrl: String
        get() = "${serverUrl}/dietDiary/foodItem/mealCategoryID/update"

    val tryToInsertFoodItemUrl: String
        get() = "${serverUrl}/dietDiary/foodItem/tryToInsert"

    val selectFoodIdByFoodNameUrl: String
        get() = "${serverUrl}/dietDiary/food/select/selectFoodIdByFoodName"

    val selectFoodNameByFoodIdUrl: String
        get() = "${serverUrl}/dietDiary/foodName/selectFoodNameByFoodId"

    val selectFoodItemByDiaryIdAndMealCategoryIdUrl: String
        get() = "${serverUrl}/dietDiary/foodItem/selectFoodItemByDiaryIdAndMealCategoryId"

    val selectDiaryByUserIdAndDateUrl: String
        get() = "${serverUrl}/dietDiary/diary/selectByUserIdAndDate"

    val selectFoodItemByDiaryIdUrl: String
        get() = "${serverUrl}/dietDiary/foodItem/selectFoodItemByDiaryId"
}