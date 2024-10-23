package com.example.healthhelper.plan.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.healthhelper.R

@Composable
fun CreateDatePicker(
    dateSelected: String
){
    var inputText by remember { mutableStateOf("") }
    TextField(
        readOnly = true,
        value = dateSelected,
        onValueChange = {  },
        singleLine = true,
        label = { Text(text = stringResource(R.string.pickdaterange)) },
        trailingIcon = {  },
        modifier = Modifier
            .border(
                width = 2.dp,
                color = colorResource(id = R.color.primarycolor),
                shape = RoundedCornerShape(20.dp)),

        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        )
    )
}

fun dateformater(
    dateSelected: String
){

}