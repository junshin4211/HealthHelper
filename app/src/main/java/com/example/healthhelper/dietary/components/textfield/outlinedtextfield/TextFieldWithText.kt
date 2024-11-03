package com.example.healthhelper.dietary.components.textfield.outlinedtextfield

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.healthhelper.attr.viewmodel.DefaultColorViewModel

@Composable
fun TextFieldWithText(
    leadingText: String,
    trailingText: String,
    textFieldColors: TextFieldColors = DefaultColorViewModel.textFieldWithTextColors,
) {
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Text(
                text = leadingText,
                modifier = Modifier
                    .padding(10.dp),
            )
        },
        trailingIcon = {
            Text(
                text = trailingText,
                modifier = Modifier
                    .padding(10.dp),
            )
        },
        readOnly = true,
        colors = textFieldColors,
    )
}