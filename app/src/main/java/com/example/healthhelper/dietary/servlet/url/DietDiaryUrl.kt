package com.example.healthhelper.dietary.servlet.url

import com.example.healthhelper.web.serverUrl

object DietDiaryUrl {
    val listAvailableFoodNameUrl : String
        get() = "${serverUrl}/dietDiary/food/listAvailableFoodsName"
}