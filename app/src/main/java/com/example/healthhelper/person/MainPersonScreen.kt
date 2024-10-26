package com.example.healthhelper.person

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainPersonScreen(
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    NavHost(
        navController = navController,
        startDestination = PersonScreenEnum.personScreen.name,
    ) {
        composable(route = PersonScreenEnum.personScreen.name) {
            PersonScreen(navController= navController)
        }
        composable(route = PersonScreenEnum.pickPhotoScreen.name) {
            PickPhotoScreen(navController)
        }
        composable(route = PersonScreenEnum.weightScreen.name) {
            WeightScreen(navController)
        }
        composable(route = PersonScreenEnum.weightSettingScreen.name) {
            WeightSettingScreen(navController)
        }
        composable(route = PersonScreenEnum.weightReviseScreen.name) {
            WeightReviseScreen(navController)
        }
        composable(route = PersonScreenEnum.achivementScreen.name) {
            AchievementScreen()
        }

    }
}