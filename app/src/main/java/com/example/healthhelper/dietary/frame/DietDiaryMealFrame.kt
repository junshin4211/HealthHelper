package com.example.healthhelper.dietary.frame

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.healthhelper.R
import com.example.healthhelper.attr.viewmodel.DefaultTextStyleViewModel
import com.example.healthhelper.dietary.components.bar.appbar.topappbar.DietAppTopBar
import com.example.healthhelper.dietary.components.button.DietDiaryDescriptionButton
import com.example.healthhelper.dietary.components.button.DietDiaryImageButton
import com.example.healthhelper.dietary.components.combo.NutritionInfoCombo
import com.example.healthhelper.dietary.components.textfield.outlinedtextfield.SearchTextFieldWithDropDownMenuItem
import com.example.healthhelper.dietary.dataclasses.vo.MealsOptionVO
import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum
import com.example.healthhelper.dietary.enumclass.MealCategoryEnum
import com.example.healthhelper.dietary.interaction.database.LoadFoodDescription
import com.example.healthhelper.dietary.interaction.database.SaveFoodDescription
import com.example.healthhelper.dietary.repository.DiaryDescriptionRepository
import com.example.healthhelper.dietary.repository.SelectedFoodItemsRepository
import com.example.healthhelper.dietary.viewmodel.DiaryDescriptionViewModel
import com.example.healthhelper.dietary.viewmodel.EnterStatusViewModel
import com.example.healthhelper.dietary.viewmodel.MealsOptionViewModel
import com.example.healthhelper.dietary.viewmodel.NutritionInfoViewModel
import com.example.healthhelper.dietary.viewmodel.SelectedFoodItemsViewModel
import com.example.healthhelper.person.widget.SaveButton

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun DietDiaryMealFrame(
    navController: NavHostController = rememberNavController(),
    selectedFoodItemsViewModel: SelectedFoodItemsViewModel = viewModel(),
    nutritionInfoViewModel: NutritionInfoViewModel = viewModel(),
    mealsOptionViewModel: MealsOptionViewModel = viewModel(),
    enterStatusViewModel: EnterStatusViewModel = viewModel(),
    diaryDescriptionViewModel: DiaryDescriptionViewModel = viewModel(),
) {
    val TAG = "tag_DietDiaryMealFrame"

    val context = LocalContext.current

    val verticalScrollState = rememberScrollState()

    val foodItems by selectedFoodItemsViewModel.data.collectAsState()
    val nutritionInfo by nutritionInfoViewModel.data.collectAsState()
    val selectedFoodItem by selectedFoodItemsViewModel.selectedData.collectAsState()
    val selectedMealOption by mealsOptionViewModel.selectedData.collectAsState()
    val diaryDescriptionVO by diaryDescriptionViewModel.data.collectAsState()
    val enterStatusVO by enterStatusViewModel.isFirstEnter.collectAsState()

    var availableFoodItems by remember { mutableStateOf(listOf<SelectedFoodItemVO>()) }

    var descriptionText by remember { mutableStateOf(diaryDescriptionVO.description) }

    var dietDiaryImageButtonText by remember { mutableStateOf(context.getString(R.string.add_graph)) }
    var dietDiaryDescriptionButtonText by remember { mutableStateOf(context.getString(R.string.add_description)) }

    var iconButtonIsClicked by remember { mutableStateOf(false) }
    var dietDiaryImageButtonIsClicked by remember { mutableStateOf(false) }
    var dietDiaryDescriptionButtonIsClicked by remember { mutableStateOf(false) }
    var saveFoodDescriptionButtonIsClicked by remember { mutableStateOf(false) }

    var isCleanEventTriggeredMeetPrerequisites by remember { mutableStateOf(false) }
    var isCleanEventTriggered by remember { mutableStateOf(false) }

    var shouldShowDescription by remember { mutableStateOf(false) }

    val currentMealCategoryId = getMealCategoryId(context)
    DiaryDescriptionRepository.setMealCategoryId(currentMealCategoryId)

    // load diary description from database and try to set it into repo -- DiaryDescriptionRepository if one can.
    LoadFoodDescription(context)

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

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            selectedImageUri = uri
        }
    )

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
                                                    iconButtonIsClicked = true
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
                                .weight(0.3f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 16.dp),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                DietDiaryImageButton(
                                    buttonModifier = Modifier.size(130.dp, 40.dp),
                                    onClick = {
                                        dietDiaryImageButtonIsClicked = true
                                    },
                                    buttonColors = ButtonDefaults.buttonColors(colorResource(R.color.primarycolor)),
                                    text = { Text(dietDiaryImageButtonText) }
                                )
                                Spacer(modifier = Modifier.width(20.dp))
                                DietDiaryDescriptionButton(
                                    buttonModifier = Modifier.size(130.dp, 40.dp),
                                    onClick = {
                                        dietDiaryDescriptionButtonIsClicked = true
                                        if (isCleanEventTriggeredMeetPrerequisites) {
                                            descriptionText = mutableStateOf("")
                                        }
                                    },
                                    buttonColors = ButtonDefaults.buttonColors(colorResource(R.color.primarycolor)),
                                    text = { Text(dietDiaryDescriptionButtonText) }
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 16.dp),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                selectedImageUri?.let { uri ->
                                    Image(
                                        painter = rememberAsyncImagePainter(uri),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(200.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                MyOutLinedTextField(
                                    value = descriptionText.value,
                                    onValueChange = {
                                        Log.e(TAG, "it:${it}")
                                        descriptionText.value = it
                                    },
                                    isVisible = shouldShowDescription,
                                )
                                isCleanEventTriggered = false
                            }
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .verticalScroll(verticalScrollState),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top,
                            ) {

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

                                Spacer(modifier = Modifier.height(10.dp))

                                SaveButton(
                                    onClick = {
                                        saveFoodDescriptionButtonIsClicked = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    })

    if (iconButtonIsClicked) {
        SelectedFoodItemsRepository.setSelectedDataMealValue(stringResource(selectedMealOption.nameResId))
        SelectedFoodItemsRepository.setCheckedWhenSelectionState(selectedFoodItem, true)
        Log.e(TAG, "At IconButton onClick event triggers,selectedFoodItem:${selectedFoodItem}")
        navController.navigate(
            DietDiaryScreenEnum.FoodItemInfoFrame.name
        )
        iconButtonIsClicked = false
    }

    if (dietDiaryImageButtonIsClicked) {
        pickImageLauncher.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )

        dietDiaryImageButtonIsClicked = false
    }

    LaunchedEffect(selectedImageUri) {
        Log.e(TAG, "After opening the photo picker, selectedImageUri:${selectedImageUri}")
        val id = if (selectedImageUri != null) R.string.delete_graph else R.string.add_graph
        dietDiaryImageButtonText = context.getString(id)
    }


    if (dietDiaryDescriptionButtonIsClicked) {
        dietDiaryDescriptionButtonText = context.getString(R.string.clear_description_text)
        shouldShowDescription = true
        isCleanEventTriggeredMeetPrerequisites = true
        dietDiaryDescriptionButtonIsClicked = false
    }

    if (saveFoodDescriptionButtonIsClicked) {
        Log.e(TAG, "saveFoodDescriptionButtonIsClicked is true.")
        SaveFoodDescription(
            navController = navController,
            context = context,
            foodIconUri = selectedImageUri,
            foodDescription = descriptionText.value,
        )
        saveFoodDescriptionButtonIsClicked = false
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
            SelectedFoodItemsRepository.setMealValue(
                foodItem,
                context.getString(mealsOptionVO.nameResId)
            )
        })
}

@Composable
fun MyOutLinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isVisible: Boolean,
) {
    if (isVisible) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.size(200.dp, 60.dp),
            textStyle = DefaultTextStyleViewModel.outlinedTextFieldTextStyle,
            maxLines = 1,
        )
    }
}

@Composable
fun getMealCategoryId(
    context: Context,
    mealsOptionViewModel: MealsOptionViewModel = viewModel(),
): Int {
    val mealsOptionVOs by mealsOptionViewModel.data.collectAsState()
    val selectedMealOption by mealsOptionViewModel.selectedData.collectAsState()
    val index = mealsOptionVOs.indexOf(selectedMealOption)
    if(index == -1){
        return 0 + 1
    }
    return index + 1
}