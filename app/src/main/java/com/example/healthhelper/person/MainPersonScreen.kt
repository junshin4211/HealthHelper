package com.example.healthhelper.person

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


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
        composable(route = PersonScreenEnum.weightScreen.name) {
            WeightScreen()
        }
        composable(route = PersonScreenEnum.achivementScreen.name) {
            AchievementScreen()
        }

    }
}