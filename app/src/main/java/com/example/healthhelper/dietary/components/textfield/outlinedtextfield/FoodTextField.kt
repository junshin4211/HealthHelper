package com.example.healthhelper.dietary.components.textfield.outlinedtextfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun FoodTextField(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mutableStateValue : MutableState<String>,
    onValueChange: (String)-> Unit,
    label: @Composable ()->Unit,
){
    /*
    val options = listOf("Apple","Banana")
    MyExposedDropDownMenu(
        navController = navController,
        mutableStateValue = mutableStateValue,
        options = options,
        onValueChangedEvent = onValueChange,
        modifier = modifier,
        label = label,
    )
    
     */
}