package com.example.healthhelper.plan.screen

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.R
import com.example.healthhelper.plan.DateRange
import com.example.healthhelper.plan.model.PlanModel
import com.example.healthhelper.plan.ui.CreateDropDownMenu
import com.example.healthhelper.plan.ui.CreateToggleButton
import com.example.healthhelper.plan.ui.CustomText
import com.example.healthhelper.plan.usecase.PlanUCImpl
import com.example.healthhelper.plan.viewmodel.CheckPlanVM
import com.example.healthhelper.plan.viewmodel.ManagePlanVM
import com.example.healthhelper.plan.viewmodel.PlanVM
import com.example.healthhelper.screen.TabViewModel
import com.example.healthhelper.plan.ui.CreateAnimationBar
import com.example.healthhelper.plan.ui.CreatePieChart
import com.himanshoe.charty.common.toChartDataCollection
import com.himanshoe.charty.pie.model.PieData

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CheckPlan(
    tabVM: TabViewModel = viewModel(),
    planVM: PlanVM,
    managePlanVM: ManagePlanVM,
    checkVM: CheckPlanVM
){
    tabVM.setTabVisibility(false)
    val formatter = PlanUCImpl()::dateTimeFormat;
    var showdatepick by remember { mutableStateOf(false) }
    var showwarning by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf("") }
    val context = LocalContext.current
    //get selected plan
    val selectedPlan by checkVM.selectedPlanState.collectAsState(initial = PlanModel())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.backgroundcolor)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        //時間範圍
        CustomText().TextWithDiffColor(
            text = "2024/12/15~2024/12/31",
            //text = "${formatter(selectedPlan.startDateTime)}~${formatter(selectedPlan.endDateTime)}"
            setsize = 18.sp
        )
        //TODO 達成度圖
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.myplanimg),
                contentDescription = "達成度圖",
                modifier = Modifier
            )
        }
        //達成度文字
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .padding(start = 30.dp),
        ){
            CustomText().TextWithDiffColor(
                text = stringResource(R.string.finishratecontent)
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .width(370.dp),
            color = colorResource(R.color.darkgray), thickness = 2.dp
        )

        //時間toglebutton
        //顯示日期選擇
        when(showdatepick)
        {
            true ->{
                CreateToggleButton(
                    onLeftClick = {showdatepick = false},
                    onRightClick = {},
                    leftButtonColor = R.color.light_gray,
                    rightButtonColor = R.color.primarycolor,
                    leftTextColor = R.color.darkgray,
                    rightTextColor = R.color.white,
                    leftText = stringResource(R.string.alltime),
                    rightText = stringResource(R.string.date),
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                        .padding(start = 38.dp),
                ){
                    CreateDropDownMenu(
                        options = DateRange.entries,
                        selectedOption = null,
                        onOptionSelected = { selectedDate = it.toString() },
                        getDisplayText = { context.getString(it.title) }
                    )
                }
            }
            false ->{
                CreateToggleButton(
                    onLeftClick = {},
                    onRightClick = {showdatepick = true},
                    leftText = stringResource(R.string.alltime),
                    rightText = stringResource(R.string.date)
                )
            }
        }

        //卡路里
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            //文字
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .padding(start = 38.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ){
                Icon(
                    painter = painterResource(id = R.drawable.calorie),
                    contentDescription = "卡路里",
                    tint = colorResource(id = R.color.black_300),
                    modifier = Modifier
                        .scale(1.5f)
                )

                CustomText().TextWithDiffColor(
                    text = stringResource(R.string.averagecalorie),
                    setsize = 24.sp
                )
            }
            //BAR
            CreateAnimationBar(1500f,1000f,352.dp,25.dp)
        }
        //三大項bar和pie chart
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(0.6f)
            ) {
                //碳水
                ShowNutrition(
                    nutritionName = R.string.carb,
                    nutritionTextColor = R.color.light_red,
                    showWarning = showwarning,
                    goal = 3500f,
                    currentValue = 2500f
                )

                //蛋白質
                ShowNutrition(
                    nutritionName = R.string.protein,
                    nutritionTextColor = R.color.sky_blue,
                    goal = 250f,
                    currentValue = 100f
                )

                //脂肪
                ShowNutrition(
                    nutritionName = R.string.fat,
                    nutritionTextColor = R.color.light_green,
                    goal = 150f,
                    currentValue = 50f
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(end = 15.dp)
            ) {
                val data = listOf(
                    PieData(12f, 12f, colorResource(R.color.light_red)),
                    PieData(38f, 38f, colorResource(R.color.sky_blue)),
                    PieData(50f, 50f, colorResource(R.color.light_green)),
                )
                CreatePieChart(
                    dataCollection = data.toChartDataCollection(),
                    modifier = Modifier.wrapContentSize()
                )
            }
        }
    }
}

@Composable
fun ShowNutrition(
    @StringRes nutritionName: Int,
    @ColorRes nutritionTextColor: Int,
    showWarning: Boolean = false,
    goal: Float,
    currentValue: Float,
    barHeight: Dp = 10.dp,
    barWidth: Dp = 124.dp
){
    var addjustOffset = (-20).dp
    if (!showWarning) addjustOffset = 0.dp
    Column (
        modifier = Modifier,
        horizontalAlignment = Alignment.Start
    ){
        CustomText().TextWithDiffColor(
            text = stringResource(nutritionName),
            setsize = 18.sp,
            setcolor = nutritionTextColor
        )
        //bar+icon
        Row (
            modifier = Modifier
                //.fillMaxWidth()
                .offset(addjustOffset),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ){
            if(showWarning) {
                Icon(
                    painter = painterResource(id = R.drawable.warnsign),
                    contentDescription = "warn",
                    tint = colorResource(id = R.color.red01),
                    modifier = Modifier
                        .scale(1.5f)
                )
            }

            CreateAnimationBar(goal,currentValue,barWidth,barHeight)
        }
        //文字敘述
        Row(
            modifier = Modifier
                .width(124.dp),
        ){
            CustomText().TextWithDiffColor(
                text = "${goal}${stringResource(R.string.grams)}",
                setsize = 10.sp,
            )
            Row (
                modifier = Modifier
                    .weight(1.0f)
                    .padding(start = 10.dp),
                horizontalArrangement = Arrangement.End
            ){
                if(currentValue > goal)
                {
                    val count = currentValue - goal
                    CustomText().TextWithDiffColor(
                        text = stringResource(R.string.over) +
                                "$count" +
                                stringResource(R.string.grams),
                        setsize = 10.sp,
                        maxline = 1
                    )
                }else{
                    val count = goal - currentValue
                    CustomText().TextWithDiffColor(
                        text = stringResource(R.string.under) +
                                "$count" +
                                stringResource(R.string.grams),
                        setsize = 10.sp,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun checkPlanPreview(){
    CheckPlan(planVM = viewModel(), managePlanVM = viewModel(), checkVM = viewModel())
}