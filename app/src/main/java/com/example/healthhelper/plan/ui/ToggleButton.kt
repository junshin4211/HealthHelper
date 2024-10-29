package com.example.healthhelper.plan.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthhelper.R

@Composable
fun CreateToggleButton(
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    backgroundColor: Int = R.color.light_gray,
    leftButtonColor: Int = R.color.primarycolor,
    rightButtonColor: Int = R.color.light_gray,
    leftTextColor: Int = R.color.white,
    rightTextColor: Int = R.color.darkgray,
    leftText: String ,
    rightText: String ,
    barHeight: Dp = 30.dp,
    barWidth: Dp = 280.dp,
    topPadding: Dp = 15.dp
) {

    Row(
        modifier = Modifier
            .padding(topPadding)
            .height(barHeight)
            .width(barWidth)
            .clip(RoundedCornerShape(10.dp))
            .background(colorResource(id = backgroundColor)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {

        Column(
            Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(30))
                .background(colorResource(id = leftButtonColor))
                .clickable {
                    onLeftClick()
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp, alignment = Alignment.CenterVertically)
        ) {
            CustomText().TextWithDiffColor(
                text = leftText,
                setsize = 14.sp,
                setcolor = leftTextColor
            )
        }

        VerticalDivider(
            thickness = 1.dp,
            color = colorResource(id = R.color.very_light_gray),
            modifier = Modifier.height(20.dp)
        )

        Column(
            Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(30))
                .background(colorResource(id = rightButtonColor))
                .clickable {
                    onRightClick()
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp, alignment = Alignment.CenterVertically)
        ) {
            CustomText().TextWithDiffColor(
                text = rightText,
                setsize = 14.sp,
                setcolor = rightTextColor
            )
        }
    }
}
