package com.example.healthhelper.plan

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.plan.ui.CreateDatePicker
import com.example.healthhelper.plan.ui.CreateDropDownMenu
import com.example.healthhelper.ui.theme.HealthHelperTheme

@Composable
fun EditPlan(planname:String,
             navcontroller: NavHostController = rememberNavController(),
             context: Context = LocalContext.current
){
    var selectedDate by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.backgroundcolor)),
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp, start = 10.dp, bottom = 10.dp)
                .clickable { navcontroller.popBackStack() }
        ) {
            Image(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "arrow",
                modifier = Modifier
                    .scale(2.2f)
                    .graphicsLayer(scaleX = -1f),
                colorFilter = ColorFilter.tint(colorResource(R.color.primarycolor))
            )

            Text(
                text = "${planname}飲食計畫",
                style = TextStyle(
                    fontSize = 26.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = colorResource(id = R.color.black),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.2.sp
                )
            )
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 13.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ){
            Text(
                text = stringResource(R.string.setplantime),
                style = TextStyle(
                    fontSize = 26.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = colorResource(id = R.color.primarycolor),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.2.sp
                )
            )

            CreateDropDownMenu(
                options = DateRange.entries,
                selectedOption = null,
                onOptionSelected = {  selectedDate = it.toString()},
                getDisplayText = { context.getString(it.title) }
            )

            Text(
                text = stringResource(R.string.startdate),
                style = TextStyle(
                    fontSize = 22.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = colorResource(id = R.color.black),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.2.sp
                )
            )

            CreateDatePicker(dateSelected = selectedDate)
        }
    }

}

@Preview(locale = "zh-rTW")
@Composable
fun EditPlanPreview() {
    HealthHelperTheme {
        EditPlan("")
    }
}