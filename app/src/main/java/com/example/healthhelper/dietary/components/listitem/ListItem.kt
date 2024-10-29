package com.example.healthhelper.dietary.components.listitem

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum
import com.example.healthhelper.dietary.repository.SelectedFoodItemRepository
import com.example.healthhelper.dietary.viewmodel.SelectedFoodItemViewModel
import com.example.healthhelper.dietary.viewmodel.SelectedMealOptionViewModel

@Composable
fun ListItem(
    navController: NavHostController,
    selectedMealOptionViewModel: SelectedMealOptionViewModel = viewModel(),
    selectedFoodItemViewModel: SelectedFoodItemViewModel = viewModel(),
    modifier: Modifier = Modifier,
    foodItem:SelectedFoodItemVO,
){
    val TAG = "tag_ListItem"

    Box(
        modifier = modifier
            .requiredWidth(width = 360.dp)
            .requiredHeight(height = 41.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .requiredWidth(width = 360.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(height = 40.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(weight = 1f),
                ) {
                    Text(
                        text = foodItem.name,
                        color = colorResource(R.color.primarycolor),
                        lineHeight = 1.27.em,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)
                ) {
                    IconButton(
                        onClick = {
                            SelectedFoodItemRepository.setData(foodItem)
                            navController.navigate(DietDiaryScreenEnum.FoodItemInfoFrame.name)
                        }
                    ){
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "arrow_right",
                            tint = colorResource(R.color.primarycolor)
                        )
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                HorizontalDivider(
                    color = colorResource(R.color.primarycolor),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
        Box(
            modifier = modifier
                .fillMaxSize()
        )
    }
}