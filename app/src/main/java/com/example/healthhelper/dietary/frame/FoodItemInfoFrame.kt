package com.example.healthhelper.dietary.frame

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.attr.color.defaultcolor.DefaultColorViewModel
import com.example.healthhelper.dietary.components.bar.appbar.topappbar.FoodItemTopAppBar
import com.example.healthhelper.dietary.components.button.DeleteButton
import com.example.healthhelper.dietary.components.button.SaveButton
import com.example.healthhelper.dietary.components.dropdown.dropmenu.MyExposedDropDownMenu
import com.example.healthhelper.dietary.components.textfield.outlinedtextfield.TextFieldWithText
import com.example.healthhelper.dietary.viewmodel.MealsOptionViewModel
import com.example.healthhelper.dietary.viewmodel.SelectedFoodItemViewModel
import com.example.healthhelper.dietary.viewmodel.SelectedMealOptionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItemInfoFrame(
    navController: NavHostController,
    selectedMealOptionViewModel: SelectedMealOptionViewModel = viewModel(),
    selectedFoodItemViewModel: SelectedFoodItemViewModel = viewModel(),
    mealOptionViewModel: MealsOptionViewModel = viewModel(),
) {
    val selectedMealOption by selectedMealOptionViewModel.data.collectAsState()
    val selectedFoodItem by selectedFoodItemViewModel.data.collectAsState()
    val mealOption by mealOptionViewModel.data.collectAsState()

    val mutableStateString = remember { mutableStateOf(selectedMealOption.name) }

    val options by remember { mutableStateOf(mutableListOf<String>()) }

    var deleteButtonClicked by remember { mutableStateOf(false) }
    var saveButtonClicked by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        mealOption.forEach {
            options.add(it.mealsOptionText)
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            FoodItemTopAppBar(
                navController = navController,
                title = {
                    Text(selectedFoodItem.name)
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                Column(

                ) {
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
                            value = selectedFoodItem.grams.toInt().toString(),
                            onValueChange = {},
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier.width(200.dp),
                            colors = DefaultColorViewModel.outlinedTextFieldDefaultColors,
                        )
                        Text(
                            text = "grams",
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
                            mutableStateValue = mutableStateString,
                            label = {},
                            modifier = Modifier.width(200.dp),
                            onValueChangedEvent = { mutableStateString.value = it },
                            options = options,
                            outlinedTextFieldColor = DefaultColorViewModel.outlinedTextFieldDefaultColors,
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
                        onClick = {
                            deleteButtonClicked = true
                            saveButtonClicked = false
                        },
                        buttonColors = DefaultColorViewModel.buttonColors,
                    )
                    Spacer(modifier = Modifier.width(20.dp))

                    SaveButton(
                        onClick = {
                            saveButtonClicked = true
                            deleteButtonClicked = false
                        },
                        buttonColors = DefaultColorViewModel.buttonColors,
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

    if (deleteButtonClicked) {
        // TODO
        deleteButtonClicked = false
    } else if (saveButtonClicked) {
        // TODO
        saveButtonClicked = false
    }
}
