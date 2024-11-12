package com.example.healthhelper.community

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.signuplogin.UserManager

@Composable
fun CmtController(
    navController: NavHostController = rememberNavController(),
    commentVM: CommentVM = viewModel(),
    postVM: PostVM = viewModel(),
) {
    val userId = UserManager.getUser()?.userId // 取得目前登入使用者的 ID
    NavHost(
        navController = navController,
        startDestination = CmtScreenEnum.CmtMainScreen.name
    ) {
        composable(
            route = CmtScreenEnum.CmtMainScreen.name
        ) {
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
            if (userId != null) {
                LaunchedEffect(Unit) {
                    postVM.fetchUserPosts(userId)
                }
            }
            MyPostsScreen(navController = navController, postVM = postVM)
        }
        composable(
            route = "${CmtScreenEnum.PersonalPostScreen.name}/{postId}"
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")
            PersonalPostScreen(navController, postVM = postVM, commentVM = commentVM, postId = postId)
        }

    }
}

