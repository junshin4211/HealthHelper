package com.example.healthhelper.dietary.components.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

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
            Image(
                painter = painterResource(innerIconId),
                contentDescription = "",
            )
            Spacer(modifier = spacerModifier)

            innerText()
        }
    }
}