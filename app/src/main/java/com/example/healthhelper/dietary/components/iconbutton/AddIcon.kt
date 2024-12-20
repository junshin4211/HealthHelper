package com.example.healthhelper.dietary.components.iconbutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.healthhelper.R

@Composable
fun AddIcon(
    navController: NavHostController,
    onClick: ()->Unit,
    iconButtonColors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
) {
    IconButton(
        onClick = onClick,
        colors = iconButtonColors,
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_icon),
        )
    }
}