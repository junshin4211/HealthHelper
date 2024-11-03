package com.example.healthhelper.dietary.components.combo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MultipleTextButton(
    outerButtonModifier: Modifier = Modifier,
    outerButtonColor: ButtonColors,
    leftTextButtonColors: ButtonColors,
    leftTextButtonOnClick: ()-> Unit,
    leftTextButtonText : String,
    rightTextButtonColors: ButtonColors,
    rightTextButtonOnClick: ()-> Unit,
    rightTextButtonText : String,

    ){
    Button(
        modifier = outerButtonModifier,
        onClick = {},
        colors = outerButtonColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
        ) {
            TextButton(
                onClick = leftTextButtonOnClick,
                colors = leftTextButtonColors,
            ) {
                Text(leftTextButtonText)
            }
            TextButton(
                onClick = rightTextButtonOnClick,
                colors = rightTextButtonColors,
            ) {
                Text(rightTextButtonText)
            }
        }
    }
}
