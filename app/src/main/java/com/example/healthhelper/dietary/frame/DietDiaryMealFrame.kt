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
import androidx.compose.material.TextField
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.dietary.components.bar.appbar.topappbar.DietAppTopBar
import com.example.healthhelper.dietary.components.combo.NutritionInfoCombo
import com.example.healthhelper.dietary.components.combo.SaveGraphAndTextRecordButton
import com.example.healthhelper.dietary.components.textfield.outlinedtextfield.SearchTextFieldWithDropDownMenuItem
import com.example.healthhelper.dietary.dataclasses.vo.MealsOptionVO
import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum
import com.example.healthhelper.dietary.enumclass.MealCategoryEnum
import com.example.healthhelper.dietary.repository.DietDiaryDescriptionRepository
import com.example.healthhelper.dietary.repository.SelectedFoodItemsRepository
import com.example.healthhelper.dietary.viewmodel.DietDiaryIconViewModel
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
    dietDiaryIconViewModel: DietDiaryIconViewModel = viewModel(),
) {
    val TAG = "tag_DietDiaryMealFrame"

    val context = LocalContext.current
    val verticalScrollState = rememberScrollState()

    val foodItems by selectedFoodItemsViewModel.data.collectAsState()
    val nutritionInfo by nutritionInfoViewModel.data.collectAsState()
    val selectedFoodItem by selectedFoodItemsViewModel.selectedData.collectAsState()
    val selectedMealOption by mealsOptionViewModel.selectedData.collectAsState()
    val dietDiaryVO by dietDiaryIconViewModel.data.collectAsState()
    var availableFoodItems by remember { mutableStateOf(listOf<SelectedFoodItemVO>()) }

    var saveGraphTextButtonHasClicked by remember { mutableStateOf(false) }
    var saveGraphTextButtonIsClicked by remember { mutableStateOf(false) }
    var deletingGraphState by remember { mutableStateOf(false) }
    var saveTextRecordTextButtonIsClicked by remember { mutableStateOf(false) }
    var saveTextRecordTextButtonHasClicked by remember { mutableStateOf(false) }
    var deletingTextFieldState by remember { mutableStateOf(false) }
    var iconButtonIsClickable by remember { mutableStateOf(false) }

    var saveGraphButtonText by remember { mutableStateOf(context.getString(R.string.save_graph)) }
    var saveDescriptionButtonText by remember { mutableStateOf(context.getString(R.string.text_record)) }

    var description by remember { mutableStateOf("") }
    var oldDescription by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        description = dietDiaryVO.description.value
        oldDescription = dietDiaryVO.description.value
    }

    val enterStatusVO by enterStatusViewModel.isFirstEnter.collectAsState()

    availableFoodItems = if (enterStatusVO.isFirstEnter.value) {
        foodItems.filter {
            it.meal.value in listOf(
                stringResource(selectedMealOption.nameResId),
                stringResource(MealCategoryEnum.EMPTY_STRING.title)
            )
        }
    } else {
        foodItems.filter {
            it.meal.value in listOf(
                stringResource(selectedMealOption.nameResId),
            )
        }
    }

    LaunchedEffect(Unit) {
        saveGraphTextButtonHasClicked = false
        deletingGraphState = false
        deletingTextFieldState = false
    }

    Log.e(TAG, "At startup,selectedMealOption:${selectedMealOption}")
    Log.e(TAG, "At startup,availableFoodItems:${availableFoodItems}")

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        DietAppTopBar(
            navController = navController,
            title = { Text(stringResource(selectedMealOption.nameResId)) },
        )
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
                                    outerButtonModifier = Modifier.size(400.dp, 40.dp),
                                    saveGraph = {
                                        saveGraphTextButtonIsClicked = !saveGraphTextButtonIsClicked
                                        saveTextRecordTextButtonIsClicked = saveGraphTextButtonIsClicked
                                        saveGraphTextButtonHasClicked = true
                                        saveTextRecordTextButtonHasClicked = true
                                    },
                                    leftTextButtonTextFontSize = 20.sp,
                                    leftTextButtonText = saveGraphButtonText,
                                    saveTextRecord = {
                                        saveTextRecordTextButtonIsClicked = !saveTextRecordTextButtonIsClicked
                                        saveGraphTextButtonIsClicked = saveTextRecordTextButtonIsClicked
                                        saveTextRecordTextButtonHasClicked = true
                                        saveGraphTextButtonHasClicked = true
                                    },
                                    rightTextButtonTextFontSize = 20.sp,
                                    rightTextButtonText = saveDescriptionButtonText,
                                    )
                                if (saveGraphTextButtonIsClicked || saveTextRecordTextButtonIsClicked) {
                                    Box(
                                        modifier = Modifier
                                            .size(600.dp, 200.dp)
                                            .padding(16.dp)
                                    ) {
                                        Image(
                                            painterResource(dietDiaryVO.iconResId),
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
                                        TextField(
                                            value = description,
                                            onValueChange = {
                                                if(it != "") {
                                                    description = it
                                                }
                                            },
                                            modifier = Modifier.fillMaxSize(),
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
                                            fontSize = 24.sp,
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

    LaunchedEffect(deletingGraphState) {
        saveGraphButtonText = if (!deletingGraphState) context.getString(R.string.save_graph) else context.getString(R.string.delete_graph)

        // TODO
        if(!deletingGraphState){
            // TODO Delete
            DietDiaryDescriptionRepository.setIconResId(R.drawable.meal_icon)
            Toast.makeText(context,"Delete the graph successfully.",Toast.LENGTH_LONG).show()
        }else{
            DietDiaryDescriptionRepository.setIconResId(R.drawable.postpic)
            Toast.makeText(context,"Save the graph successfully.",Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(deletingTextFieldState) {
        saveDescriptionButtonText = if(!deletingTextFieldState) context.getString(R.string.text_record) else context.getString(R.string.save_text_record)

        // TODO
        if(!deletingTextFieldState){
            // TODO Clear
            // DietDiaryDescriptionRepository.setDescription("")
            Toast.makeText(context,"Clear the description in OutlinedTextField successfully.",Toast.LENGTH_LONG).show()
        }else{
            DietDiaryDescriptionRepository.setDescription(description)
            Toast.makeText(context,"Save the description in OutlinedTextField successfully.",Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(saveGraphTextButtonIsClicked) {
        if (saveGraphTextButtonHasClicked && saveGraphTextButtonIsClicked) {
            deletingGraphState = !deletingGraphState
        }
    }

    LaunchedEffect(saveTextRecordTextButtonIsClicked) {
        if (saveTextRecordTextButtonHasClicked && saveTextRecordTextButtonIsClicked) {
            deletingTextFieldState = !deletingTextFieldState
        }
    }

    // test
    LaunchedEffect(saveGraphTextButtonIsClicked) {
        Log.e(TAG,"test. saveGraphTextButtonIsClicked is changed to ${saveGraphTextButtonIsClicked}")
    }

    LaunchedEffect(saveTextRecordTextButtonIsClicked) {
        Log.e(TAG,"test. saveTextRecordTextButtonIsClicked is changed to ${saveTextRecordTextButtonIsClicked}")
    }

    LaunchedEffect(deletingGraphState) {
        Log.e(TAG,"test. deletingGraphState is changed to ${deletingGraphState}")
    }

    LaunchedEffect(deletingTextFieldState) {
        Log.e(TAG,"test. deletingGraphState is changed to ${deletingTextFieldState}")
    }
    // end test
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
            SelectedFoodItemsRepository.setMealValue(
                foodItem,
                context.getString(mealsOptionVO.nameResId)
            )
        })
}