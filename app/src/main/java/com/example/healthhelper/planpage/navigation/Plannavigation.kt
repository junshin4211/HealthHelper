package com.example.healthhelper.planpage.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.planpage.ui.AddPlan
import com.example.healthhelper.planpage.ui.ManagePlan
import com.example.healthhelper.planpage.ui.PlanDetail
import com.example.healthhelper.planpage.ui.PlanMain
import com.example.healthhelper.screen.TabViewModel


// Plan page navigation route
sealed class Screen(val route: String) {
    object PlanMain : Screen("plan_main")
    object AddPlan : Screen("add_plan")
    object ManagePlan : Screen("manage_plan")
    object PlanDetail : Screen("plan_detail")
}

@Composable
fun PlanNav(
    navController: NavHostController = rememberNavController(),
) {
    val tag = "tag_PlanNav"

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
        composable(route = Screen.AddPlan.route) {
            AddPlan(
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