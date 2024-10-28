package com.example.healthhelper.plan.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthhelper.R
import com.example.healthhelper.plan.model.PlanProperty

class CustomList {
    @Composable
    fun PlanList(
        plans: List<PlanProperty>,
        onItemClick: (PlanProperty) -> Unit,
        leadingicon: @Composable () -> Unit,
        trialingicon: @Composable () -> Unit,
    ) {
        LazyColumn(
            modifier = Modifier

        ) {
            items(plans) { plan ->
                val startdatetime = plan.startTime
                val enddatetime = plan.endTime
                ListItem(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(25.dp))
                        .clickable { onItemClick(plan) }
                        .height(80.dp)
                        .width(320.dp)
                        .padding(top = 5.dp, bottom = 5.dp),
                    headlineContent = {
                        CustomText().TextWithDiffColor(
                            text = plan.categoryName,
                            setsize = 20.sp
                        )
                    },
                    supportingContent = {
                        CustomText().TextWithDiffColor(
                            text = "${startdatetime}~${enddatetime}",
                            setsize = 15.sp
                        )
                    },
                    leadingContent = {
                        leadingicon()
                    },
                    trailingContent = {
                       trialingicon()
                    },
                    colors = ListItemDefaults.colors(colorResource(R.color.primarycolor))
                )
            }
        }
    }

}