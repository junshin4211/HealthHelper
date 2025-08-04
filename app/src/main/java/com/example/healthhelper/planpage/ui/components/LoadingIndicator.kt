package com.example.healthhelper.planpage.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthhelper.ui.theme.HealthHelperTheme
import com.google.android.material.loadingindicator.LoadingIndicator
import com.himanshoe.charty.util.dpToPx


@Composable
fun LoadingIndicator() {
    HealthHelperTheme {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.scale(2.0f),
                // indicator的顏色
                color = MaterialTheme.colorScheme.primary,
                // indicator後面的軌道顏色
                trackColor = Color.LightGray,
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun LoadingIndicatorPreview() {
    HealthHelperTheme {
        LoadingIndicator()
    }
}