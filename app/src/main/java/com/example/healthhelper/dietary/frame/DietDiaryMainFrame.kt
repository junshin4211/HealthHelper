package com.example.healthhelper.dietary.frame

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.dietary.components.bar.appbar.topappbar.QueryTopAppBar
import com.example.healthhelper.dietary.components.button.AddNewDietDiaryItemButton
import com.example.healthhelper.dietary.components.button.DownloadButton
import com.example.healthhelper.dietary.components.button.MealButton
import com.example.healthhelper.dietary.components.picker.datepicker.CustomDatePicker
import com.example.healthhelper.dietary.dataclasses.vo.SelectedMealOptionVO
import com.example.healthhelper.dietary.repository.SelectedMealOptionRepository
import com.example.healthhelper.dietary.viewmodel.DiaryViewModel
import com.example.healthhelper.dietary.viewmodel.MealsOptionViewModel
import com.example.healthhelper.dietary.viewmodel.SelectedMealOptionViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun DietDiaryMainFrame(
    navController: NavHostController,
    diaryViewModel: DiaryViewModel = viewModel(),
    mealsOptionViewModel: MealsOptionViewModel = viewModel(),
    selectedMealOptionViewModel: SelectedMealOptionViewModel = viewModel(),
) {
    val context = LocalContext.current

    val mealsOptions by mealsOptionViewModel.data.collectAsState()

    val verticalScrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            QueryTopAppBar(
                navController = navController,
                title = { Text(stringResource(R.string.diet_diary_main_frame_title)) },
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.95f)
                        .verticalScroll(verticalScrollState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                            .fillMaxWidth()
                    )

                    CustomDatePicker()

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
                        val innerIconId = mealsOption.innerIconId
                        val innerText =
                            @Composable {
                                Text(
                                    text = mealsOption.mealsOptionText,
                                    color = Color.White,
                                )
                            }
                        MealButton(
                            outerIconButtonModifier = outerIconButtonModifier,
                            outerIconButtonColor = outerIconButtonColor,
                            onClick = {
                                SelectedMealOptionRepository.setData(SelectedMealOptionVO(name = mealsOption.mealsOptionText))
                            },
                            innerIconId = innerIconId,
                            spacerModifier = spacerModifier,
                            innerText = innerText,
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(0.05f)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(0.75f)
                        ) {

                        }
                        Column(
                            modifier = Modifier
                                .weight(0.25f)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(0.5f),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    DownloadButton(
                                        context = context,
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(0.5f),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    AddNewDietDiaryItemButton(navController)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DietDiaryMainFramePreview() {
    val navController = rememberNavController()
    DietDiaryMainFrame(navController);
}