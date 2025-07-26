package com.example.healthhelper.planpage.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthhelper.R
import com.example.healthhelper.ui.theme.HealthHelperTheme
import java.util.Locale

@Composable
fun NutritionSlider(
    label: String,
    percent: Float,
    gram: Float,
    themeColor: Color,
    onValueChange: (Float) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                color = themeColor
            )
            OutlinedTextField_Plan(
                value = String.format(Locale.US, "%.2f", percent) + "%",
                onValueChange = { onValueChange(it.toFloat().coerceIn(0f..100f)) },
                modifier = Modifier.width(120.dp)
            )
            Text(
                text = "(" + String.format(Locale.US, "%.2f", gram)
                        + stringResource(R.string.grams) + ")"
            )
        }

        Slider(
            value = percent,
            onValueChange = { onValueChange(it.coerceIn(0f..100f)) },
            valueRange = 0f..100f,
            steps = 9,
            colors = SliderDefaults.colors(
                thumbColor = themeColor,
                activeTrackColor = themeColor,
                activeTickColor = themeColor,
                inactiveTrackColor = Color.LightGray,
                inactiveTickColor = Color.LightGray
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NutritionCalculatorPreview() {
    HealthHelperTheme {
        NutritionSlider(
            label = "Label",
            percent = 50f,
            gram = 100f,
            themeColor = Color.Red,
            onValueChange = {}
        )
    }
}