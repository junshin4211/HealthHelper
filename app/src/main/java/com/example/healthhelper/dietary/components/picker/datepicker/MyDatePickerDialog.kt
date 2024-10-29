package com.example.healthhelper.dietary.components.picker.datepicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * MyDatePickerDialog
 *
 * a class than can pop up a date picker
 */

class MyDatePickerDialog @OptIn(ExperimentalMaterial3Api::class) constructor(
    private val buttonText:String,
    private val modifier: Modifier = Modifier.height(40.dp).fillMaxWidth(),
    private val onDateSelected: (String) -> Unit,
    private val selectableDates: SelectableDates? = null
){
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun popUp(){
        var showDatePicker by remember { mutableStateOf(false) }

        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Button(
                modifier = Modifier.fillMaxSize(),
                onClick = { showDatePicker = true }
            ) {
                Text(text = buttonText)
            }
        }

        if (showDatePicker) {
            MyDatePickerDialogUtil.popUp(
                _selectableDates = selectableDates,
                onDateSelected = onDateSelected,
                onDismiss = { showDatePicker = false }
            )
        }
    }
}