package com.example.healthhelper.dietary.components.button

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MyButton(
    buttonModifier:Modifier=Modifier,
    onClick: ()->Unit,
    buttonColors:ButtonColors,
    text:@Composable ()->Unit
){
    Button(
        modifier = buttonModifier,
        onClick = onClick,
        colors = buttonColors,
    ) {
        text()
    }
}