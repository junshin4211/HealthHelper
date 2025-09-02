package com.example.healthhelper.planpage.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.healthhelper.R
import com.example.healthhelper.planpage.ui.AddCustomPlan
import com.example.healthhelper.planpage.ui.AddPlan
import com.example.healthhelper.planpage.ui.ManagePlan
import com.example.healthhelper.planpage.ui.PlanDetail
import com.example.healthhelper.planpage.ui.PlanMain
import com.example.healthhelper.screen.TabViewModel


// Plan page navigation route
sealed class Screen(val route: String) {
    object PlanMain : Screen("plan_main")
    object AddPlan : Screen("add_plan/{categoryId}") {
        fun createRoute(categoryId: Int): String {
            return "add_plan/$categoryId"
        }
    }
    object AddCustomPlan : Screen("add_custom_plan/{categoryId}") {
        fun createRoute(categoryId: Int): String {
            return "add_custom_plan/$categoryId"
        }
    }
    object ManagePlan : Screen("manage_plan/{planType}") {

        fun createRoute(planType: Int): String {
            return "manage_plan/$planType"
        }
    }
    object PlanDetail : Screen("plan_detail/{userDietPlanId}/{categoryName}") {
        fun createRoute(userDietPlanId: Int, categoryName: String): String {
            return "plan_detail/$userDietPlanId/$categoryName"
        }
    }
}

@Composable
fun PlanNav(
    navController: NavHostController = rememberNavController(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    "tag_PlanNav"
    val backStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.PlanMain.route
    ) {
        //to the plan main page
        composable(route = Screen.PlanMain.route) {
            PlanMain(
                navController = navController,
            )
        }

        //to the add plan page
        composable(
            route = Screen.AddPlan.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
            ) {
            val categoryId = backStackEntry?.arguments?.getInt("categoryId") ?: R.string.add_plan_default_title

            AddPlan(
                navController = navController,
                title = categoryId,
            )
        }

        //to the add custom plan page
        composable(
            route = Screen.AddCustomPlan.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
            ) {
            val categoryId = backStackEntry?.arguments?.getInt("categoryId") ?: R.string.add_plan_default_title

            AddCustomPlan(
                navController = navController,
                title = categoryId,
            )
        }

        //to the manage plan page
        composable(
            route = Screen.ManagePlan.route,
            arguments = listOf(navArgument("planType") { type = NavType.IntType })
        ) {
            val planType = backStackEntry?.arguments?.getInt("planType") ?: R.string.no_plantext

            ManagePlan(
                navController = navController,
                planType = planType,
            )
        }

        //to the plan detail page
        composable(
            route = Screen.PlanDetail.route,
            arguments = listOf(
                navArgument("userDietPlanId") { type = NavType.IntType },
                navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val planId = backStackEntry.arguments?.getInt("userDietPlanId")
            val categoryName = backStackEntry.arguments?.getString("categoryName")
            if (planId != null && categoryName != null && categoryName.isNotEmpty()) {
                // 確保 planId 和 categoryName 不為 null
                PlanDetail(
                    userDietPlanId = planId,
                    categoryName = categoryName,
                    navController = navController
                )
            } else {
                // 處理 planId 為 null 的情況，例如導航回上一頁或顯示錯誤
            }
        }
    }
}