package com.example.healthhelper.dietary.components.button

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.healthhelper.R

@Composable
fun DeleteButton(
    onClick: ()->Unit,
){
    Button(
        onClick = onClick,
    ) {
        Text(stringResource(R.string.delete))
    }
}