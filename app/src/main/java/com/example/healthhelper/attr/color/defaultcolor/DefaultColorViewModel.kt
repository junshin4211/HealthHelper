package com.example.healthhelper.attr.color.defaultcolor

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.healthhelper.R

object DefaultColorViewModel {
    val outlinedTextFieldDefaultColors
        @Composable
        get() = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Blue,
            unfocusedTextColor = Color.Gray,
            disabledTextColor = colorResource(R.color.very_light_black),
            errorTextColor = Color.Red,
            focusedContainerColor = Color.Blue,
            unfocusedContainerColor = Color.Gray,
            disabledContainerColor = colorResource(R.color.very_light_black),
            errorContainerColor = Color.Red,
            cursorColor = colorResource(R.color.blue01),
            errorCursorColor = Color.Red,
            selectionColors = null,
            focusedBorderColor = Color.Blue,
            unfocusedBorderColor = Color.Gray,
            disabledBorderColor = colorResource(R.color.very_light_black),
            errorBorderColor = Color.Red,
            focusedLeadingIconColor = Color.Blue,
            unfocusedLeadingIconColor = Color.Gray,
            disabledLeadingIconColor = colorResource(R.color.very_light_black),
            errorLeadingIconColor = Color.Red,
            focusedTrailingIconColor = Color.Blue,
            unfocusedTrailingIconColor = Color.Gray,
            disabledTrailingIconColor = colorResource(R.color.very_light_black),
            errorTrailingIconColor = Color.Red,
            focusedLabelColor = Color.Blue,
            unfocusedLabelColor = Color.Gray,
            disabledLabelColor = colorResource(R.color.very_light_black),
            errorLabelColor = Color.Red,
            focusedPlaceholderColor = Color.Blue,
            unfocusedPlaceholderColor = Color.Gray,
            disabledPlaceholderColor = colorResource(R.color.very_light_black),
            errorPlaceholderColor = Color.Red,
            focusedSupportingTextColor = Color.Blue,
            unfocusedSupportingTextColor = Color.Gray,
            disabledSupportingTextColor = colorResource(R.color.very_light_black),
            errorSupportingTextColor = Color.Red,
            focusedPrefixColor = Color.Blue,
            unfocusedPrefixColor = Color.Gray,
            disabledPrefixColor = colorResource(R.color.very_light_black),
            errorPrefixColor = Color.Red,
            focusedSuffixColor = Color.Blue,
            unfocusedSuffixColor = Color.Gray,
            disabledSuffixColor = colorResource(R.color.very_light_black),
            errorSuffixColor = Color.Red,
        )
}