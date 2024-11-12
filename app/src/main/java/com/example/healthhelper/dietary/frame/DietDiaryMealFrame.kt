package com.example.healthhelper.dietary.frame

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import androidx.lifecycle.viewModelScope
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
import com.example.healthhelper.dietary.dataclasses.vo.FoodItemVO
import com.example.healthhelper.dietary.dataclasses.vo.FoodVO
import com.example.healthhelper.dietary.dataclasses.vo.MealsOptionVO
import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum
import com.example.healthhelper.dietary.enumclass.MealCategoryEnum
import com.example.healthhelper.dietary.interaction.database.LoadFoodDescription
import com.example.healthhelper.dietary.interaction.database.LoadFoodItemInfo
import com.example.healthhelper.dietary.repository.DiaryDescriptionRepository
import com.example.healthhelper.dietary.repository.FoodItemRepository
import com.example.healthhelper.dietary.repository.FoodRepository
import com.example.healthhelper.dietary.repository.SelectedFoodItemsRepository
import com.example.healthhelper.dietary.viewmodel.DiaryDescriptionViewModel
import com.example.healthhelper.dietary.viewmodel.DiaryViewModel
import com.example.healthhelper.dietary.viewmodel.FoodItemViewModel
import com.example.healthhelper.dietary.viewmodel.FoodViewModel
import com.example.healthhelper.dietary.viewmodel.MealsOptionViewModel
import com.example.healthhelper.dietary.viewmodel.NutritionInfoViewModel
import com.example.healthhelper.dietary.viewmodel.SelectedFoodItemsViewModel
import com.example.healthhelper.person.widget.SaveButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun DietDiaryMealFrame(
    navController: NavHostController = rememberNavController(),
    selectedFoodItemsViewModel: SelectedFoodItemsViewModel = viewModel(),
    nutritionInfoViewModel: NutritionInfoViewModel = viewModel(),
    mealsOptionViewModel: MealsOptionViewModel = viewModel(),
    diaryDescriptionViewModel: DiaryDescriptionViewModel = viewModel(),
    foodViewModel: FoodViewModel = viewModel(),
    diaryViewModel: DiaryViewModel = viewModel(),
    foodItemViewModel: FoodItemViewModel = viewModel(),
) {
    val TAG = "tag_DietDiaryMealFrame"

    val context = LocalContext.current

    val verticalScrollState = rememberScrollState()

    val foodItems by selectedFoodItemsViewModel.data.collectAsState()
    val selectedFoodItem by selectedFoodItemsViewModel.selectedData.collectAsState()

    val nutritionInfo by nutritionInfoViewModel.data.collectAsState()

    val mealsOptionVOs by mealsOptionViewModel.data.collectAsState()
    val selectedMealsOptionVO by mealsOptionViewModel.selectedData.collectAsState()

    val diaryDescriptionVO by diaryDescriptionViewModel.data.collectAsState()
    val foodVO by foodViewModel.data.collectAsState()
    val diaryVO by diaryViewModel.data.collectAsState()
    val foodItemVOs by foodItemViewModel.data.collectAsState()

    var availableFoodItems by remember { mutableStateOf(listOf<SelectedFoodItemVO>()) }
    var checkedFoodItems by remember { mutableStateOf(mutableListOf<SelectedFoodItemVO>()) }

    var clickedFoodItemVO by remember { mutableStateOf(SelectedFoodItemVO(name = mutableStateOf(""))) }
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

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            selectedImageUri = uri
        }
    )

    // get id of meal category that indicates the meal.
    var currentMealCategoryId = getMealCategoryId(
        mealsOptionVOs = mealsOptionVOs,
        selectedMealsOptionVO = selectedMealsOptionVO,
    )

    // set the data in repo so that its corresponding view model can access it.
    DiaryDescriptionRepository.setMealCategoryId(currentMealCategoryId)
    // load diary description from database and try to set it into repo -- DiaryDescriptionRepository if one can.
    LoadFoodDescription(context)

    // set the data in repo so that its corresponding view model can access it.
    FoodItemRepository.setSelectedMealCategoryId(currentMealCategoryId)
    // load food items about this diaryId from database and try to set it into repo -- FoodItemRepository if one can.
    LoadFoodItemInfo(context)

    availableFoodItems =
        if (foodItems.isNotEmpty()) {
            foodItems.filter {
                it.meal.value in listOf(
                    context.getString(selectedMealsOptionVO.nameResId),
                    context.getString(MealCategoryEnum.EMPTY_STRING.title)
                )
            }
        } else {
            emptyList()
        }
    checkedFoodItems =
        availableFoodItems?.filter { it.isCheckedWhenSelection.value }?.toMutableList()
            ?: mutableListOf()

    LaunchedEffect(diaryDescriptionVO) {
        Log.e(
            TAG,
            "In DietDiaryMealFrame function, LaunchedEffect(diaryDescriptionVO) block was called.diaryDescriptionVO:${diaryDescriptionVO}"
        )
        descriptionText = diaryDescriptionVO.description
        selectedImageUri =
            if (diaryDescriptionVO.uri == null) null else Uri.parse(diaryDescriptionVO.uri)
    }
    LaunchedEffect(foodItemVOs) {
        Log.e(
            TAG,
            "In DietDiaryMealFrame function, LaunchedEffect(foodItemVOs) block was called.availableFoodItems:${availableFoodItems}"
        )
        Log.e(
            TAG,
            "In DietDiaryMealFrame function, LaunchedEffect(foodItemVOs) blocked was called.foodItemVOs:${foodItemVOs}"
        )

        foodItemVOs.forEach { foodItemVO ->
            val foodId = foodItemVO.foodID
            val mealCategoryId = foodItemVO.mealCategoryID
            val newFoodVO = FoodVO()
            newFoodVO.foodID = foodId
            val foodName = foodViewModel.selectFoodNameByFoodId(newFoodVO)
            availableFoodItems.firstOrNull { it.name.value == foodName }?.isCheckedWhenSelection?.value =
                true
            availableFoodItems.firstOrNull { it.name.value == foodName }?.meal?.value =
                when (mealCategoryId) {
                    1 -> context.getString(MealCategoryEnum.BREAKFAST.title)
                    2 -> context.getString(MealCategoryEnum.LUNCH.title)
                    3 -> context.getString(MealCategoryEnum.DINNER.title)
                    4 -> context.getString(MealCategoryEnum.SUPPER.title)
                    else -> context.getString(MealCategoryEnum.EMPTY_STRING.title)
                }
        }
    }

    LaunchedEffect(saveFoodDescriptionButtonIsClicked) {
        if (!saveFoodDescriptionButtonIsClicked) return@LaunchedEffect

        // save data to database about fooditem table.
        foodViewModel.viewModelScope.launch {
            Log.e(
                TAG,
                "In DietDiaryMealFrame,DisposableEffect(Unit),onDispose,foodViewModel.viewModelScope.launch block.checkedFoodItems:${checkedFoodItems}"
            )
            Log.e(
                TAG,
                "In DietDiaryMealFrame,DisposableEffect(Unit),onDispose,foodViewModel.viewModelScope.launch block.currentMealCategoryId:${currentMealCategoryId}"
            )
            checkedFoodItems.forEach { checkedFoodItem ->
                val foodName = checkedFoodItem.name.value
                val newFoodVO = FoodVO()
                newFoodVO.foodName = foodName
                val foodId = foodViewModel.selectFoodIdByFoodName(newFoodVO)
                val newFoodItemVO = FoodItemVO(
                    diaryID = diaryVO.diaryID,
                    foodID = foodId,
                    mealCategoryID = currentMealCategoryId,
                    grams = 100.0,
                )
                Log.e(
                    TAG,
                    "\"In DietDiaryMealFrame,DisposableEffect(Unit),onDispose,foodViewModel.viewModelScope.launch block. newFoodItemVO:${newFoodItemVO}"
                )
                foodItemViewModel.tryToInsertFoodItem(newFoodItemVO)
            }
            Toast.makeText(
                context,
                context.getString(R.string.save_food_item_successfully),
                Toast.LENGTH_LONG
            ).show()
        }
        // save data to database about diarydescription table.
        diaryDescriptionViewModel.viewModelScope.launch {
            Log.e(
                TAG,
                "\"In DietDiaryMealFrame,DisposableEffect(Unit),onDispose, diaryDescriptionViewModel.viewModelScope.launch block. diaryDescriptionVO:${diaryDescriptionVO}"
            )
            DiaryDescriptionRepository.setDiaryId(diaryVO.diaryID)
            DiaryDescriptionRepository.setMealCategoryId(currentMealCategoryId)
            DiaryDescriptionRepository.setDescription(descriptionText)
            DiaryDescriptionRepository.setUri(selectedImageUri?.toString())
            Log.e(
                TAG,
                "\"In DietDiaryMealFrame,DisposableEffect(Unit),onDispose, diaryDescriptionViewModel.viewModelScope.launch block. diaryDescriptionVO:${diaryDescriptionVO}"
            )
            diaryDescriptionViewModel.tryToInsert(diaryDescriptionVO)
            Toast.makeText(
                context,
                context.getString(R.string.save_food_description_successfully),
                Toast.LENGTH_LONG
            ).show()
        }
        navController.navigate(DietDiaryScreenEnum.FoodItemInfoFrame.name)
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        DietAppTopBar(
            navController = navController,
            title = { Text(stringResource(selectedMealsOptionVO.nameResId)) },
        )
    }, content = { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = colorResource(R.color.backgroundcolor))
                .verticalScroll(rememberScrollState()),
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
                        checkedFoodItems.forEach { foodItem ->
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
                                                    mealsOptionVO = selectedMealsOptionVO,
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
                                                SelectedFoodItemsRepository.setSelectedData(foodItem)
                                                clickedFoodItemVO = foodItem
                                                iconButtonIsClicked = true
                                            }) {
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
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
                                            descriptionText = ""
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
                                    value = descriptionText,
                                    onValueChange = { descriptionText = it },
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
        SelectedFoodItemsRepository.setSelectedDataMealValue(stringResource(selectedMealsOptionVO.nameResId))
        SelectedFoodItemsRepository.setCheckedWhenSelectionState(selectedFoodItem, true)
        FoodRepository.setFoodName(clickedFoodItemVO.name.value)
        LaunchedEffect(Unit) {
            val currentFoodId = foodViewModel.selectFoodIdByFoodName(foodVO)
            Log.e(TAG, "LaunchedEffect(Unit) was called. currentFoodId:${currentFoodId}")
            FoodItemRepository.setSelectedFoodId(currentFoodId)
        }

        FoodItemRepository.setSelectedGrams(clickedFoodItemVO.grams.value)
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

fun getMealCategoryId(
    mealsOptionVOs: List<MealsOptionVO>,
    selectedMealsOptionVO: MealsOptionVO,
): Int {
    val TAG = "tag_getMealCategoryId"
    Log.e(TAG, "-".repeat(50))
    Log.e(TAG, "getMealCategoryId function was called. mealsOptionVOs:${mealsOptionVOs}")
    Log.e(
        TAG,
        "getMealCategoryId function was called. selectedMealsOptionVO:${selectedMealsOptionVO}"
    )
    val index = mealsOptionVOs.indexOf(selectedMealsOptionVO)
    Log.e(TAG, "getMealCategoryId function was called. index:${index}")
    Log.e(TAG, "-".repeat(50))
    if (index == -1) {
        return 0
    }
    return index + 1
}