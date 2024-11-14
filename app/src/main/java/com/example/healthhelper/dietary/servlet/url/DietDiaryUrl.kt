package com.example.healthhelper.dietary.servlet.url

import com.example.healthhelper.web.serverUrl

object DietDiaryUrl {

    val listAvailableFoodNameUrl: String
        get() = "${serverUrl}/dietDiary/food/search/listAvailableFoodsName"

    val insertDietDiaryUrl: String
        get() = "${serverUrl}/dietDiary/diary/insert/insertDietDiary"

    val tryToInsertDiaryDescriptionUrl: String
        get() = "${serverUrl}/dietDiary/diaryDescription/tryToInsert"

    val selectDiaryDescriptionUrl: String
        get() = "${serverUrl}/dietDiary/diaryDescription/select/selectDiaryDescriptionByDiaryId"

    val updateDiaryInfoUrl: String
        get() = "${serverUrl}/dietDiary/diary/update/updateDietDiary"

    val updateFoodItemByDiaryIdAndFoodId: String
        get() = "${serverUrl}/dietDiary/foodItem/update/updateFoodItemByDiaryIdAndFoodId"

    val deleteFoodItemByDiaryIdAndFoodIdUrl:String
        get() = "${serverUrl}/dietDiary/foodItem/delete/deleteFoodItemByDiaryIdAndFoodId"

    val tryToInsertFoodItemUrl: String
        get() = "${serverUrl}/dietDiary/foodItem/insert/tryToInsertFoodItem"

    val selectFoodIdByFoodNameUrl: String
        get() = "${serverUrl}/dietDiary/food/select/selectFoodIdByFoodName"

    val selectFoodNameByFoodIdUrl: String
        get() = "${serverUrl}/dietDiary/foodName/select/selectFoodNameByFoodId"

    val selectFoodItemByDiaryIdAndMealCategoryIdUrl: String
        get() = "${serverUrl}/dietDiary/foodItem/select/selectFoodItemByDiaryIdAndMealCategoryId"

    val selectDiaryByUserIdAndDateUrl: String
        get() = "${serverUrl}/dietDiary/diary/select/selectByUserIdAndDate"

    val selectFoodItemByDiaryIdUrl: String
        get() = "${serverUrl}/dietDiary/foodItem/select/selectFoodItemByDiaryId"
}