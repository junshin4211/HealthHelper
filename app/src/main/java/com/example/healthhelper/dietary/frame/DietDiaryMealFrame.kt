package com.example.healthhelper.dietary.frame

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.attr.viewmodel.DefaultColorViewModel
import com.example.healthhelper.dietary.components.bar.appbar.topappbar.DietAppTopBar
import com.example.healthhelper.dietary.components.button.DeleteButton
import com.example.healthhelper.dietary.components.button.DownloadButton
import com.example.healthhelper.dietary.components.combo.NutritionInfoCombo
import com.example.healthhelper.dietary.components.combo.SaveGraphAndTextRecordButton
import com.example.healthhelper.dietary.components.iconbutton.AddIcon
import com.example.healthhelper.dietary.components.textfield.outlinedtextfield.SearchTextFieldWithDropDownMenuItem
import com.example.healthhelper.dietary.dataclasses.vo.MealsOptionVO
import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum
import com.example.healthhelper.dietary.enumclass.MealCategoryEnum
import com.example.healthhelper.dietary.repository.SelectedFoodItemsRepository
import com.example.healthhelper.dietary.util.downloaddata.DownloadData
import com.example.healthhelper.dietary.viewmodel.EnterStatusViewModel
import com.example.healthhelper.dietary.viewmodel.MealsOptionViewModel
import com.example.healthhelper.dietary.viewmodel.NutritionInfoViewModel
import com.example.healthhelper.dietary.viewmodel.SelectedFoodItemsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun DietDiaryMealFrame(
    navController: NavHostController = rememberNavController(),
    selectedFoodItemsViewModel: SelectedFoodItemsViewModel = viewModel(),
    nutritionInfoViewModel: NutritionInfoViewModel = viewModel(),
    mealsOptionViewModel: MealsOptionViewModel = viewModel(),
    enterStatusViewModel: EnterStatusViewModel = viewModel(),
) {
    val TAG = "tag_DietDiaryMealFrame"

    val context = LocalContext.current
    val verticalScrollState = rememberScrollState()

    val foodItems by selectedFoodItemsViewModel.data.collectAsState()
    val nutritionInfo by nutritionInfoViewModel.data.collectAsState()

    val selectedFoodItem by selectedFoodItemsViewModel.selectedData.collectAsState()
    val selectedMealOption by mealsOptionViewModel.selectedData.collectAsState()


    Log.e(TAG, "-".repeat(50))

    Log.e(TAG, "foodItems:${foodItems}")

    var availableFoodItems by remember { mutableStateOf(listOf<SelectedFoodItemVO>()) }

    val selectedFoodItems = remember { mutableStateOf(foodItems) }

    var deleteButtonIsClicked by remember { mutableStateOf(false) }
    var addIconButtonIsClicked by remember { mutableStateOf(false) }
    var downloadButtonIsClicked by remember { mutableStateOf(false) }
    var saveGraphTextButtonIsClicked by remember { mutableStateOf(false) }
    var saveTextRecordTextButtonIsClicked by remember { mutableStateOf(false) }

    var iconButtonIsClickable by remember { mutableStateOf(false) }

    val enterStatusVO by enterStatusViewModel.isFirstEnter.collectAsState()

    if (enterStatusVO.isFirstEnter.value) {
        availableFoodItems = foodItems.filter {
            it.meal.value in listOf(
                stringResource(selectedMealOption.nameResId),
                stringResource(MealCategoryEnum.EMPTY_STRING.title)
            )
        }
    } else {
        availableFoodItems = foodItems.filter {
            it.meal.value in listOf(
                stringResource(selectedMealOption.nameResId),
            )
        }
    }

    Log.e(TAG, "At startup,selectedMealOption:${selectedMealOption}")
    Log.e(TAG, "At startup,availableFoodItems:${availableFoodItems}")
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        DietAppTopBar(
            navController = navController,
            title = { Text(stringResource(selectedMealOption.nameResId)) },
        )
    }, floatingActionButton = {
        Row() {
            DownloadButton(
                context = context,
                onClick = { downloadButtonIsClicked = true }
            )
            DeleteButton(
                onClick = { deleteButtonIsClicked = true },
                buttonColors = DefaultColorViewModel.buttonColors,
            )
            AddIcon(
                navController = navController,
                onClick = { addIconButtonIsClicked = true },
                iconButtonColors = DefaultColorViewModel.iconButtonColors
            )
        }
    }, content = { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = colorResource(R.color.backgroundcolor)),
        ) {
            Column(
                modifier = Modifier
                    .weight(0.05f)
                    .verticalScroll(verticalScrollState)
            ) {
                SearchTextFieldWithDropDownMenuItem(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    label = { Text(stringResource(R.string.search_label)) },
                )

                Column(
                    modifier = Modifier
                        .weight(0.95f)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.7f),
                    ) {
                        availableFoodItems.filter { it.isCheckedWhenSelection.value }
                            .forEach { foodItem ->
                                Box(
                                    modifier = Modifier
                                        .requiredWidth(width = 360.dp)
                                        .requiredHeight(height = 41.dp),
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .requiredWidth(width = 360.dp)
                                            .padding(horizontal = 16.dp)
                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(
                                                16.dp, Alignment.CenterHorizontally
                                            ),
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .requiredHeight(height = 40.dp)
                                        ) {
                                            Column(
                                                verticalArrangement = Arrangement.Center,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .weight(weight = 1f),
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                ) {
                                                    MyCheckBox(
                                                        context = context,
                                                        foodItem = foodItem,
                                                        mealsOptionVO = selectedMealOption,
                                                    )

                                                    Text(
                                                        text = foodItem.name.value,
                                                        color = colorResource(R.color.primarycolor),
                                                        lineHeight = 1.27.em,
                                                        style = MaterialTheme.typography.titleLarge,
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .wrapContentHeight(
                                                                align =
                                                                Alignment.CenterVertically
                                                            )
                                                    )
                                                }
                                            }
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(
                                                    10.dp, Alignment.Start
                                                )
                                            ) {
                                                IconButton(onClick = {
                                                    SelectedFoodItemsRepository.setSelectedData(
                                                        foodItem
                                                    )
                                                    iconButtonIsClickable = true
                                                }) {
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
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            HorizontalDivider(
                                                color = colorResource(R.color.primarycolor),
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                    }
                                    Box(
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        Column(
                            modifier = Modifier
                                .weight(0.3f)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .verticalScroll(verticalScrollState),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top,
                            ) {
                                SaveGraphAndTextRecordButton(
                                    outerButtonModifier = Modifier.size(250.dp, 40.dp),
                                    saveGraph = {
                                        saveGraphTextButtonIsClicked = true
                                        saveTextRecordTextButtonIsClicked = false
                                    },
                                    saveTextRecord = {
                                        saveTextRecordTextButtonIsClicked = true
                                        saveGraphTextButtonIsClicked = false
                                    },
                                )
                                if (saveGraphTextButtonIsClicked || saveTextRecordTextButtonIsClicked) {
                                    Box(
                                        modifier = Modifier
                                            .size(600.dp, 200.dp)
                                            .padding(16.dp)
                                    ) {
                                        Image(
                                            painterResource(R.drawable.postpic),
                                            contentDescription = "",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.FillBounds,
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .size(600.dp, 100.dp)
                                            .padding(16.dp)
                                            .border(
                                                1.dp,
                                                Color.Black,
                                            ),
                                    ) {
                                        Text(
                                            text = "Hello World!!!"
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                NutritionInfoCombo(
                                    nutritionInfoVO = nutritionInfo,
                                    showTitle = false,
                                    title = {
                                        Text(
                                            text = "${stringResource(R.string.total_title)}:",
                                            fontWeight = FontWeight.Bold,
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    })

    if (iconButtonIsClickable) {
        SelectedFoodItemsRepository.setSelectedDataMealValue(stringResource(selectedMealOption.nameResId))
        SelectedFoodItemsRepository.setCheckedWhenSelectionState(selectedFoodItem, true)
        Log.e(TAG, "At IconButton onClick event triggers,selectedFoodItem:${selectedFoodItem}")
        navController.navigate(
            DietDiaryScreenEnum.FoodItemInfoFrame.name
        )
        iconButtonIsClickable = false
    }

    // NOT test YET
    if (deleteButtonIsClicked) {
        val selectedFoodItemVOs by remember { mutableStateOf(selectedFoodItems.value.filter { it.isCheckingWhenSelection.value }) }
        SelectedFoodItemsRepository.setAllCheckedWhenQueryState(false)
        if (selectedFoodItemVOs.isNotEmpty()) {
            selectedFoodItemVOs.forEach { selectedFoodItemVo ->
                SelectedFoodItemsRepository.setCheckedWhenSelectionState(selectedFoodItemVo, false)
            }
            Toast.makeText(
                context,
                stringResource(R.string.delete_data_successfully),
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                context,
                stringResource(R.string.no_item_selected),
                Toast.LENGTH_LONG
            ).show()
        }
        deleteButtonIsClicked = false
    } else if (addIconButtonIsClicked) {
        val selectedFoodItemVOs by remember { mutableStateOf(selectedFoodItems.value.filter { it.isCheckingWhenQuery.value }) }
        SelectedFoodItemsRepository.setAllCheckedWhenQueryState(false)
        if (selectedFoodItemVOs.isNotEmpty()) {
            Toast.makeText(
                context,
                stringResource(R.string.add_data_successfully),
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                context,
                stringResource(R.string.no_item_selected),
                Toast.LENGTH_LONG
            ).show()
        }
        addIconButtonIsClicked = false
    } else if (downloadButtonIsClicked) {
        DownloadData(
            context = context,
            vo = foodItems
        )
        downloadButtonIsClicked = false
    }
}

@Composable
fun MyCheckBox(
    context: Context,
    foodItem: SelectedFoodItemVO,
    mealsOptionVO: MealsOptionVO,
) {
    var isChecked by remember { mutableStateOf(false) }
    Checkbox(
        checked = isChecked,
        onCheckedChange = {
            isChecked = !isChecked
            SelectedFoodItemsRepository.setCheckingWhenSelectionState(foodItem, it)
            SelectedFoodItemsRepository.setMealValue(foodItem,context.getString(mealsOptionVO.nameResId))
        })
}