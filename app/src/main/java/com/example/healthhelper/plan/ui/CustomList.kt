package com.example.healthhelper.plan.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.healthhelper.R
import com.example.healthhelper.plan.model.PlanProperty

class CustomList {
    @Composable
    fun PlanList(
        plans: List<PlanProperty>,
        innerPadding: PaddingValues,
        onItemClick: (PlanProperty) -> Unit,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(plans) { plan ->
                // 用來建立Lists內容物
                ListItem(
                    modifier = Modifier.clickable {
                        onItemClick(plan)
                    },
                    overlineContent = { Text(text = plan.categoryName) },
                    headlineContent = { Text(text = plan.startTime ?: "") },
                    supportingContent = { Text(plan.endTime ?: "") },
                    leadingContent = {
                        Image(
                            painter = painterResource(id = R.drawable.protein),
                            contentDescription = "plantype",
                            modifier = Modifier.padding(16.dp)
                        )
                    },
                    trailingContent = {
                        CustomIcon().CreateArrow(
                            isRight = true,
                            size = 3.0f,
                            color = R.color.black
                        )
                    }
                )
                HorizontalDivider()
            }
        }
    }

}