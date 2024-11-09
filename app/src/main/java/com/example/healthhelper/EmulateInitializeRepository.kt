package com.example.healthhelper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.healthhelper.dietary.repository.DiaryInfoUpdatorRepository
import com.example.healthhelper.dietary.repository.FoodDiaryRepository

@Composable
fun EmulateInitializeRepository(){
    val TAG = "tag_EmulateInitializeRepository"

    LaunchedEffect(Unit) {
        setUserId(2)
    }
}

suspend fun setUserId(userId:Int){
    DiaryInfoUpdatorRepository.setUserId(userId)
    DiaryInfoUpdatorRepository.setUserId(userId)
    FoodDiaryRepository.setUserId(userId)
}