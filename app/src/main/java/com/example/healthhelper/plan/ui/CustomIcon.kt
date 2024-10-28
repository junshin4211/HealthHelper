package com.example.healthhelper.plan.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CreateArrow(expanded: Boolean = false) {
    if (expanded) {
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

@Preview(showBackground = true)
@Composable
fun CreateArrowPreview() {
    CreateArrow()
}