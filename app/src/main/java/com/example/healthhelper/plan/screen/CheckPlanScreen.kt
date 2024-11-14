package com.example.healthhelper.plan.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.R
import com.example.healthhelper.plan.NutritionType
import com.example.healthhelper.plan.model.PlanModel
import com.example.healthhelper.plan.model.PlanSpecificModel
import com.example.healthhelper.plan.ui.AnimatedGaugeChart
import com.example.healthhelper.plan.ui.CreateAnimationBar
import com.example.healthhelper.plan.ui.CreateDropDownMenu
import com.example.healthhelper.plan.ui.CreatePieChart
import com.example.healthhelper.plan.ui.CreateToggleButton
import com.example.healthhelper.plan.ui.CustomText
import com.example.healthhelper.plan.ui.GaugeChart
import com.example.healthhelper.plan.usecase.PlanUCImpl
import com.example.healthhelper.plan.viewmodel.CheckPlanVM
import com.example.healthhelper.plan.viewmodel.ManagePlanVM
import com.example.healthhelper.plan.viewmodel.PlanVM
import com.example.healthhelper.screen.TabViewModel
import com.himanshoe.charty.common.toChartDataCollection
import com.himanshoe.charty.pie.model.PieData
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.TimeZone

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CheckPlan(
    tabVM: TabViewModel = viewModel(),
    planVM: PlanVM,
    managePlanVM: ManagePlanVM,
    checkVM: CheckPlanVM,
) {
    tabVM.setTabVisibility(false)
    val planUCImpl = PlanUCImpl()
    val formatter = PlanUCImpl()::dateTimeFormat;
    var showdatepick by remember { mutableStateOf(false) }
    var showfinish by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(-1) }
    var isdiary by remember { mutableStateOf(false) }
    var finishpercent by remember { mutableFloatStateOf(0f) }
    val scrollstate = rememberScrollState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val tag = "tag_CheckPlan"

    //get selected plan
    val selectedPlan by checkVM.selectedPlanState.collectAsState()
    val planSpecific by checkVM.planSpecificState.collectAsState()
    val diaryRangeList by checkVM.diaryRangeListState.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        runCatching {
        }.onFailure {
            Log.d(tag, "CheckPlan: ${it.message}")
        }
    }
    Log.d(tag, "selectedPlan: ${selectedPlan.userDietPlanId}")
    checkVM.getSpecificPlan(selectedPlan.userDietPlanId)
    if(planSpecific.startDateTime!= null && planSpecific.endDateTime != null) {
        Log.d(tag, "finishState: ${planSpecific.finishState}")
        checkVM.getDiaryList()
    }

    val size = diaryRangeList.size
    Log.d(tag, "size: $size")
    val totalNutrition = planUCImpl.calculateTotalNutrition(diaryRangeList)

    if (planSpecific.userDietPlanId!= null&&diaryRangeList.isNotEmpty()) {
        isdiary = true
        Log.d(tag,"your userDietPlanId: ${planSpecific.userDietPlanId}")
        var carbgram = 0.0f
        var proteingram = 0.0f
        var fatgram = 0.0f
        planUCImpl.percentToGram("carb",planSpecific.Caloriesgoal,planSpecific.carbongoal, onSetGram = {carbgram = it})
        planUCImpl.percentToGram("protein",planSpecific.Caloriesgoal,planSpecific.proteingoal, onSetGram = {proteingram = it})
        planUCImpl.percentToGram("fat",planSpecific.Caloriesgoal,planSpecific.fatgoal, onSetGram = {fatgram = it})
        when (planSpecific.finishState) {
            0 -> {
                var count = 0
                diaryRangeList.forEach { diary ->
                    if (diary.totalCarbon >= carbgram
                        && diary.totalProtein >= proteingram
                        && diary.totalFat >= fatgram
                    ) {
                        count++
                    }
                }
                finishpercent = (count.toFloat() / size) * 100f
                Log.d(tag, "finishState 0 : $finishpercent")
            }
            1 -> {
                var count = 0
                diaryRangeList.forEach { diary ->
                    if (diary.totalCarbon >= carbgram
                        || diary.totalProtein >= proteingram
                        || diary.totalFat >= fatgram
                    ) {
                        count++
                    }
                }
                finishpercent = (count.toFloat() / size)*100
                // 檢查條件
                if (finishpercent >= 60.0f) {
                    showfinish = true
                    scope.launch {
                        checkVM.updatePlan(planSpecific.userDietPlanId!!, 2)
                    }

                }
                Log.d(tag, "finishState 1 : $finishpercent")
            }
            2 -> {
                var days: Long = 0

                // 檢查 startTimestamp 和 endTimestamp 是否為 null
                if (planSpecific.startDateTime == null || planSpecific.endDateTime == null) {
                    days = 0
                } else {
                    // 提取毫秒數
                    val startMillis = planSpecific.startDateTime?.time
                    val endMillis = planSpecific.endDateTime?.time

                    // 設定台北時區
                    val timeZone = TimeZone.getTimeZone("Asia/Taipei")
                    val calendarStart = Calendar.getInstance(timeZone)
                    val calendarEnd = Calendar.getInstance(timeZone)

                    // 設定開始與結束時間
                    calendarStart.timeInMillis = startMillis ?: 0L
                    calendarEnd.timeInMillis = endMillis ?: 0L

                    // 計算區間天數
                    val diffInMillis = calendarEnd.timeInMillis - calendarStart.timeInMillis
                    days = (diffInMillis / (1000 * 60 * 60 * 24) + 1)
                }

                var count = 0
                diaryRangeList.forEach { diary ->
                    if (diary.totalCarbon >= carbgram
                        && diary.totalProtein >= proteingram
                        && diary.totalFat >= fatgram
                    ) {
                        count++
                    }
                }
                finishpercent = (count.toFloat() / size)*100
                // 檢查條件
                if (finishpercent >= 60.0f) {
                    showfinish = true
                }
                Log.d(tag, "finishState 2 : $finishpercent")
            }
        }
    }

    var carbgoal = 0.0f
    var proteingoal = 0.0f
    var fatgoal = 0.0f
    var totalthree by remember { mutableFloatStateOf(0f) }
    var averagecarb by remember { mutableFloatStateOf(0f) }
    var averageprotein by remember { mutableFloatStateOf(0f) }
    var averagefat by remember { mutableFloatStateOf(0f) }
    var averagefiber by remember { mutableFloatStateOf(0f) }
    var averagesugar by remember { mutableFloatStateOf(0f) }
    var averagesodium by remember { mutableFloatStateOf(0f) }
    var averagecalories by remember { mutableFloatStateOf(0f) }
    var carbpercent by remember { mutableFloatStateOf(0f) }
    var proteinpercent by remember { mutableFloatStateOf(0f) }
    var fatpercent by remember { mutableFloatStateOf(0f) }
    var rangepercentcarb by remember { mutableStateOf("") }
    var rangepercentprotein by remember { mutableStateOf("") }
    var rangepercentfat by remember { mutableStateOf("") }

    planUCImpl.percentToGram("carb", planSpecific.Caloriesgoal, planSpecific.carbongoal)
    {
        carbgoal = it
    }
    planUCImpl.percentToGram("protein", planSpecific.Caloriesgoal, planSpecific.proteingoal)
    {
        proteingoal = it
    }
    planUCImpl.percentToGram("fat", planSpecific.Caloriesgoal, planSpecific.fatgoal)
    {
        fatgoal = it
    }
    if (!showdatepick) {
        totalthree =
            totalNutrition.totalCarbon + totalNutrition.totalProtein + totalNutrition.totalFat
        averagecalories = totalNutrition.totalCalories / size
        averagecarb = totalNutrition.totalCarbon / size
        averageprotein = totalNutrition.totalProtein / size
        averagefat = totalNutrition.totalFat / size
        carbpercent = (totalNutrition.totalCarbon / totalthree) * 100
        proteinpercent = (totalNutrition.totalProtein / totalthree) * 100
        fatpercent = (totalNutrition.totalFat / totalthree) * 100
        rangepercentcarb =
            planUCImpl.averageNutrition(size = size, carbgoal, totalNutrition.totalCarbon)
        rangepercentprotein =
            planUCImpl.averageNutrition(size = size, proteingoal, totalNutrition.totalProtein)
        rangepercentfat = planUCImpl.averageNutrition(size = size, fatgoal, totalNutrition.totalFat)
        averagefiber = totalNutrition.totalFiber / size
        averagesugar = totalNutrition.totalSugar / size
        averagesodium = (totalNutrition.totalSodium / size) * 0.001f
    } else {
        if (diaryRangeList.isNotEmpty() && selectedIndex in diaryRangeList.indices) {
            val element = diaryRangeList[selectedIndex]
            totalthree = element.totalCarbon + element.totalProtein + element.totalFat
            averagecalories = element.totalCalories
            averagecarb = element.totalCarbon
            averageprotein = element.totalProtein
            averagefat = element.totalFat
            carbpercent = (element.totalCarbon / totalthree) * 100
            proteinpercent = (element.totalProtein / totalthree) * 100
            fatpercent = (element.totalFat / totalthree) * 100
            rangepercentcarb =
                planUCImpl.dayNutrition(carbgoal, element.totalCarbon)
            rangepercentprotein =
                planUCImpl.dayNutrition(proteingoal, element.totalProtein)
            rangepercentfat =
                planUCImpl.dayNutrition(fatgoal, element.totalFat)
            averagefiber = element.totalFiber
            averagesugar = element.totalSugar
            averagesodium = (element.totalSodium) * 0.001f
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollstate)
            .background(color = colorResource(id = R.color.backgroundcolor)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (isdiary) {
            //時間範圍
            Row(
                modifier = Modifier
                    .padding(top = 10.dp),
            ) {
                CustomText().TextWithDiffColor(
                    text = "${formatter(selectedPlan.startDateTime)}~${formatter(selectedPlan.endDateTime)}",
                    setsize = 18.sp
                )
            }
            if(showfinish) {
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                ) {
                    CustomText().TextWithDiffColor(
                        text = stringResource(R.string.isfinish),
                        setsize = 40.sp
                    )
                }
            }
            //達成度圖
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                AnimatedGaugeChart(
                    maxPercentage = finishpercent,
                    modifier = Modifier.size(200.dp)
                )
                CustomText().TextWithDiffColor(
                    text = stringResource(R.string.finishrate) + planUCImpl.formatToOneF(finishpercent) + "%",
                    setsize = 35.sp
                )
            }
            //達成度文字
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .padding(start = 30.dp),
            ) {
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
            when (showdatepick) {
                true -> {
                    CreateToggleButton(
                        onLeftClick = {
                            selectedIndex = -1
                            showdatepick = false
                        },
                        onRightClick = {},
                        leftButtonColor = R.color.light_gray,
                        rightButtonColor = R.color.primarycolor,
                        leftTextColor = R.color.darkgray,
                        rightTextColor = R.color.white,
                        leftText = stringResource(R.string.alltime),
                        rightText = stringResource(R.string.date),
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Start)
                            .padding(start = 38.dp),
                    ) {
                        Log.d(tag,"list size is ${diaryRangeList.size}")
                        val createDateList: MutableList<String> = ArrayList()
                        for ((_, _, createDate) in diaryRangeList) {
                            createDateList.add(planUCImpl.dateTimeFormat(createDate))
                        }

                        CreateDropDownMenu(
                            options = createDateList,
                            selectedOption = null,
                            onOptionSelected = { selectedIndex = createDateList.indexOf(it) },
                            getDisplayText = { it.toString() }
                        )
                    }
                }

                false -> {
                    CreateToggleButton(
                        onLeftClick = {},
                        onRightClick = { showdatepick = true },
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
                var isWarnColor by remember { mutableIntStateOf(R.color.black_300) }
                val Caloriesgoal = planSpecific.Caloriesgoal
                isWarnColor = if (averagecalories > Caloriesgoal) R.color.red01
                else R.color.black_300
                //文字
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                        .padding(start = 22.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.calorie),
                        contentDescription = "卡路里",
                        tint = colorResource(id = isWarnColor),
                        modifier = Modifier
                            .scale(1.5f)
                    )

                    CustomText().TextWithDiffColor(
                        text = stringResource(R.string.averagecalorie),
                        setsize = 24.sp
                    )
                }
                //BAR
                CreateAnimationBar(Caloriesgoal, averagecalories, 352.dp, 25.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 22.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    CustomText().TextWithDiffColor(
                        setcolor = isWarnColor,
                        text = planUCImpl.formatToOneF(averagecalories) + stringResource(R.string.cals),
                        setsize = 24.sp
                    )
                }
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
                        goal = carbgoal,
                        currentValue = averagecarb,
                        planUCImpl = planUCImpl
                    )

                    //蛋白質
                    ShowNutrition(
                        nutritionName = R.string.protein,
                        nutritionTextColor = R.color.sky_blue,
                        goal = proteingoal,
                        currentValue = averageprotein,
                        planUCImpl = planUCImpl
                    )

                    //脂肪
                    ShowNutrition(
                        nutritionName = R.string.fat,
                        nutritionTextColor = R.color.yellow_300,
                        goal = fatgoal,
                        currentValue = averagefat,
                        planUCImpl = planUCImpl
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(end = 15.dp)
                ) {
                    val data = listOf(
                        PieData(carbpercent, carbpercent, colorResource(R.color.light_red)),
                        PieData(proteinpercent, proteinpercent, colorResource(R.color.sky_blue)),
                        PieData(fatpercent, fatpercent, colorResource(R.color.yellow_300)),
                    )
                    CreatePieChart(
                        dataCollection = data.toChartDataCollection(),
                        modifier = Modifier.wrapContentSize()
                    )
                }
            }
            //營養成分標題
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .padding(start = 28.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.nutrition),
                    contentDescription = "卡路里",
                    tint = colorResource(id = R.color.black_300),
                    modifier = Modifier
                        .scale(1.2f)
                )

                CustomText().TextWithDiffColor(
                    text = stringResource(R.string.nutritionstitle),
                    setsize = 24.sp
                )
            }

            //營養成分表
            ShowNutritionList(
                planSpecificModel = planSpecific,
                planUCImpl = planUCImpl,
                carbgoal = carbgoal,
                proteingoal = proteingoal,
                fatgoal = fatgoal,
                rangepercentcarb = rangepercentcarb,
                rangepercentprotein = rangepercentprotein,
                rangepercentfat = rangepercentfat,
                averagesfiber = averagefiber,
                averagesugar = averagesugar,
                averagesodium = averagesodium,
                averagescarb = averagecarb,
                averagesprotein = averageprotein,
                averagesfat = averagefat,
            )

        } else {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                CustomText().TextWithDiffColor(
                    setcolor = R.color.primarycolor,
                    text = stringResource(R.string.no_diarytext),
                    setsize = 40.sp
                )
            }
        }
    }

}


