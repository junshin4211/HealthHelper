package com.example.healthhelper.dietary.frame

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.dietary.components.bar.appbar.topappbar.DietAppTopBar
import com.example.healthhelper.dietary.components.card.DietDiaryCards
import com.example.healthhelper.dietary.components.textfield.outlinedtextfield.SearchTextField
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum
import com.example.healthhelper.dietary.viewmodel.DiaryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun DietDiaryMealFrame(
    navController: NavHostController = rememberNavController(),
    vm : DiaryViewModel = viewModel(),
    title: @Composable () -> Unit,
) {
    val TAG = "tag_DietDiaryMealFrame"

    val currentContext = LocalContext.current

    val verticalScrollState = rememberScrollState()

    val diaries by vm.data.collectAsState()

    Log.d(TAG,"Ready to add DietAppTopBar,navController:$navController,title:$title.")


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DietAppTopBar(
                navController = navController,
                title = title
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.95f)
                        .verticalScroll(verticalScrollState)
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
                                            Log.d(TAG,"add button was clicked.")
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
}