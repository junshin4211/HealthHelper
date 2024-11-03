package com.example.healthhelper.dietary.screen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum
import com.example.healthhelper.dietary.frame.AddNewDietDiaryItemFrame
import com.example.healthhelper.dietary.frame.DietDiaryMainFrame
import com.example.healthhelper.dietary.frame.DietDiaryMealFrame
import com.example.healthhelper.dietary.frame.FoodItemInfoFrame
import com.example.healthhelper.dietary.frame.SearchHintFrame
import com.example.healthhelper.dietary.viewmodel.MealsOptionViewModel

@Composable
fun DietDiaryMainScreen(
    navController: NavHostController = rememberNavController(),
    mealsOptionViewModel: MealsOptionViewModel = viewModel(),
) {
    val TAG = "tag_DietDiaryMainScreen"

    val selectedMealOption by mealsOptionViewModel.selectedData.collectAsState()

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
                route = "${DietDiaryScreenEnum.DietDiaryMealFrame.name}/{title}"
            ) { backStackEntry ->
                DietDiaryMealFrame(
                    navController = navController,
                    title = backStackEntry.arguments?.getString("title") ?: ""
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
                route = "${DietDiaryScreenEnum.FoodItemInfoFrame.name}/{title}/{mealOptionName}"
            ) { backStackEntry ->
                val mealOptionName = backStackEntry.arguments?.getString("mealOptionName") ?: ""

                Log.e(TAG,"mealOptionName:${mealOptionName}")

                FoodItemInfoFrame(
                    navController = navController,
                    title = backStackEntry.arguments?.getString("title") ?: "",
                    mealOptionName = mealOptionName,
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

@Preview(showBackground = true)
@Composable
fun DietDiaryMainScreenPreview() {
    DietDiaryMainScreen()
}