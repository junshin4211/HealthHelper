package com.example.healthhelper.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum
import com.example.healthhelper.dietary.frame.AddNewDietDiaryItemFrame
import com.example.healthhelper.dietary.frame.DietDiaryMainFrame
import com.example.healthhelper.dietary.frame.SearchHintFrame
import com.example.healthhelper.enumclass.ScreenEnum
import com.example.healthhelper.main.TabViewModel



@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    tabViewModel: TabViewModel = viewModel()
) {
    val TAG = "tag_MainScreen"

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenEnum.HomePage.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            composable(
                route = ScreenEnum.HomePage.name
            ){
                TabScreen(navController)
            }

            composable(
                route = ScreenEnum.DietDiaryMainFrame.name,
            ) {
                DietDiaryMainFrame(navController)
            }

            composable(
                route = DietDiaryScreenEnum.DietDiaryMainFrame.name
            ) {
                DietDiaryMainFrame(
                    navController = navController
                )
            }
            composable(
                route = DietDiaryScreenEnum.AddNewDietDiaryItemFrame.name
            ) {
                AddNewDietDiaryItemFrame(
                    navController = navController,
                )
            }

            composable(
                route = DietDiaryScreenEnum.SearchHintFrame.name
            ) {
                SearchHintFrame(
                    navController = navController,
                )
            }
        }
    }
}