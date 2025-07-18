package com.example.healthhelper.planpage.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun ManagePlan(
    navcontroller: NavHostController = rememberNavController(),
){
    "tag_ManagePlan"
    Text("計劃管理葉面")
}