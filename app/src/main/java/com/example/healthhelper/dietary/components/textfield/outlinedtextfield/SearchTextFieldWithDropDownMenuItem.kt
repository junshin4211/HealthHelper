package com.example.healthhelper.dietary.components.textfield.outlinedtextfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.healthhelper.attr.color.defaultcolor.DefaultColorViewModel
import com.example.healthhelper.dietary.components.dropdown.dropmenu.MyExposedDropDownMenuWithCheckBox
import com.example.healthhelper.dietary.viewmodel.SelectedFoodItemsViewModel

@Composable
fun SearchTextFieldWithDropDownMenuItem(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    selectedFoodItemsViewModel: SelectedFoodItemsViewModel = viewModel(),
    label: @Composable () -> Unit,
) {
    val TAG = "tag_SearchTextFieldWithDropDownMenuItem"

    val availableFoodItems by selectedFoodItemsViewModel.data.collectAsState()

    var availableOptions by remember { mutableStateOf(availableFoodItems) }

    val queryText = remember { mutableStateOf("") }

    MyExposedDropDownMenuWithCheckBox(
        navController = navController,
        mutableStateValue = queryText,
        options = availableOptions,
        modifier = Modifier,
        label = {},
        onValueChangedEvent = { input ->
            queryText.value = input
            if(queryText.value != "") {
                availableOptions =
                    availableFoodItems.filter { it.name.contains(queryText.value) }.toMutableList()
            }else{
                availableOptions = availableFoodItems
            }
        },
        outlinedTextFieldColor = DefaultColorViewModel.outlinedTextFieldDefaultColors,
        readOnly = false,
    )
}