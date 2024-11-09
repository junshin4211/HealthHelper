package com.example.healthhelper.community

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
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
            val postVM: PostVM = viewModel()
            CmtMainScreen(navController = navController, postVM = postVM)
        }
        composable(
            route = CmtScreenEnum.CreatePostScreen.name
        ) {
            CreatePostScreen(navController = navController)
        }
        composable(
            route = CmtScreenEnum.EditPostScreen.name
        ) {
            EditPostScreen(navController)
        }
        composable(
            route = CmtScreenEnum.MyPostsScreen.name
        ) {
            MyPostsScreen(navController)
        }
        composable(
                route = CmtScreenEnum.PersonalPostScreen.name
                ) {
            PersonalPostScreen(navController)
        }

    }
}

