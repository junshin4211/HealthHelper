package com.example.healthhelper.dietary.frame

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.attr.color.defaultcolor.DefaultColorViewModel
import com.example.healthhelper.dietary.components.bar.appbar.topappbar.DietAppTopBar
import com.example.healthhelper.dietary.components.button.DeleteButton
import com.example.healthhelper.dietary.components.button.DownloadButton
import com.example.healthhelper.dietary.components.iconbutton.AddIcon
import com.example.healthhelper.dietary.components.textfield.outlinedtextfield.SearchTextFieldWithDropDownMenuItem
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum
import com.example.healthhelper.dietary.repository.SelectedFoodItemRepository
import com.example.healthhelper.dietary.repository.SelectedFoodItemsRepository
import com.example.healthhelper.dietary.util.downloaddata.DownloadData
import com.example.healthhelper.dietary.viewmodel.SelectedFoodItemsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun DietDiaryMealFrame(
    navController: NavHostController = rememberNavController(),
    selectedFoodItemsViewModel: SelectedFoodItemsViewModel = viewModel(),
    title: @Composable () -> Unit,
) {
    val TAG = "tag_DietDiaryMealFrame"

    val context = LocalContext.current
    val verticalScrollState = rememberScrollState()

    val foodItems by selectedFoodItemsViewModel.data.collectAsState()
    val selectedFoodItems = remember { mutableStateOf(foodItems) }

    var hasFound by remember { mutableStateOf(false) }

    var deleteButtonIsClicked by remember { mutableStateOf(false) }
    var addIconButtonIsClicked by remember { mutableStateOf(false) }
    var downloadButtonIsClicked by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        DietAppTopBar(
            navController = navController, title = title
        )
    }, floatingActionButton = {
        Row() {
            DownloadButton(
                context = context,
                onClick = { downloadButtonIsClicked = true}
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
        ) {
            Column(
                modifier = Modifier
                    .weight(0.95f)
                    .verticalScroll(verticalScrollState)
            ) {
                SearchTextFieldWithDropDownMenuItem(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    label = { Text(stringResource(R.string.search_label)) },
                )

                hasFound = (selectedFoodItems.value.isNotEmpty() && selectedFoodItems.value.filter { it.isCheckedWhenSelection.value }.any{ it.isCheckedWhenSelection.value })
                if (hasFound) {
                    selectedFoodItems.value.filter { it.isCheckedWhenSelection.value }.forEach { foodItem ->
                        Box(
                            modifier = Modifier
                                .requiredWidth(width = 360.dp)
                                .requiredHeight(height = 41.dp),
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.requiredWidth(width = 360.dp),
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(
                                        16.dp, Alignment.CenterHorizontally
                                    ),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .requiredHeight(height = 40.dp)
                                        .padding(horizontal = 16.dp)
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .weight(weight = 1f),
                                    ) {
                                        Row() {
                                            Checkbox(
                                                checked = foodItem.isCheckingWhenSelection.value,
                                                onCheckedChange = {
                                                    SelectedFoodItemsRepository.setCheckingWhenSelectionState(foodItem, it)
                                                })

                                            Text(
                                                text = foodItem.name,
                                                color = colorResource(R.color.primarycolor),
                                                lineHeight = 1.27.em,
                                                style = MaterialTheme.typography.titleLarge,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .wrapContentHeight(align = Alignment.CenterVertically)
                                            )
                                        }
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(
                                            10.dp, Alignment.Start
                                        )
                                    ) {
                                        IconButton(onClick = {
                                            SelectedFoodItemRepository.setData(foodItem)
                                            navController.navigate(DietDiaryScreenEnum.FoodItemInfoFrame.name)
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
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = stringResource(R.string.result_not_found))
                    }
                }
            }
        }
    })
    if (deleteButtonIsClicked) {
        val selectedFoodItemVOs by remember { mutableStateOf(selectedFoodItems.value.filter { it.isCheckingWhenSelection.value }) }
        SelectedFoodItemsRepository.setAllCheckedWhenQueryState(false)
        if (selectedFoodItemVOs.isNotEmpty()) {
            selectedFoodItemVOs.forEach{ selectedFoodItemVo ->
                SelectedFoodItemsRepository.setCheckedWhenSelectionState(selectedFoodItemVo,false)
            }
            Toast.makeText(context, stringResource(R.string.delete_data_successfully), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, stringResource(R.string.no_item_selected), Toast.LENGTH_LONG).show()
        }
        deleteButtonIsClicked = false
    } else if (addIconButtonIsClicked) {
        val selectedFoodItemVOs by remember { mutableStateOf(selectedFoodItems.value.filter { it.isCheckingWhenQuery.value }) }
        SelectedFoodItemsRepository.setAllCheckedWhenQueryState(false)
        if (selectedFoodItemVOs.isNotEmpty()) {
            Toast.makeText(context, stringResource(R.string.add_data_successfully), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, stringResource(R.string.no_item_selected), Toast.LENGTH_LONG).show()
        }
        addIconButtonIsClicked = false
    }else if(downloadButtonIsClicked){
        DownloadData(
            context = context,
            vo = foodItems
        )
        downloadButtonIsClicked = false
    }
}