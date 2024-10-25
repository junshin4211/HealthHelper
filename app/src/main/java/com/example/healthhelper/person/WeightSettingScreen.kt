package com.example.healthhelper.person

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.person.widget.CustomTopBar
import com.example.healthhelper.person.widget.MeasurementRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightSettingScreen(navController: NavHostController) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(R.color.backgroundcolor)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TODO: 可以選擇日期
            Text("顯示日期", fontSize = 24.sp)
            Box(
                modifier = Modifier
                    .height(270.dp)
                    .width(412.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    MeasurementRow(
                        stringResource(R.string.height),
                        height,
                        stringResource(R.string.centermeter)
                    ) { height = it }
                    Spacer(modifier = Modifier.height(8.dp))
                    MeasurementRow(
                        stringResource(R.string.weight),
                        weight,
                        stringResource(R.string.kilogram)
                    ) { weight = it }
                    Spacer(modifier = Modifier.height(8.dp))
                    MeasurementRow(
                        stringResource(R.string.fat),
                        fat,
                        stringResource(R.string.percentage)
                    ) { fat = it }
                }
            }
            Button(
                onClick = { /* Handle save */ },
                modifier = Modifier
                    .width(100.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primarycolor)),
            ) {
                Text(
                    stringResource(R.string.save),
                    color = Color.White,
                    fontSize = 24.sp
                )
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun WeightSettingPreview() {
    WeightSettingScreen(rememberNavController())
}