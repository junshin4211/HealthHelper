package com.example.healthhelper.dietary.components.textfield.outlinedtextfield

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.healthhelper.attr.color.defaultcolor.DefaultColorViewModel
import com.example.healthhelper.dietary.components.dropdown.dropmenu.MyExposedDropDownMenu
import com.example.healthhelper.dietary.viewmodel.FoodItemsViewModel

@Composable
fun SearchTextFieldWithDropDownMenuItem(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    foodItemsViewModel: FoodItemsViewModel = viewModel(),
    label: @Composable () -> Unit,
) {
    val TAG = "tag_SearchTextFieldWithDropDownMenuItem"

    var supportingTextColor1 by remember { mutableStateOf(Color.Gray) }
    var supportingTextColor2 by remember { mutableStateOf(Color.Gray) }
    val queryText = remember { mutableStateOf("") }
    val availableFoodItems by foodItemsViewModel.data.collectAsState()
    var availableOptions by remember { mutableStateOf(availableFoodItems.map { it.name }) }
    MyExposedDropDownMenu(
        navController = navController,
        mutableStateValue = queryText,
        options = availableOptions,
        modifier = Modifier,
        label = {},
        onValueChangedEvent = {input ->
            queryText.value = input
            availableOptions = availableFoodItems.map { it.name }.filter{ it.contains(queryText.value)}
            Log.d(TAG,"availableOptions:${availableOptions}")
        },
        outlinedTextFieldColor = DefaultColorViewModel.outlinedTextFieldDefaultColors,
        readOnly = false,
    )
}