package com.example.healthhelper.planpage.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.material.loadingindicator.LoadingIndicator


@Composable
fun LoadingIndicator(){
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        // indicator的顏色
        color = Color.Blue,
        // indicator後面的軌道顏色
        trackColor = Color.LightGray,
    )
}