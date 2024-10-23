package com.example.healthhelper.dietary.frame

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.dietary.components.card.DietDiaryCards
import com.example.healthhelper.dietary.components.textfield.outlinedtextfield.SearchTextField
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum
import com.example.healthhelper.dietary.gson.toJson
import com.example.healthhelper.dietary.util.file.savingfile.saveEternal
import com.example.healthhelper.dietary.viewmodel.DiaryViewModel
import com.example.healthhelper.screen.Main
import com.example.healthhelper.ui.theme.HealthHelperTheme
import java.io.File
import kotlin.io.path.Path

const val TAG = "DietDiaryMainFrame"

@SuppressLint("MutableCollectionMutableState")
@Composable
fun DietDiaryMainFrame(
    navController: NavHostController = rememberNavController(),
) {
    val currentContext = LocalContext.current
    val toastMessage by remember { mutableStateOf("") }
    var savingFileFlag by remember { mutableStateOf(false) }
    val verticalScrollState = rememberScrollState()

    val diaries by remember { mutableStateOf(DiaryViewModel.diaries) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Main()
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .border(2.dp, Color.Magenta),
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.9f)
                        .verticalScroll(verticalScrollState)
                        .border(2.dp, Color.Blue),
                ) {
                    SearchTextField(
                        navController = navController,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(stringResource(R.string.search_label)) },
                    )

                    DietDiaryCards(
                        context = currentContext,
                        cards = diaries,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(0.1f)
                        .fillMaxWidth()
                        .border(2.dp, Color.Green)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxHeight().border(4.dp, Color.Red)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(0.5f)
                        ) {

                        }
                        Column(
                            modifier = Modifier
                                .weight(0.5f)
                                .border(8.dp, Color.Yellow),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .border(10.dp, Color.Cyan),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(1f)
                                        .border(12.dp, Color.Black),
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
                                        .weight(1f)
                                        .border(12.dp, Color.Black),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    IconButton(
                                        colors = IconButtonColors(
                                            containerColor = Color.Blue,
                                            contentColor = Color.Blue,
                                            disabledContainerColor = Color.Gray,
                                            disabledContentColor = Color.Gray,
                                        ), onClick = {
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
    if (toastMessage.isNotBlank()) {
        Toast.makeText(currentContext, toastMessage, Toast.LENGTH_LONG).show()
    } else if (savingFileFlag) {
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

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Preview(showBackground = true)
@Composable
fun aPreview() {
    HealthHelperTheme {
        DietDiaryMainFrame()
    }
}