package com.example.healthhelper.dietary.components.iconbutton.navigationicon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.healthhelper.R

@Composable
fun NavigateUpNavigationIcon(
    onClick: ()->Unit,
){
    IconButton(onClick = onClick){
        Icon(
            imageVector = Icons.Filled.KeyboardArrowLeft,
            contentDescription = stringResource(R.string.navigate_up_button_text),
        )
    }
}