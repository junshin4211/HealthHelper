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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.healthhelper.R

class CustomTextField {
    @Composable
    fun TextFieldWithBorder(
        value: Float,
        onValueChange: (Float) -> Unit,
        label: String,
        width: Dp = 200.dp,
        keyboardType: KeyboardType = KeyboardType.Number
    ) {
        TextField(
            value = value.toInt().toString(), // 顯示為 Int 格式的 String
            onValueChange = { newValue ->
                val convertedValue = newValue.toFloatOrNull()
                if (convertedValue != null) {
                    onValueChange(convertedValue) // 將 Float 值傳回外部
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