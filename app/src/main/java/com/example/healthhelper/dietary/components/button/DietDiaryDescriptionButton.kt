package com.example.healthhelper.dietary.components.button

import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DietDiaryDescriptionButton(
    buttonModifier: Modifier = Modifier,
    onClick:  () -> Unit,
    buttonColors: ButtonColors,
    text: @Composable () -> Unit,
) {
    MyButton(
        buttonModifier = buttonModifier,
        onClick = onClick,
        buttonColors = buttonColors,
        text = text,
    )
}
