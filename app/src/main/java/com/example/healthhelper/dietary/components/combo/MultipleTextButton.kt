package com.example.healthhelper.dietary.components.combo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun MultipleTextButton(
    outerButtonModifier: Modifier = Modifier,
    outerButtonColor: ButtonColors,
    leftTextButtonColors: ButtonColors,
    leftTextButtonOnClick: () -> Unit,
    leftTextButtonText: String,
    leftTextButtonTextFontSize: TextUnit,
    rightTextButtonColors: ButtonColors,
    rightTextButtonOnClick: () -> Unit,
    rightTextButtonText: String,
    rightTextButtonTextFontSize: TextUnit,
) {
    Box(
        modifier = outerButtonModifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            TextButton(
                onClick = leftTextButtonOnClick,
                colors = leftTextButtonColors,
                modifier = Modifier.padding(0.dp),
            ) {
                Text(
                    text = leftTextButtonText,
                    fontSize = leftTextButtonTextFontSize,
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            TextButton(
                onClick = rightTextButtonOnClick,
                colors = rightTextButtonColors,
                modifier = Modifier.padding(0.dp),
            ) {
                Text(
                    text = rightTextButtonText,
                    fontSize = rightTextButtonTextFontSize,
                )
            }
        }
    }
}
