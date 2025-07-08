package com.example.healthhelper.planpage.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    object ManagePlan : Screen("manage_plan")
    object PlanDetail : Screen("plan_detail")
}

@Composable
fun PlanNav(
    navController: NavHostController = rememberNavController(),
) {
    val tag = "tag_PlanNav"
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
                title = categoryId
            )
        }

        //to the add custom plan page
        composable(route = Screen.AddCustomPlan.route) {
            AddCustomPlan(
                navController = navController
            )
        }

        //to the manage plan page
        composable(route = Screen.ManagePlan.route) {
            ManagePlan(
                navcontroller = navController,
            )
        }

        //to the plan detail page
        composable(route = Screen.PlanDetail.route) {
            PlanDetail(
                navcontroller = navController,
            )
        }
    }
}