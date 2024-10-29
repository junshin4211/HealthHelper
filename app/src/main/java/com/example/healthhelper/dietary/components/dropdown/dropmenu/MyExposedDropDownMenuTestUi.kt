package com.example.healthhelper.dietary.components.dropdown.dropmenu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyExposedDropDownMenuTestUi(){
    val mutableStateString = remember { mutableStateOf("") }
    val options = listOf("Apple","Banana")
    Scaffold { innerPaddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            MyExposedDropDownMenu(
                mutableStateValue = mutableStateString,
                label = {},
                onValueChangedEvent = { mutableStateString.value = it },
                options = options,
                modifier = Modifier.width(200.dp)
            )
        }
    }
}