package com.example.healthhelper.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.R
import com.example.healthhelper.plan.PlanMain
import com.example.healthhelper.ui.theme.HealthHelperTheme

@Composable
fun Main(tabViewModel: TabViewModel = viewModel()){
    val tabVisibility = tabViewModel.tabVisibility.collectAsState()
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        stringResource(id = R.string.management),
        stringResource(id = R.string.diary),
        stringResource(id = R.string.community),
        stringResource(id = R.string.plan),
        stringResource(id = R.string.map)
    )

    Column (modifier = Modifier.fillMaxSize()){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ){
            when (tabIndex) {
//                0 -> Management()
//                1 -> Diary()
//                2 -> Community()
                3 -> PlanMain()
//                4 -> Map()
            }
        }

        if(tabVisibility.value){
            TabRow(
                selectedTabIndex = tabIndex,
                containerColor = colorResource(id = R.color.blue),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ){
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        selectedContentColor = colorResource(id = R.color.white),
                        icon = {
                            when(index){
                                0 -> Icon(
                                    painter = painterResource(R.drawable.baseline_people_24),
                                    contentDescription = title
                                )
                                1 -> Icon(
                                    painter = painterResource(R.drawable.baseline_menu_book_24),
                                    contentDescription = title
                                )
                                2 -> Icon(
                                    painter = painterResource(R.drawable.baseline_menu_book_24),
                                    contentDescription = title
                                )
                                3 -> Icon(
                                    painter = painterResource(R.drawable.baseline_people_24),
                                    contentDescription = title
                                )
                                4 -> Icon(
                                    painter = painterResource(R.drawable.baseline_menu_book_24),
                                    contentDescription = title
                                )

                            }
                        }
                    )
                }

            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HealthHelperTheme {
        Main()
    }
}