package com.example.healthhelper.dietary.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum
import com.example.healthhelper.dietary.frame.AddNewDietDiaryItemFrame
import com.example.healthhelper.dietary.frame.DietDiaryMainFrame
import com.example.healthhelper.dietary.frame.DietDiaryMealFrame
import com.example.healthhelper.dietary.frame.SearchHintFrame

@Composable
fun DietDiaryMainScreen(
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DietDiaryScreenEnum.DietDiaryMainFrame.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            composable(
                route = DietDiaryScreenEnum.DietDiaryMainFrame.name
            ) {
                DietDiaryMainFrame(
                    navController = navController
                )
            }

            composable(
                route = "${DietDiaryScreenEnum.DietDiaryMealFrame.name}/{selectedMealsOption}"
            ) { backStackEntry ->
                val selectedMealOption =
                    backStackEntry.arguments?.getString("selectedMealsOption") ?: ""
                DietDiaryMealFrame(
                    navController = navController,
                    title = {
                        Text(
                            text = selectedMealOption,
                            maxLines = 1,
                        )
                    }
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