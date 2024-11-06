package com.example.healthhelper.plan.screen

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.plan.PlanPage
import com.example.healthhelper.plan.viewmodel.ManagePlanVM
import com.example.healthhelper.plan.viewmodel.PlanVM
import com.example.healthhelper.screen.TabViewModel

@Composable
fun CheckPlan(
    navcontroller: NavHostController = rememberNavController(),
    tabVM: TabViewModel = viewModel(),
    planVM: PlanVM,
    managePlanVM: ManagePlanVM,
    planName: PlanPage
){
    tabVM.setTabVisibility(false)



}