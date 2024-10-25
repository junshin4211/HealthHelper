package com.example.healthhelper.plan.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.healthhelper.R

class CustomTextField {
    @Composable
    fun <T> TextFieldWithBorder(
        value: T,
        onValueChange: (T) -> Unit,
        convertFromString: (String) -> T?,
        label: String,
        width: Dp = 200.dp,
        keyboardType: KeyboardType = KeyboardType.Number
    ) {
        TextField(
            value = value.toString(),
            onValueChange = { newValue ->
                val convertedValue = convertFromString(newValue)
                if (convertedValue != null) {
                    onValueChange(convertedValue)
                }
            },
            singleLine = true,
            label = { Text(text = label) },
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = colorResource(id = R.color.primarycolor),
                    shape = RoundedCornerShape(20.dp)
                )
                .width(width),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
        )
    }


}