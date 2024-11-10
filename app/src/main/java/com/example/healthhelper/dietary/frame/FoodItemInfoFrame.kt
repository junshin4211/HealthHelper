package com.example.healthhelper.dietary.frame

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.attr.viewmodel.DefaultColorViewModel
import com.example.healthhelper.dietary.components.bar.appbar.topappbar.FoodItemTopAppBar
import com.example.healthhelper.dietary.components.button.DeleteButton
import com.example.healthhelper.dietary.components.button.MyButton
import com.example.healthhelper.dietary.components.dropdown.dropmenu.MyExposedDropDownMenu
import com.example.healthhelper.dietary.components.textfield.outlinedtextfield.TextFieldWithText
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum
import com.example.healthhelper.dietary.repository.EnterStatusRepository
import com.example.healthhelper.dietary.repository.FoodItemRepository
import com.example.healthhelper.dietary.repository.SelectedFoodItemsRepository
import com.example.healthhelper.dietary.viewmodel.FoodItemViewModel
import com.example.healthhelper.dietary.viewmodel.MealsOptionViewModel
import com.example.healthhelper.dietary.viewmodel.SelectedFoodItemsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItemInfoFrame(
    navController: NavHostController,
    mealOptionViewModel: MealsOptionViewModel = viewModel(),
    selectedFoodItemsViewModel: SelectedFoodItemsViewModel = viewModel(),
    foodItemViewModel: FoodItemViewModel = viewModel(),
) {

    val TAG = "tag_FoodItemInfoFrame"

    val context = LocalContext.current

    val mealOptions by mealOptionViewModel.data.collectAsState()
    val foodItemVO by foodItemViewModel.selectedData.collectAsState()

    val selectedFoodItem by selectedFoodItemsViewModel.selectedData.collectAsState()

    var deleteButtonIsClicked by remember { mutableStateOf(false) }
    var saveButtonIsClicked by remember { mutableStateOf(false) }
    val mealOptionNames by remember { mutableStateOf(mutableListOf<String>()) }

    LaunchedEffect(Unit) {
        mealOptions.forEach {
            mealOptionNames.add(context.getString(it.nameResId))
        }
    }

    LaunchedEffect(Unit) {
        EnterStatusRepository.setIsFirstEnter(false)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            FoodItemTopAppBar(
                navController = navController,
                title = {
                    Text(selectedFoodItem.meal.value)
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(color = colorResource(R.color.backgroundcolor)),
            ) {
                Column() {
                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                            .fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp, 0.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        OutlinedTextField(
                            value = selectedFoodItem.grams.value.toInt().toString(),
                            onValueChange = {
                                if (it.isNotBlank() && it.isDigitsOnly()) {
                                    selectedFoodItem.grams.value = it.toDouble()
                                }
                            },
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier.width(200.dp),
                            colors = DefaultColorViewModel.outlinedTextFieldDefaultColors,
                        )
                        Text(
                            text = stringResource(R.string.grams),
                            modifier = Modifier
                                .padding(16.dp, 0.dp),
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                            .fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp, 0.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        MyExposedDropDownMenu(
                            navController = navController,
                            mutableStateValue = selectedFoodItem.meal,
                            label = {},
                            modifier = Modifier.width(200.dp),
                            onValueChangedEvent = {
                                selectedFoodItem.meal.value = it
                            },
                            options = mealOptionNames,
                            outlinedTextFieldColor = DefaultColorViewModel.outlinedTextFieldDefaultColors,
                            readOnly = true,
                        )
                        Image(
                            painter = painterResource(R.drawable.meal_icon),
                            contentDescription = stringResource(R.string.meal_icon),
                            modifier = Modifier
                                .padding(16.dp, 0.dp),
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    DeleteButton(
                        buttonModifier = Modifier.size(100.dp, 40.dp),
                        onClick = {
                            deleteButtonIsClicked = true
                            saveButtonIsClicked = false
                        },
                        buttonColors = DefaultColorViewModel.buttonColors,
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    MyButton(
                        buttonModifier = Modifier.size(100.dp, 40.dp),
                        onClick = {
                            saveButtonIsClicked = true
                            deleteButtonIsClicked = false
                        },
                        buttonColors = ButtonDefaults.buttonColors(colorResource(R.color.primarycolor)),
                        text = {
                            Text(stringResource(R.string.save))
                        },
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                        .fillMaxWidth()
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "${stringResource(R.string.nutrition)}${stringResource(R.string.ingredient)}",
                        fontSize = 36.sp,
                    )
                    TextFieldWithText(
                        leadingText = stringResource(R.string.calories),
                        trailingText = "75 kcal"
                    )
                    TextFieldWithText(
                        leadingText = stringResource(R.string.fat),
                        trailingText = "5 g"
                    )
                    TextFieldWithText(
                        leadingText = stringResource(R.string.carb),
                        trailingText = "5 g"
                    )
                    TextFieldWithText(
                        leadingText = stringResource(R.string.protein),
                        trailingText = "7 g "
                    )
                    TextFieldWithText(
                        leadingText = stringResource(R.string.cholesterol),
                        trailingText = "186 mg"
                    )
                    TextFieldWithText(
                        leadingText = stringResource(R.string.natrium),
                        trailingText = "200 mg"
                    )
                }
            }
        }
    )

    if (deleteButtonIsClicked) {
        SelectedFoodItemsRepository.remove(selectedFoodItem)
        Toast.makeText(
            context,
            stringResource(R.string.delete_data_successfully),
            Toast.LENGTH_LONG
        ).show()
        deleteButtonIsClicked = false
        navController.navigateUp()
    } else if (saveButtonIsClicked) {
        val mealCategoryId = getMealCategoryIdByName(selectedFoodItem.meal.value)
        FoodItemRepository.setSelectedGrams(selectedFoodItem.grams.value)
        FoodItemRepository.setSelectedMealCategoryId(mealCategoryId)
        LaunchedEffect(Unit) {
            Log.e(TAG,"-".repeat(50))
            Log.e(TAG,"foodItemVO:${foodItemVO}")
            val affectedRows = foodItemViewModel.updateMealCategoryID(foodItemVO)
            Log.e(TAG,"affectedRows of foodItemViewModel.updateMealCategoryID(foodItemVO):${affectedRows}")
        }
        Toast.makeText(context, stringResource(R.string.save_data_successfully), Toast.LENGTH_LONG)
            .show()
        saveButtonIsClicked = false
        navController.navigate(DietDiaryScreenEnum.DietDiaryMainFrame.name)
    }
}

@Composable
fun getMealCategoryIdByName(
    targetMealName:String,
    mealOptionViewModel: MealsOptionViewModel = viewModel(),
):Int{
    val mealsOptionVOs by mealOptionViewModel.data.collectAsState()
    val mealNames = mealsOptionVOs.map { stringResource(it.nameResId) }
    val mealCategoryId = mealNames.indexOf(targetMealName)
    if(mealCategoryId == -1){
        return 0
    }
    return mealCategoryId + 1
}