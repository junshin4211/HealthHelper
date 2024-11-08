package com.example.healthhelper.dietary.components.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun MealButton(
    outerIconButtonModifier: Modifier = Modifier,
    outerIconButtonColor: IconButtonColors,
    innerIconId: Int,
    spacerModifier: Modifier = Modifier,
    innerText: @Composable ()-> Unit,
    onClick: () -> Unit,
){
    IconButton(
        modifier = outerIconButtonModifier,
        onClick = onClick,
        colors = outerIconButtonColor,
    ){
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ){
            Box(
                modifier = Modifier
                    .weight(0.3f),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(innerIconId),
                    contentDescription = "",
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.7f),
                contentAlignment = Alignment.Center,
            ){
                innerText()
            }
        }
    }
}