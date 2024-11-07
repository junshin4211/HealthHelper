package com.example.healthhelper.dietary.servlet.url

import com.example.healthhelper.web.serverUrl

object DietDiaryUrl {
    val listAvailableFoodNameUrl : String
        get() = "${serverUrl}/dietDiary/food/listAvailableFoodsName"

    val queryByDateUrl : String
        get() = "${serverUrl}/dietDiary/query/byDate"

    val insertDietDiary : String
        get() = "${serverUrl}/dietDiary/insert/insertDietDiary"
}