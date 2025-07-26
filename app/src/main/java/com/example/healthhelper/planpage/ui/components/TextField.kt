package com.example.healthhelper.planpage.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.healthhelper.R

@Composable
fun OutlinedTextField_Plan(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable (() -> Unit)? =  null,
    keyboardType: KeyboardOptions? = null
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { placeholder ?: "" },
        modifier = modifier,
        singleLine = true,
        keyboardOptions = keyboardType ?: KeyboardOptions(keyboardType = KeyboardType.Text),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            // 背景色
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            // 邊框顏色
            unfocusedBorderColor = Color.DarkGray,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@Composable
fun OutlinedTextField_Plan(
    modifier: Modifier,
    value: String,
    placeholder: @Composable (() -> Unit)? =  null,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
){
    var showText by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = {},
        placeholder = placeholder ,
        modifier = Modifier.clickable{ showText = !showText },
        readOnly = readOnly,
        singleLine = true,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            // 背景色
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            // 邊框顏色
            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f),
            focusedBorderColor = MaterialTheme.colorScheme.primary,
        )
    )
    if (showText) {
        Text("If you see this means the clickable works", color = Color.Red)
    }
}