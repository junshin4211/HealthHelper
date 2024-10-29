package com.example.healthhelper.plan.ui

import androidx.annotation.ColorRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.healthhelper.R

class CustomIcon {
    @Composable
    fun CreateArrow(isUp: Boolean = false) {
        if (isUp) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "arrowup",
                modifier = Modifier.graphicsLayer(scaleY = -1f)
            )
        } else {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "arrowdown"
            )
        }
    }

    @Composable
    fun CreateArrow(isRight: Boolean = false, size: Float, @ColorRes color: Int) {
        if (isRight) {
            Icon(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "arrowright",
                modifier = Modifier.scale(size),
                tint = colorResource(color)
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "arrowleft",
                modifier = Modifier
                    .graphicsLayer(scaleX = -1f)
                    .scale(size),
                tint = colorResource(color)
            )
        }
    }

    @Composable
    fun CreateDelete(
        size: Float,
        onDeleteClick: () -> Unit,
    ) {
        IconButton(
            onClick = {
                onDeleteClick()
            }
        ){
            Icon(
                painter = painterResource(R.drawable.delete),
                contentDescription = "delete",
                modifier = Modifier.scale(size)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CreateArrowPreview() {
    //CustomIcon().CreateArrow(false,2f)
}