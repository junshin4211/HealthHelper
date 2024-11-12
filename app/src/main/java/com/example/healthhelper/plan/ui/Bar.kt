package com.example.healthhelper.plan.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.healthhelper.R
import kotlinx.coroutines.delay

@Composable
fun CreateAnimationBar(
    goal: Float,
    currentvalue: Float,
    barwidth: Dp,
    barheight: Dp,
    backgroundColor: Int = R.color.gray_250,
    dynamicColor: Int = R.color.primarycolor,
    offset: Dp = 0.dp
) {
    // 使用 remember 來儲存動畫進度的狀態
    var animatedValue by remember { mutableFloatStateOf(0f) }

    // 使用 LaunchedEffect 在組件首次啟動時觸發動畫
    LaunchedEffect(currentvalue) {
        animatedValue = 0f // 初始化為 0
        val increment = currentvalue / 10 // 每次增加 1/10
        while (animatedValue < currentvalue) {
            animatedValue += increment // 每次增加一個步長
            delay(100) // 控制增量之間的時間間隔
        }
        animatedValue = currentvalue // 確保最終值達到 currentvalue
    }

    // 計算目標比例
    val progress = if (goal > 0) animatedValue / goal else 0f

    // 動態的進度條顯示
    Box(
        modifier = Modifier
            .width(barwidth)
            .height(barheight)
            .offset(x = offset)
            .clip(RoundedCornerShape(30.dp))
            .background(colorResource(backgroundColor)) // Bar 背景
    ) {
        // 根據進度顯示的動畫 Bar
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress.coerceIn(0f, 1f)) // 使用計算出的進度比例
                .background(colorResource(dynamicColor)) // Bar 顏色
        )
    }
}

