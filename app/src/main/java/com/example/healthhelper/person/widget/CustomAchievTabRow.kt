package com.example.healthhelper.person.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.healthhelper.R


@Composable
fun CustomAchievTabRow(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    tabLabels: List<String>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        tabLabels.forEachIndexed { index, label ->
            TabButton(
                isSelected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                label = label,
                shape = when (index) {
                    0 -> RoundedCornerShape(15.dp, 0.dp, 0.dp, 15.dp)
                    tabLabels.lastIndex -> RoundedCornerShape(0.dp, 15.dp, 15.dp, 0.dp)
                    else -> RoundedCornerShape(0.dp)
                }
            )
        }
    }
}

@Composable
fun TabButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    label: String,
    shape: RoundedCornerShape,
) {
    Box(
        modifier = Modifier
            .width(90.dp)
            .height(40.dp)
            .background(
                color = if (isSelected) colorResource(R.color.primarycolor) else colorResource(R.color.backgroundcolor),
                shape = shape
            )
            .border(1.dp, colorResource(R.color.primarycolor), shape = shape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (isSelected) Color.White else Color.Black
        )
    }

}
