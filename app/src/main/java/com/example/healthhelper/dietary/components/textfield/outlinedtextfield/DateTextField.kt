package com.example.healthhelper.dietary.components.textfield.outlinedtextfield

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import com.example.healthhelper.R
import com.example.healthhelper.dietary.components.picker.datepicker.DatePickerModal
import com.example.healthhelper.dietary.util.datetime.DateUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTextField(
    modifier: Modifier = Modifier,
    mutableStateValue : MutableState<String>,
    onValueChange: ((String)-> Unit) = {},
    label: @Composable ()->Unit,
) {
    var shouldShowDatePicker by remember { mutableStateOf(false) }
    if(shouldShowDatePicker){
        DatePickerModal(
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
    OutlinedTextField(
        modifier = modifier
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    shouldShowDatePicker = true
                }
            },
        value = mutableStateValue.value,
        onValueChange = onValueChange,
        label = label,
        readOnly = true,
        trailingIcon = {
            IconButton(
                onClick = {
                    mutableStateValue.value = ""
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = stringResource(R.string.clear_icon),
                )
            }
        },
    )
}