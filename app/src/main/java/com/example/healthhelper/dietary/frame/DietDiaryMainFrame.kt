package com.example.healthhelper.dietary.frame

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.dietary.components.bar.appbar.topappbar.QueryTopAppBar
import com.example.healthhelper.dietary.components.button.MealButton
import com.example.healthhelper.dietary.components.combo.NutritionInfoCombo
import com.example.healthhelper.dietary.components.picker.datepicker.CustomDatePicker
import com.example.healthhelper.dietary.dataclasses.vo.FoodItemVO
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum
import com.example.healthhelper.dietary.repository.MealsOptionRepository
import com.example.healthhelper.dietary.repository.NutritionInfoRepository
import com.example.healthhelper.dietary.viewmodel.DiaryViewModel
import com.example.healthhelper.dietary.viewmodel.FoodItemViewModel
import com.example.healthhelper.dietary.viewmodel.FoodViewModel
import com.example.healthhelper.dietary.viewmodel.MealsOptionViewModel
import com.example.healthhelper.dietary.viewmodel.NutritionInfoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun DietDiaryMainFrame(
    navController: NavHostController,
    mealsOptionViewModel: MealsOptionViewModel = viewModel(),
    nutritionInfoViewModel: NutritionInfoViewModel = viewModel(),
    diaryViewModel: DiaryViewModel = viewModel(),
    foodViewModel: FoodViewModel = viewModel(),
    foodItemViewModel: FoodItemViewModel = viewModel(),
) {
    val TAG = "tag_DietDiaryMainFrame"

    val context = LocalContext.current

    val mealsOptions by mealsOptionViewModel.data.collectAsState()
    val nutritionInfo by nutritionInfoViewModel.data.collectAsState()
    val diaryVO by diaryViewModel.data.collectAsState()

    var currentMealOption by remember { mutableStateOf(mealsOptions[0]) }

    var mealsButtonIsClicked by remember { mutableStateOf(false) }

    LaunchedEffect(diaryVO) {
        NutritionInfoRepository.setNutritionInfo(diaryVO)
        val newFoodItemVO = FoodItemVO(
            diaryID = diaryVO.diaryID,
            foodID = -1,
            grams = -1.0,
        )
        val queriedFoodItems = foodItemViewModel.selectFoodItemByDiaryId(newFoodItemVO)
        if (queriedFoodItems.isEmpty()) { // fetch data failed.
            Toast.makeText(
                context,
                context.getString(R.string.fetch_food_item_failed),
                Toast.LENGTH_LONG
            ).show()
            return@LaunchedEffect
        }

        // fetch data successfully
        Toast.makeText(
            context,
            context.getString(R.string.fetch_food_item_successfully),
            Toast.LENGTH_LONG
        ).show()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            QueryTopAppBar(
                navController = navController,
                title = {
                    Text(
                        stringResource(R.string.diet_diary_main_frame_title),
                        fontSize = 24.sp,
                        fontWeight = FontWeight(600)
                    )
                },
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(color = colorResource(R.color.backgroundcolor))
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = colorResource(R.color.primarycolor)
                    )

                    CustomDatePicker(
                        diaryViewModel = diaryViewModel,
                    )

                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                            .fillMaxWidth()
                    )

                    mealsOptions.forEachIndexed { index, mealsOption ->
                        val outerIconButtonModifier = Modifier
                            .size(300.dp, 70.dp)
                            .padding(10.dp)
                        val spacerModifier = Modifier
                            .width(30.dp)
                            .fillMaxHeight()
                        val outerIconButtonColor = IconButtonColors(
                            contentColor = colorResource(R.color.primarycolor),
                            containerColor = colorResource(R.color.primarycolor),
                            disabledContentColor = colorResource(R.color.gray_300),
                            disabledContainerColor = colorResource(R.color.gray_300),
                        )
                        val innerIconId = mealsOption.iconResId
                        val innerText =
                            @Composable {
                                Text(
                                    text = stringResource(mealsOption.nameResId),
                                    color = Color.White,
                                    fontSize = 30.sp,
                                )
                            }
                        MealButton(
                            outerIconButtonModifier = outerIconButtonModifier,
                            outerIconButtonColor = outerIconButtonColor,
                            onClick = {
                                currentMealOption = mealsOption
                                mealsButtonIsClicked = true
                            },
                            innerIconId = innerIconId,
                            spacerModifier = spacerModifier,
                            innerText = innerText,
                        )
                    }
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                    ) {
                        NutritionInfoCombo(
                            nutritionInfoVO = nutritionInfo,
                            showTitle = true,
                            title = {
                                Text(
                                    text = "${stringResource(R.string.total_title)}:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                )
                            }
                        )
                    }
                }
            }

            if (mealsButtonIsClicked) {
                MealsOptionRepository.setSelectedData(currentMealOption)
                navController.navigate(DietDiaryScreenEnum.DietDiaryMealFrame.name)
                mealsButtonIsClicked = false
            }
        })
}

@Preview(showBackground = true)
@Composable
fun DietDiaryMainFramePreview() {
    val navController = rememberNavController()
    DietDiaryMainFrame(navController);
}