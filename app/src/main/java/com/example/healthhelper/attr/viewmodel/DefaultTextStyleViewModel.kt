package com.example.healthhelper.attr.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

object DefaultTextStyleViewModel {
    val outlinedTextFieldTextStyle: TextStyle
        get() = TextStyle(
            textAlign = TextAlign.Left,
            fontSize = 16.sp,
            color = Color.Black,
        )
}