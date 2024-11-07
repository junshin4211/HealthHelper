package com.example.healthhelper.plan.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.healthhelper.R
import java.util.Locale

@Composable
fun createSliders(
    value: Float,
    minvalue: Float,
    maxvalue: Float,
    activatecolor:Int,
    inactivatecolor:Int,
    onValueChange: (Float) -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomText().TextWithDiffColor(text = String.format(Locale.US, "%.1f", minvalue))

        Slider(
            value = value,
            onValueChange = {newvalue ->
                onValueChange(newvalue)
                            },
            valueRange = minvalue..maxvalue,
            modifier = Modifier
                .weight(1.0f),
            colors = SliderDefaults.colors(
                thumbColor = colorResource(R.color.black),
                activeTrackColor = colorResource(activatecolor),
                inactiveTickColor = colorResource(inactivatecolor)
            )
        )

        CustomText().TextWithDiffColor(text = String.format(Locale.US, "%.1f", maxvalue))

    }
}