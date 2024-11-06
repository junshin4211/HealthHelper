package com.example.healthhelper.dietary.components.dropdown.dropmenu

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.healthhelper.dietary.components.iconbutton.SearchIcon
import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import com.example.healthhelper.dietary.repository.SelectedFoodItemsRepository
import com.example.healthhelper.dietary.viewmodel.SelectedFoodItemsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyExposedDropDownMenuWithCheckBox(
    navController: NavHostController,
    mutableStateValue: MutableState<String>,
    options: List<SelectedFoodItemVO>,
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit,
    onValueChangedEvent: (String) -> Unit,
    outlinedTextFieldColor: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    readOnly: Boolean = true,
    selectedFoodItemsViewModel: SelectedFoodItemsViewModel= viewModel(),
) {
    val TAG="tag_MyExposedDropDownMenuWithCheckBox"

    val selectedFoodItems by selectedFoodItemsViewModel.data.collectAsState()

    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        Log.d(TAG,"options:${options}")
    }
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
                    onClick = {expanded=!expanded},
                )
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
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = option.name,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                modifier = Modifier.width(100.dp)
                            )
                            Checkbox(
                                modifier = Modifier.padding(10.dp,0.dp),
                                checked = option.isCheckedWhenSelection.value,
                                onCheckedChange = {
                                    option.isCheckedWhenSelection.value = it
                                    SelectedFoodItemsRepository.setCheckedWhenSelectionState(option,it)
                                }
                            )
                        }
                    },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(option.name)
                    }
                )
            }
        }
    }
}