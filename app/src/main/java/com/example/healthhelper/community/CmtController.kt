package com.example.healthhelper.community

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun CmtController(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = CmtScreenEnum.CmtMainScreen.name
    ) {
        composable(
            route = CmtScreenEnum.CmtMainScreen.name
        ) {
            CmtMainScreen(navController = navController)
        }
        composable(
            route = CmtScreenEnum.CreatePostScreen.name
        ) {
            CreatePostScreen()
        }
    }
}

