package com.example.healthhelper.attr.viewmodel

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.healthhelper.R

object DefaultColorViewModel {
    val outlinedTextFieldDefaultColors
        @Composable
        get() = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Gray,
            disabledTextColor = colorResource(R.color.very_light_black),
            errorTextColor = Color.Red,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = colorResource(R.color.very_light_black),
            errorContainerColor = Color.Red,
            cursorColor = colorResource(R.color.blue01),
            errorCursorColor = Color.Red,
            selectionColors = null,
            focusedBorderColor = colorResource(R.color.primarycolor),
            unfocusedBorderColor = colorResource(R.color.primarycolor),
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

    @OptIn(ExperimentalMaterial3Api::class)
    val topAppBarColors: TopAppBarColors
        @Composable
        get() = TopAppBarColors(
            containerColor = colorResource(R.color.primarycolor),
            scrolledContainerColor = colorResource(R.color.primarycolor),
            navigationIconContentColor = Color.Black,
            titleContentColor = Color.White,
            actionIconContentColor = Color.Black,
        )

    @OptIn(ExperimentalMaterial3Api::class)
    val datePickerColors: DatePickerColors
        @Composable
        get() = DatePickerDefaults.colors(
            containerColor = colorResource(R.color.primarycolor),
            weekdayContentColor = Color.White,
            subheadContentColor = Color.White,
            yearContentColor = Color.White,
            currentYearContentColor = Color.White,
            selectedYearContainerColor = Color.White.copy(alpha = 0.3f),
            selectedYearContentColor = Color.White,
            dayContentColor = Color.White,
            selectedDayContainerColor = Color.White.copy(alpha = 0.3f),
            selectedDayContentColor = Color.White,
            todayContentColor = Color.White,
            todayDateBorderColor = Color.White
        )

    val iconButtonColors: IconButtonColors
        @Composable
        get() = IconButtonColors(
            containerColor = colorResource(R.color.primarycolor),
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Gray,
        )

    val buttonColors: ButtonColors
        @Composable
        get() = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.primarycolor),
            contentColor = Color.White,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White,
        )

    val textFieldWithTextColors: TextFieldColors
        @Composable
        get() = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = colorResource(R.color.very_light_black),
            errorTextColor = Color.Red,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = colorResource(R.color.very_light_black),
            errorContainerColor = Color.Red,
            cursorColor = colorResource(R.color.blue01),
            errorCursorColor = Color.Red,
            selectionColors = null,
            focusedBorderColor = colorResource(R.color.primarycolor),
            unfocusedBorderColor = colorResource(R.color.primarycolor),
            disabledBorderColor = colorResource(R.color.very_light_black),
            errorBorderColor = Color.Red,
            focusedLeadingIconColor = Color.Black,
            unfocusedLeadingIconColor = Color.Black,
            disabledLeadingIconColor = colorResource(R.color.very_light_black),
            errorLeadingIconColor = Color.Red,
            focusedTrailingIconColor = Color.Black,
            unfocusedTrailingIconColor = Color.Black,
            disabledTrailingIconColor = colorResource(R.color.very_light_black),
            errorTrailingIconColor = Color.Red,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Gray,
            disabledLabelColor = colorResource(R.color.very_light_black),
            errorLabelColor = Color.Red,
            focusedPlaceholderColor = Color.Black,
            unfocusedPlaceholderColor = Color.Gray,
            disabledPlaceholderColor = colorResource(R.color.very_light_black),
            errorPlaceholderColor = Color.Red,
            focusedSupportingTextColor = Color.Gray,
            unfocusedSupportingTextColor = Color.Black,
            disabledSupportingTextColor = colorResource(R.color.very_light_black),
            errorSupportingTextColor = Color.Red,
            focusedPrefixColor = Color.Black,
            unfocusedPrefixColor = Color.Black,
            disabledPrefixColor = colorResource(R.color.very_light_black),
            errorPrefixColor = Color.Red,
            focusedSuffixColor = Color.Black,
            unfocusedSuffixColor = Color.Black,
            disabledSuffixColor = colorResource(R.color.very_light_black),
            errorSuffixColor = Color.Red,
        )
}