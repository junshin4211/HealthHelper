package com.example.healthhelper.plan.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.plan.Bar
import com.example.healthhelper.plan.PlanPage
import com.example.healthhelper.screen.TabViewModel


@Composable
fun Plan(
    navController: NavHostController = rememberNavController(),
    tabViewModel: TabViewModel = viewModel()
) {
    val context = LocalContext.current
    val backStackEntry by navController.currentBackStackEntryAsState()
//    val currentScreen = PlanPage.valueOf(
//        backStackEntry?.destination?.route ?: PlanPage.DietPlan.name
//    )
    tabViewModel.setTabVisibility(true)
    Scaffold(
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = PlanPage.DietPlan.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = PlanPage.DietPlan.name) {
                PlanMain(navcontroller = navController,
                    tabViewModel = tabViewModel
                )
            }
            composable(route = Bar.HighProtein.name) {
                EditPlan(
                    Bar.HighProtein.getbar(context),
                    navcontroller = navController,
                    tabViewModel
                )
            }
            composable(route = Bar.LowCarb.name) {
                EditPlan(Bar.LowCarb.getbar(context),
                    navcontroller = navController,
                    tabViewModel
                )
            }
            composable(route = Bar.Ketone.name) {
                EditPlan(Bar.Ketone.getbar(context),
                    navcontroller = navController,
                    tabViewModel
                )
            }
            composable(route = Bar.Mediterra.name) {
                EditPlan(Bar.Mediterra.getbar(context),
                    navcontroller = navController,
                    tabViewModel
                )
            }
            composable(route = Bar.Custom.name) {
                CustomEditPlan(
                    Bar.Custom.getbar(context),
                    navcontroller = navController,
                    tabViewModel
                )
            }
            composable(route = "${PlanPage.ManagePlan.name}/{planpannel}") {backStackEntry ->
                ManagePlan(
                    navcontroller = navController,
                    planpannel = backStackEntry.arguments?.getString("planpannel") ?: ""
                )
            }
        }
    }
}

