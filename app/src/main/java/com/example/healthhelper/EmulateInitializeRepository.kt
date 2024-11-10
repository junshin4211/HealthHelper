package com.example.healthhelper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.healthhelper.dietary.repository.DiaryRepository

@Composable
fun EmulateInitializeRepository(){
    val TAG = "tag_EmulateInitializeRepository"

    LaunchedEffect(Unit) {
        setUserId(2)
    }
}

suspend fun setUserId(userId:Int){
    DiaryRepository.setUserId(userId)
}