package com.example.healthhelper.dietary.components.button

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.healthhelper.dietary.components.picker.datepicker.DatePickerModal
import com.example.healthhelper.dietary.util.datetime.DateUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateButton(
    mutableStateValue: MutableState<String>,
){
    var shouldShowDatePicker by remember { mutableStateOf(false) }
    if(shouldShowDatePicker){
        DatePickerModal.DatePickerDialog(
            onDateSelected = {
                mutableStateValue.value = DateUtil.convertMillisToDate(it ?: 0)
                shouldShowDatePicker = false
            },
            onDismiss = {
                mutableStateValue.value = ""
                shouldShowDatePicker = false
            },
            _selectableDates = null,
        )
    }
    Button(
        onClick = {
            shouldShowDatePicker = true
        }
    ){
        Text(mutableStateValue.value)
    }
}