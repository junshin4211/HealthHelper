package com.example.healthhelper.planpage.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthhelper.R
import com.example.healthhelper.plan.DateRange
import com.example.healthhelper.plan.ui.CustomIcon
import com.example.healthhelper.ui.theme.HealthHelperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CreateDropDownMenu(
    options: List<T>,
    selectedOption: T?,
    onOptionSelected: (T) -> Unit,
    getDisplayText: @Composable (option: T) -> String
) {
    var expanded by remember { mutableStateOf(false) }

    var selectedText = selectedOption?.let { getDisplayText(it) } ?: ""

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField( // <--- 改用 OutlinedTextField
            readOnly = true,
            value = selectedText,
            onValueChange = { /* 通常 readOnly TextField不需要 */ },
            label = { Text(text = stringResource(R.string.pickdaterange)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            shape = if (expanded) { // 設置 TextField 的形狀
                RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
            } else {
                RoundedCornerShape(20.dp)
            },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable, true) // menuAnchor 仍然需要
                .fillMaxWidth() // 設置寬度
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .exposedDropdownSize(matchTextFieldWidth = true)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(getDisplayText(option)) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Preview(locale = "zh-rTW", showBackground = true)
@Composable
fun EditPlanPreview() {
    HealthHelperTheme {
        CreateDropDownMenu(
            options = DateRange.entries,
            selectedOption = null,
            onOptionSelected = { },
            getDisplayText = { it.title.toString() })

    }
}