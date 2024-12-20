package com.example.healthhelper.plan.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.healthhelper.R
import com.example.healthhelper.plan.model.PlanModel
import com.example.healthhelper.plan.usecase.PlanUCImpl

class CustomList {
    @Composable
    fun ItemList(
        inputList: List<PlanModel>,
        onItemClick: (PlanModel) -> Unit,
        leadingIcon: @Composable () -> Unit,
        trialingIcon: @Composable (PlanModel) -> Unit,
    ) {
        val formatter = PlanUCImpl()::dateTimeFormat;
        LazyColumn(
            modifier = Modifier
        ) {
            items(items = inputList, key = {it.userDietPlanId}) { plan ->
                val currentPlan by rememberUpdatedState(plan)
                ListItem(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(25.dp))
                        .clickable {
                            onItemClick(currentPlan)
                            Log.d("tag_ClickItem", "click ${plan.userDietPlanId}")
                        }
                        .height(80.dp)
                        .width(320.dp)
                        .padding(top = 5.dp, bottom = 5.dp),
                    headlineContent = {
                        CustomText().TextWithDiffColor(
                            setcolor = R.color.white,
                            text = plan.categoryName
                        )
                    },
                    supportingContent = {
                        CustomText().TextWithDiffColor(
                            setcolor = R.color.white,
                            text = "${formatter(plan.startDateTime)}~${formatter(plan.endDateTime)}"
                        )
                    },
                    leadingContent = {
                        leadingIcon()
                    },
                    trailingContent = {
                       trialingIcon(plan)
                    },
                    colors = ListItemDefaults.colors(colorResource(R.color.primarycolor))
                )
                Log.d("tag_ItemList", "${plan.userDietPlanId}")
            }
        }
    }

}