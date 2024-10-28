package com.example.healthhelper.plan.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthhelper.R
import com.example.healthhelper.ui.theme.HealthHelperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CreateDropDownMenu(
    options: List<T>,
    selectedOption: T?,
    onOptionSelected: (T) -> Unit,
    getDisplayText: (T) -> String
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(if (selectedOption != null) getDisplayText(selectedOption) else "")}

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        TextField(
            readOnly = true,
            value = selectedText,
            onValueChange = {  },
            singleLine = true,
            label = { Text(text = stringResource(R.string.pickdaterange)) },
            trailingIcon = { if (expanded) CustomIcon().CreateArrow() else CustomIcon().CreateArrow(true) },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                .border(
                    width = 2.dp,
                    color = colorResource(id = R.color.primarycolor),
                    if (expanded) {RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)} else {RoundedCornerShape(20.dp)})
                .width(200.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = colorResource(id = R.color.primarycolor)
                )
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(getDisplayText(option)) },
                    onClick = {
                        selectedText = getDisplayText(option)
                        onOptionSelected(option)
                        expanded = false
                    },
                )
            }
        }
    }
}


@Preview(locale = "zh-rTW")
@Composable
fun EditPlanPreview() {
    HealthHelperTheme {
        //CreateDropDownMenu()
    }
}