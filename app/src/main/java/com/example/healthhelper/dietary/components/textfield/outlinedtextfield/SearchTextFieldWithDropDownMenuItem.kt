package com.example.healthhelper.dietary.components.textfield.outlinedtextfield

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.healthhelper.attr.viewmodel.DefaultColorViewModel
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

    Log.e(TAG,"In SearchTextFieldWithDropDownMenuItem function, availableOptions:${availableOptions}")
    MyExposedDropDownMenuWithCheckBox(
        navController = navController,
        mutableStateValue = queryText,
        options = availableOptions,
        exposedDropdownMenuBoxModifier = Modifier.padding(16.dp,0.dp),
        label = {},
        onValueChangedEvent = { input ->
            Log.e(TAG,"In SearchTextFieldWithDropDownMenuItem function, onValueChangedEvent callback was triggered.input:${input}")
            queryText.value = input
            if(queryText.value != "") {
                availableOptions =
                    availableFoodItems.filter { it.name.value.contains(queryText.value) }.toMutableList()
            }else{
                availableOptions = availableFoodItems
            }
        },
        outlinedTextFieldColor = DefaultColorViewModel.outlinedTextFieldDefaultColors,
        readOnly = false,
    )
}