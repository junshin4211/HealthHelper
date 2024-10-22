package com.example.healthhelper.dietary.frame

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.dietary.components.bar.appbar.topappbar.QueryTopAppBar
import com.example.healthhelper.dietary.components.button.DateButton
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum
import com.example.healthhelper.dietary.gson.toJson
import com.example.healthhelper.dietary.util.file.savingfile.saveEternal
import com.example.healthhelper.dietary.viewmodel.DiaryViewModel
import com.example.healthhelper.dietary.viewmodel.MealsOptionViewModel
import java.io.File
import java.util.Date
import kotlin.io.path.Path

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun DietDiaryMainFrame(
    navController: NavHostController,
    diaryViewModel: DiaryViewModel = viewModel(),
    mealsOptionViewModel: MealsOptionViewModel = viewModel(),
) {
    val TAG = "tag_DietDiaryMainFrame"

    val selectedDate = remember { mutableStateOf((Date(System.currentTimeMillis()).toString())) }
    var savingFileFlag by remember { mutableStateOf(false) }
    val selectedMealOptionIndex = remember { mutableIntStateOf(0) }

    val verticalScrollState = rememberScrollState()

    val diaries by diaryViewModel.data.collectAsState()
    val mealsOptions by mealsOptionViewModel.data.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            QueryTopAppBar(
                navController = navController,
                title = { Text(stringResource(R.string.diet_diary_main_frame_title)) },
                selectedMealsOptionIndex = selectedMealOptionIndex,
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
                    DateButton(
                        selectedDate
                    )

                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                            .fillMaxWidth()
                    )

                    mealsOptions.forEachIndexed { index, mealsOption ->
                        val textColor = if (index == selectedMealOptionIndex.intValue) {
                            Color.White
                        } else {
                            Color.Gray
                        }
                        Button(
                            onClick = {
                                Log.d(TAG,"The ${index}th button was clicked.")
                                selectedMealOptionIndex.intValue = index
                                Log.d(TAG,"selectedMealOptionIndex.intValue:${selectedMealOptionIndex.intValue}")
                            }
                        ) {
                            Text(
                                text = mealsOption,
                                color = textColor
                            )
                        }
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
                                    IconButton(
                                        colors = IconButtonColors(
                                            containerColor = Color.Blue,
                                            contentColor = Color.Blue,
                                            disabledContainerColor = Color.Gray,
                                            disabledContentColor = Color.Gray,
                                        ), onClick = {

                                        }) {
                                        Image(
                                            painter = painterResource(R.drawable.download),
                                            contentDescription = stringResource(R.string.download_icon),
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(0.5f),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    IconButton(
                                        colors = IconButtonColors(
                                            containerColor = Color.Blue,
                                            contentColor = Color.Blue,
                                            disabledContainerColor = Color.Gray,
                                            disabledContentColor = Color.Gray,
                                        ), onClick = {
                                            Log.d(TAG, "add button was clicked.")
                                            navController.navigate(DietDiaryScreenEnum.AddNewDietDiaryItemFrame.name)
                                        }) {
                                        Image(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = stringResource(R.string.add_new_item_icon),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )

    Log.d(TAG, "savingFileFlag:$savingFileFlag")

    if (savingFileFlag) {
        val fileName = "download.txt"
        val jsonString = diaries.toJson()
        val folder = File(
            LocalContext.current.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            fileName,
        )
        val fullFilePath = Path(folder.toPath().toString(), fileName)
        val fullFile = fullFilePath.toFile()

        val saveSuccessfully = saveEternal(fullFile, jsonString)

        val currentActivity = LocalContext.current as Activity
        val toastMessage =
            if (saveSuccessfully) "Data saved successfully." else "Data saved failed."

        Toast.makeText(currentActivity, toastMessage, Toast.LENGTH_LONG).show()

        savingFileFlag = false
    }
}