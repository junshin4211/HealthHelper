package com.example.healthhelper.planpage.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun PlanDetail(
    navcontroller: NavHostController = rememberNavController(),
){
    Text("計畫詳細葉面")
}