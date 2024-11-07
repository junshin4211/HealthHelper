package com.example.healthhelper.dietary.components.dropdown.dropmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.attr.viewmodel.DefaultColorViewModel
import com.example.healthhelper.dietary.components.iconbutton.SearchIcon
import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import com.example.healthhelper.dietary.repository.SelectedFoodItemsRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyExposedDropDownMenuWithCheckBox(
    navController: NavHostController,
    mutableStateValue: MutableState<String>,
    options: List<SelectedFoodItemVO>,
    exposedDropdownMenuBoxModifier: Modifier = Modifier,
    label: @Composable () -> Unit,
    onValueChangedEvent: (String) -> Unit,
    outlinedTextFieldColor: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    readOnly: Boolean = true,
) {
    val TAG="tag_MyExposedDropDownMenuWithCheckBox"

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = exposedDropdownMenuBoxModifier,
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
                    iconButtonColors = DefaultColorViewModel.iconButtonColors2,
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
            LazyColumn(
                modifier = Modifier
                    .width(500.dp)
                    .height(700.dp)
                    .background(color = colorResource(R.color.backgroundcolor)),
            ) {
                items(options) {
                    DropdownMenuItem(
                        text = {
                            MenuItem(it)
                        },
                        onClick = {
                            expanded = false
                            onValueChangedEvent(it.name)
                        },
                    )
                    HorizontalDivider(
                        color = colorResource(R.color.primarycolor),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun MenuItem(
    selectedFoodItemVO: SelectedFoodItemVO
) {
    var isChecked by remember { mutableStateOf(selectedFoodItemVO.isCheckedWhenSelection.value) }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Text(
            text = selectedFoodItemVO.name,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            modifier = Modifier.width(100.dp),
            color = colorResource(R.color.primarycolor),
        )
        Checkbox(
            modifier = Modifier.padding(10.dp,0.dp),
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
                SelectedFoodItemsRepository.setCheckedWhenSelectionState(selectedFoodItemVO,it)
            },
        )
    }
}