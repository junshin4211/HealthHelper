package com.example.healthhelper.dietary.viewmodel

import com.example.healthhelper.dietary.dataclasses.Diary

object DiaryViewModel {
    val diaries = mutableListOf<Diary>()

    fun addDiary(
        name:String,
        foodName:String,
        date:String,
        time:String,
    ){
        val diary = Diary(
            name = name,
            foodName = foodName ,
            date = date,
            time = time,
        )
        diaries.add(diary)
    }
}