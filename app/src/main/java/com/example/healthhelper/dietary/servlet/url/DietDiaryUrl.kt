package com.example.healthhelper.dietary.servlet.url

import com.example.healthhelper.web.serverUrl

object DietDiaryUrl {
    val listAvailableFoodNameUrl : String
        get() = "${serverUrl}/dietDiary/food/listAvailableFoodsName"

    val queryByDateUrl : String
        get() = "${serverUrl}/dietDiary/query/byDate"

    val insertDietDiaryUrl : String
        get() = "${serverUrl}/dietDiary/insert/insertDietDiary"

    val tryToInsertDiaryDescriptionUrl : String
        get() = "${serverUrl}/dietDiary/diaryDescription/tryToInsert"

    val selectDiaryDescriptionUrl : String
        get() = "${serverUrl}/dietDiary/diaryDescription/tryToInsert"
}