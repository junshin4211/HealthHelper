package com.example.healthhelper.plan.ui


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
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

        val configuration = LocalConfiguration.current.screenWidthDp
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = colorResource(id = R.color.primarycolor)
                )
                .width(200.dp)
                .height((configuration*0.5).dp),
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