package com.example.healthhelper.dietary.frame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.dietary.components.bar.appbar.topappbar.DietAppTopBar
import com.example.healthhelper.dietary.components.dialog.alertdialog.MyAlertDialog
import com.example.healthhelper.dietary.components.textfield.outlinedtextfield.DateTextField
import com.example.healthhelper.dietary.components.textfield.outlinedtextfield.FoodTextField
import com.example.healthhelper.dietary.components.textfield.outlinedtextfield.NameTextField
import com.example.healthhelper.dietary.dataclasses.vo.DiaryVO
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum
import com.example.healthhelper.dietary.repository.DiaryRepository
import com.example.healthhelper.dietary.components.textfield.outlinedtextfield.TimeTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewDietDiaryItemFrame(
    navController: NavHostController
) {
    var shouldPopUpAlertDialog by remember { mutableStateOf(false) }

    val name = remember { mutableStateOf("") }
    val dateText = remember { mutableStateOf("") }
    val timeText = remember { mutableStateOf("") }
    val foodText = remember { mutableStateOf("") }

    if (shouldPopUpAlertDialog) {
        MyAlertDialog.popUp(
            onConfirmation = { shouldPopUpAlertDialog = false },
            onDismissRequest = { shouldPopUpAlertDialog = false },
            dialogTitle = stringResource(R.string.error_invalid_input),
            dialogText = stringResource(R.string.invalid_textfield_value_for_name_and_date_and_time),
        )
    }
    Scaffold(
        topBar = {
            DietAppTopBar(
                navController = navController,
                title = {
                    Text(
                        text = stringResource(R.string.diet_app_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                hasShareButton = false,
            )
        },
        bottomBar = {

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (
                        name.value.isNotBlank() &&
                        foodText.value.isNotBlank() &&
                        dateText.value.isNotBlank() &&
                        timeText.value.isNotBlank()
                    ) {
                        val diary = DiaryVO(
                            name = name.value,
                            foodName = foodText.value,
                            date = dateText.value,
                            time = timeText.value,
                        )
                        DiaryRepository.addData(diary)

                        navController.navigate(DietDiaryScreenEnum.DietDiaryMainFrame.name)
                        return@FloatingActionButton
                    }
                    shouldPopUpAlertDialog = true
                    return@FloatingActionButton
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add_new_item_icon),
                )
            }
        },
        content = { innerPadding ->
            val textFieldWidth = 300
            val spacerHeight = 20
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                NameTextField(
                    modifier = Modifier.width(textFieldWidth.dp),
                    mutableStateValue = name,
                    onValueChange = { name.value = it },
                    label = { Text(stringResource(R.string.diary_diet_name_label)) },
                )
                Spacer(
                    modifier = Modifier
                        .height(spacerHeight.dp)
                        .fillMaxWidth()
                )
                FoodTextField(
                    modifier = Modifier
                        .width(textFieldWidth.dp)
                        .fillMaxWidth(),
                    mutableStateValue = foodText,
                    onValueChange = { foodText.value = it },
                    label = { Text(stringResource(R.string.diary_diet_food_label)) },
                )
                Spacer(
                    modifier = Modifier
                        .height(spacerHeight.dp)
                        .fillMaxWidth()
                )
                DateTextField(
                    modifier = Modifier.width(textFieldWidth.dp),
                    mutableStateValue = dateText,
                    onValueChange = { dateText.value = it },
                    label = { Text(stringResource(R.string.diary_diet_date_label)) },
                )
                Spacer(
                    modifier = Modifier
                        .height(spacerHeight.dp)
                        .fillMaxWidth()
                )
                TimeTextField(
                    modifier = Modifier
                        .width(textFieldWidth.dp)
                        .fillMaxWidth(),
                    mutableStateValue = timeText,
                    onValueChange = { timeText.value = it },
                    label = { Text(stringResource(R.string.diary_diet_time_label)) },
                )
            }
        }
    )
}