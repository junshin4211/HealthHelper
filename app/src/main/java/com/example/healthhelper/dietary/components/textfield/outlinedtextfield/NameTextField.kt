package com.example.healthhelper.dietary.components.textfield.outlinedtextfield

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.healthhelper.R

@Composable
fun NameTextField(
    modifier: Modifier = Modifier,
    mutableStateValue : MutableState<String>,
    onValueChange: ((String)-> Unit) = {},
    label: @Composable ()->Unit,
){
    OutlinedTextField(
        value = mutableStateValue.value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        trailingIcon = {
            IconButton(
                onClick = {
                    mutableStateValue.value = ""
                }
            ){
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = stringResource(R.string.clear_icon),
                )
            }
        },
    )
}