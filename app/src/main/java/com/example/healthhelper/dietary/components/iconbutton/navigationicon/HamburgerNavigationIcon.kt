package com.example.healthhelper.dietary.components.iconbutton.navigationicon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.healthhelper.R

@Composable
fun HamburgerNavigationIcon(){
    IconButton(onClick = {

    }){
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = stringResource(R.string.hamburger_icon),
        )
    }
}