@Composable
fun ShowNutritionList(
    planSpecificModel: PlanSpecificModel,
    planUCImpl: PlanUCImpl,
    carbgoal: Float,
    proteingoal: Float,
    fatgoal: Float,
    rangepercentcarb: String,
    rangepercentprotein: String,
    rangepercentfat: String,
    averagescarb: Float,
    averagesprotein: Float,
    averagesfat: Float,
    averagesfiber: Float,
    averagesugar: Float,
    averagesodium: Float,
) {
    val tag = "tag_ShowNutritionList"
    val fibergoal = 30f
    val sugargoal = (planSpecificModel.Caloriesgoal) * 0.1f
    val sodiumgoal = 2.4f
    var isWarnColor by remember { mutableIntStateOf(R.color.black_300) }
    val gramStr = stringResource(R.string.grams)
    val context = LocalContext.current

    NutritionType.entries.forEachIndexed { index, nutritionType ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, bottom = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(0.5f),
                horizontalArrangement = Arrangement.Start
            ) {
                CustomText().TextWithDiffColor(
                    text = nutritionType.getNutritionTitle(context),
                    setsize = 14.sp
                )
            }
            Column(
                modifier = Modifier
                    .weight(1.0f),
            ) {
                Row(
                    modifier = Modifier.width(220.dp)
                ) {
                    when (index) {
                        0 -> {
                            CustomText().TextWithDiffColor(
                                text = planUCImpl.formatToOneF(averagescarb) + gramStr,
                                setsize = 14.sp
                            )
                            Row(
                                modifier = Modifier.weight(1.0f),
                                horizontalArrangement = Arrangement.End
                            ) {
                                isWarnColor = if (rangepercentcarb.toFloat() > 100.0f) R.color.red01
                                else R.color.black_300
                                CustomText().TextWithDiffColor(
                                    setcolor = isWarnColor,
                                    text = "${rangepercentcarb}%",
                                    setsize = 14.sp
                                )
                            }
                        }

                        1 -> {
                            CustomText().TextWithDiffColor(
                                text = planUCImpl.formatToOneF(averagesprotein) + gramStr,
                                setsize = 14.sp
                            )
                            Row(
                                modifier = Modifier.weight(1.0f),
                                horizontalArrangement = Arrangement.End
                            ) {
                                isWarnColor =
                                    if (rangepercentprotein.toFloat() > 100.0f) R.color.red01
                                    else R.color.black_300
                                CustomText().TextWithDiffColor(
                                    setcolor = isWarnColor,
                                    text = "$rangepercentprotein%",
                                    setsize = 14.sp
                                )
                            }
                        }

                        2 -> {
                            CustomText().TextWithDiffColor(
                                text = planUCImpl.formatToOneF(averagesfat) + gramStr,
                                setsize = 14.sp
                            )
                            Row(
                                modifier = Modifier.weight(1.0f),
                                horizontalArrangement = Arrangement.End
                            ) {
                                isWarnColor = if (rangepercentfat.toFloat() > 100.0f) R.color.red01
                                else R.color.black_300
                                CustomText().TextWithDiffColor(
                                    setcolor = isWarnColor,
                                    text = "$rangepercentfat%",
                                    setsize = 14.sp
                                )
                            }
                        }
                    }
                    //fiber,sugar,sodium
                    when (index) {
                        3 -> {
                            isWarnColor = if (averagesfiber > fibergoal) R.color.red01
                            else R.color.black_300
                            CustomText().TextWithDiffColor(
                                setcolor = isWarnColor,
                                text = planUCImpl.formatToOneF(averagesfiber) + gramStr,
                                setsize = 14.sp
                            )
                        }

                        4 -> {
                            isWarnColor = if (averagesugar > sugargoal) R.color.red01
                            else R.color.black_300
                            CustomText().TextWithDiffColor(
                                setcolor = isWarnColor,
                                text = planUCImpl.formatToOneF(averagesugar) + gramStr,
                                setsize = 14.sp
                            )
                        }

                        5 -> {
                            isWarnColor = if (averagesodium > sodiumgoal) R.color.red01
                            else R.color.black_300
                            CustomText().TextWithDiffColor(
                                text = planUCImpl.formatToOneF(averagesodium) + gramStr,
                                setsize = 14.sp
                            )
                        }
                    }
                }
                //create bar
                when (index) {
                    0 -> {
                        Row(
                            horizontalArrangement = Arrangement.Start
                        ) {
                            isWarnColor = if (averagescarb > carbgoal) R.color.red01
                            else R.color.light_green
                            CreateAnimationBar(
                                carbgoal,
                                averagescarb,
                                220.dp,
                                10.dp,
                                dynamicColor = isWarnColor
                            )
                        }
                    }

                    1 -> {
                        Row(
                            horizontalArrangement = Arrangement.Start
                        ) {
                            isWarnColor = if (averagesprotein > proteingoal) R.color.red01
                            else R.color.light_green
                            CreateAnimationBar(
                                proteingoal,
                                averagesprotein,
                                220.dp,
                                10.dp,
                                dynamicColor = isWarnColor
                            )
                        }
                    }

                    2 -> {
                        Row(
                            horizontalArrangement = Arrangement.Start
                        ) {
                            isWarnColor = if (averagesfat > fatgoal) R.color.red01
                            else R.color.light_green
                            CreateAnimationBar(
                                fatgoal,
                                averagesfat,
                                220.dp,
                                10.dp,
                                dynamicColor = isWarnColor
                            )
                        }
                    }

                    3 -> {
                        Row(
                            horizontalArrangement = Arrangement.Start
                        ) {
                            isWarnColor = if (averagesfiber > fibergoal) R.color.red01
                            else R.color.light_green
                            CreateAnimationBar(
                                fibergoal,
                                averagesfiber,
                                220.dp,
                                10.dp,
                                dynamicColor = isWarnColor
                            )
                        }
                    }

                    4 -> {
                        Row(
                            horizontalArrangement = Arrangement.Start
                        ) {
                            isWarnColor = if (averagesugar > sugargoal) R.color.red01
                            else R.color.light_green
                            CreateAnimationBar(
                                sugargoal,
                                averagesugar,
                                220.dp,
                                10.dp,
                                dynamicColor = isWarnColor
                            )
                        }
                    }

                    5 -> {
                        Row(
                            horizontalArrangement = Arrangement.Start
                        ) {
                            isWarnColor = if (averagesodium > sodiumgoal) R.color.red01
                            else R.color.light_green
                            CreateAnimationBar(
                                sodiumgoal,
                                averagesodium,
                                220.dp,
                                10.dp,
                                dynamicColor = isWarnColor
                            )
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun ShowNutrition(
    @StringRes nutritionName: Int,
    @ColorRes nutritionTextColor: Int,
    goal: Float,
    currentValue: Float,
    barHeight: Dp = 10.dp,
    barWidth: Dp = 124.dp,
    planUCImpl: PlanUCImpl,
) {
    var adjustOffset = (-20).dp
    var isWarnColor by remember { mutableIntStateOf(R.color.light_green) }
    var showwarning by remember { mutableStateOf(false) }
    if (currentValue > goal) {
        showwarning = true
        isWarnColor = R.color.red01
    } else {
        showwarning = false
        isWarnColor = R.color.light_green
    }
    if (!showwarning) adjustOffset = 0.dp
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.Start
    ) {
        CustomText().TextWithDiffColor(
            text = stringResource(nutritionName),
            setsize = 18.sp,
            setcolor = nutritionTextColor
        )
        //bar+icon
        Row(
            modifier = Modifier
                .offset(adjustOffset),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (showwarning) {
                Icon(
                    painter = painterResource(id = R.drawable.warnsign),
                    contentDescription = "warn",
                    tint = colorResource(id = R.color.red01),
                    modifier = Modifier
                        .scale(1.5f)
                )
            }

            CreateAnimationBar(goal, currentValue, barWidth, barHeight, dynamicColor = isWarnColor)
        }
        //文字敘述
        Row(
            modifier = Modifier
                .width(124.dp),
        ) {
            CustomText().TextWithDiffColor(
                text = "${planUCImpl.formatToOneF(goal)}${stringResource(R.string.grams)}",
                setsize = 10.sp,
            )
            Row(
                modifier = Modifier
                    .weight(1.0f)
                    .padding(start = 10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (currentValue > goal) {
                    val count = currentValue - goal
                    CustomText().TextWithDiffColor(
                        setcolor = isWarnColor,
                        text = stringResource(R.string.over) +
                                planUCImpl.formatToOneF(count) +
                                stringResource(R.string.grams),
                        setsize = 10.sp,
                    )
                } else {
                    val count = goal - currentValue
                    CustomText().TextWithDiffColor(
                        text = stringResource(R.string.under) +
                                planUCImpl.formatToOneF(count) +
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
fun checkPlanPreview() {
    CheckPlan(planVM = viewModel(), managePlanVM = viewModel(), checkVM = viewModel())
}