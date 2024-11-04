package com.example.healthhelper.dietary.components.button

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.attr.viewmodel.DefaultColorViewModel
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum

@Composable
fun AddNewDietDiaryItemButton(
    navController: NavHostController,
){
    IconButton(
        colors = DefaultColorViewModel.iconButtonColors,
        onClick = {
            navController.navigate(DietDiaryScreenEnum.AddNewDietDiaryItemFrame.name)
        }) {
        Image(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_new_item_icon),
        )
    }
}