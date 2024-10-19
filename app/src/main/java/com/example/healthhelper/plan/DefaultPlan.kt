package com.example.healthhelper.plan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthhelper.ui.theme.HealthHelperTheme

@Composable
fun EditPlan(planname:String){
    var input by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        Text(text = "XXX計畫")
        TextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("insert") }
        )
    }

}

@Preview(locale = "zh-rTW")
@Composable
fun EditPlanPreview() {
    HealthHelperTheme {
        EditPlan("")
    }
}