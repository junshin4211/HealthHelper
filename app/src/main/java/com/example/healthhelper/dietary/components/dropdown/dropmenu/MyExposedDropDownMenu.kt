package com.example.healthhelper.dietary.components.dropdown.dropmenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthhelper.dietary.components.iconbutton.SearchIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyExposedDropDownMenu(
    navController: NavHostController,
    mutableStateValue: MutableState<String>,
    options: List<String>,
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit,
    onValueChangedEvent: (String) -> Unit,
    outlinedTextFieldColor: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    readOnly: Boolean = true,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier,
    ) {
        OutlinedTextField(
            readOnly = readOnly,
            value = mutableStateValue.value,
            onValueChange = { onValueChangedEvent(it) },
            label = label,
            trailingIcon = {
                SearchIcon(
                    navController = navController,
                    onClick = {expanded=!expanded}
                )
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
            ),
            colors = outlinedTextFieldColor,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option: String ->
                DropdownMenuItem(
                    text = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = option,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp
                            )
                        }

                    },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(option)
                    }
                )
            }
        }
    }
}