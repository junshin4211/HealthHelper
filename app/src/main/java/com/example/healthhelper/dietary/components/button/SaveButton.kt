package com.example.healthhelper.dietary.components.button

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.healthhelper.R

@Composable
fun SaveButton(
    buttonModifier:Modifier=Modifier,
    onClick: ()->Unit,
    buttonColors:ButtonColors,
){
    Button(
        modifier = buttonModifier,
        onClick = onClick,
        colors = buttonColors,
    ) {
        Text(stringResource(R.string.save))
    }
}