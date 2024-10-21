package com.example.healthhelper.plan

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Plan(){
    val navController :NavHostController= rememberNavController()
    NavHost(
        navController = navController,
        startDestination = PlanPage.DietPlan.name,
    ) {
        composable(route = PlanPage.DietPlan.name) {
            PlanMain(navcontroller = navController)
        }
        composable(route = Bar.HighProtein.name) {
            EditPlan(Bar.HighProtein.name)
        }
        composable(route = Bar.LowCarb.name) {
            //EditPlan()
        }
        composable(route = Bar.Ketone.name) {
            //EditPlan()
        }
        composable(route = Bar.Mediterra.name) {
            //EditPlan()
        }
        composable(route = Bar.Custom.name) {
            //CustomEdit()
        }
//        composable(route = PlanPage.CheckEdit.name) {
//            CheckEdit()
//        }
    }

}