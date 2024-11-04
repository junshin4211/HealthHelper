package com.example.healthhelper.attr.viewmodel

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

object DefaultShapeViewModel {
    val roundedCornerShape : RoundedCornerShape
        @Composable
        get() = RoundedCornerShape(
            2.dp,
            2.dp,
            2.dp,
            2.dp,
        )

}