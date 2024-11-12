package com.example.healthhelper.plan.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.plan.DateRange
import com.example.healthhelper.plan.PlanPage
import com.example.healthhelper.plan.ui.CreateDatePicker
import com.example.healthhelper.plan.ui.CreateDropDownMenu
import com.example.healthhelper.plan.ui.CreatePieChart
import com.example.healthhelper.plan.ui.CustomButton
import com.example.healthhelper.plan.ui.CustomSnackBar
import com.example.healthhelper.plan.ui.CustomText
import com.example.healthhelper.plan.ui.CustomTextField
import com.example.healthhelper.plan.ui.createSliders
import com.example.healthhelper.plan.usecase.PlanUCImpl
import com.example.healthhelper.plan.viewmodel.EditPlanVM
import com.example.healthhelper.plan.viewmodel.ManagePlanVM
import com.example.healthhelper.plan.viewmodel.PlanVM
import com.example.healthhelper.screen.TabViewModel
import com.example.healthhelper.signuplogin.UserManager
import com.example.healthhelper.ui.theme.HealthHelperTheme
import com.himanshoe.charty.common.toChartDataCollection
import com.himanshoe.charty.pie.model.PieData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.util.Locale

@Composable
fun CustomEditPlan(
    planname: PlanPage,
    tabVM: TabViewModel = viewModel(),
    editPlanVM: EditPlanVM,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    navController: NavHostController = rememberNavController(),
) {
    val tag = "tag_CustomEditPlan"
    val scrollstate = rememberScrollState()
    tabVM.setTabVisibility(false)
    val context = LocalContext.current
    val df = DecimalFormat("#.#")
    val currentuserId = UserManager.getUser().userId

    val calorieErr = stringResource(R.string.calorieerror)
    val dateErr = stringResource(R.string.dateerror)
    val percentErr = stringResource(R.string.percenterror)
    val insertSuccess = stringResource(R.string.insertplansuccess)
    val insertFailed = stringResource(R.string.insertplanfail)

    var selectedDate by remember { mutableStateOf("") }
    //display nutrient gram value
    var carbgram by remember { mutableFloatStateOf(0.0f) }
    var proteingram by remember { mutableFloatStateOf(0.0f) }
    var fatgram by remember { mutableFloatStateOf(0.0f) }

    //display nutrient percent value
    var carbpercent by remember { mutableFloatStateOf(30.0f) }
    var proteinpercent by remember { mutableFloatStateOf(30.0f) }
    var fatpercent by remember { mutableFloatStateOf(40.0f) }

    //display calorie value
    var calorie by remember { mutableFloatStateOf(0f) }
    val planUCImpl = remember { PlanUCImpl() }

    //set customplan cateId
    planUCImpl.customPlanInitial(
        planName = planname,
        onSetCateId = { cateId ->
            editPlanVM.updateCategoryId(cateId)
        }
    )

    // 計算克數
    fun updateGrams() {
        planUCImpl.percentToGram("carb", calorie, carbpercent) { carbgram = it }
        planUCImpl.percentToGram("protein", calorie, proteinpercent) { proteingram = it }
        planUCImpl.percentToGram("fat", calorie, fatpercent) { fatgram = it }
    }

    // 每次更改百分比時保持總和為 100 並更新克數
    fun adjustAndRefreshValues(changedPercent: Float, onValueChange: (Float) -> Unit) {
        onValueChange(changedPercent)
        val remaining = 100f - carbpercent - proteinpercent
        fatpercent = remaining.coerceIn(0f, 100f)
        updateGrams()
    }


    //UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.backgroundcolor))
            .verticalScroll(scrollstate)
            .padding(top = 10.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 13.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
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
                onOptionSelected = { selectedDate = it.toString() },
                getDisplayText = { context.getString(it.title) }
            )

            Text(
                text = stringResource(R.string.startdate),
                style = TextStyle(
                    fontSize = 22.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = colorResource(id = R.color.black_300),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.2.sp
                )
            )

            CreateDatePicker(dateSelected = selectedDate, EditPlanVM = editPlanVM)

            Text(
                text = stringResource(R.string.enddate),
                style = TextStyle(
                    fontSize = 22.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = colorResource(id = R.color.black_300),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.2.sp
                )
            )

            CreateDatePicker(
                dateSelected = selectedDate,
                dateStart = false,
                EditPlanVM = editPlanVM
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(start = 30.dp, end = 55.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start
            ) {
                for (i in 0..2) {
                    when (i) {
                        0 -> CustomText().TextWithDiffColor(
                            setcolor = R.color.light_red,
                            text = stringResource(R.string.carb) +
                                    "${String.format(Locale.US, "%.1f", carbgram)}克",
                            setsize = 20.sp
                        )

                        1 -> CustomText().TextWithDiffColor(
                            setcolor = R.color.sky_blue,
                            text = stringResource(R.string.protein) +
                                    "${String.format(Locale.US, "%.1f", proteingram)}克",
                            setsize = 20.sp
                        )

                        2 -> CustomText().TextWithDiffColor(
                            setcolor = R.color.dark_green,
                            text = stringResource(R.string.fat) +
                                    "${String.format(Locale.US, "%.1f", fatgram)}克",
                            setsize = 20.sp
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(end = 10.dp)
            ) {
                val data = listOf(
                    PieData(carbpercent, carbpercent, colorResource(R.color.light_red)),
                    PieData(proteinpercent, proteinpercent, colorResource(R.color.sky_blue)),
                    PieData(fatpercent, fatpercent, colorResource(R.color.light_green)),
                )
                CreatePieChart(
                    dataCollection = data.toChartDataCollection(),
                    modifier = Modifier.wrapContentSize()
                )
            }
        }

        Column(
            modifier = Modifier.padding(top = 10.dp, start = 13.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomText().TextWithDiffColor(
                    R.color.primarycolor,
                    "${stringResource(R.string.setting)}${stringResource(R.string.dailygoal)}",
                    26.sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                CustomText().TextWithDiffColor(
                    R.color.black_300,
                    stringResource(R.string.calories),
                    22.sp
                )
                CustomTextField().TextFieldWithBorder(
                    value = calorie,
                    onValueChange = { newvalue ->
                        calorie = newvalue
                        updateGrams()
                    },
                    label = stringResource(R.string.examcalorie),
                    width = 130.dp
                )
                CustomText().TextWithDiffColor(R.color.black_300, stringResource(R.string.cals), 16.sp)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText().TextWithDiffColor(
                    text = stringResource(R.string.carb),
                    setsize = 22.sp
                )

                CustomTextField().TextFieldWithBorder(
                    value = carbpercent,
                    onValueChange = { newValue ->
                        carbpercent = newValue
                        val remaining = 100f - carbpercent
                        // 將剩餘值按比例分配給 proteinpercent 和 fatpercent
                        proteinpercent = (remaining * 0.5f).coerceIn(0f, remaining)
                        fatpercent = (remaining * 0.5f).coerceIn(0f, remaining)
                        val total = carbpercent + proteinpercent + fatpercent
                        val adjustment = 100f - total
                        carbpercent += adjustment
                        adjustAndRefreshValues(newValue) { carbpercent = it }
                    },
                    label = stringResource(R.string.exampercent),
                    width = 100.dp,
                    modifier = Modifier
                )

                CustomText().TextWithDiffColor(
                    text = "%(${
                        String.format(
                            Locale.US,
                            "%.1f",
                            carbgram
                        )
                    }克)", setsize = 20.sp
                )
            }

            createSliders(
                carbpercent,
                0.0f,
                100.0f,
                R.color.light_red,
                R.color.light_gray
            ) { newValue ->
                updateSliders(
                    "carb",
                    newValue,
                    carbpercent,
                    proteinpercent,
                    fatpercent,
                    { carbpercent = it },
                    { proteinpercent = it },
                    { fatpercent = it })
                carbpercent = newValue
                adjustAndRefreshValues(newValue) { carbpercent = it }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText().TextWithDiffColor(
                    text = stringResource(R.string.protein),
                    setsize = 22.sp
                )

                CustomTextField().TextFieldWithBorder(
                    value = proteinpercent,
                    onValueChange = { newValue ->
                        proteinpercent = newValue
                        val remaining = 100f - carbpercent - proteinpercent
                        // 更新 fatpercent，使總和保持 100
                        fatpercent = remaining.coerceIn(0f, 100f - carbpercent - proteinpercent)

                        adjustAndRefreshValues(newValue) { proteinpercent = it }
                    },
                    label = stringResource(R.string.exampercent),
                    width = 100.dp,
                    modifier = Modifier
                )

                CustomText().TextWithDiffColor(
                    text = "%(${
                        String.format(
                            Locale.US,
                            "%.1f",
                            proteingram
                        )
                    }克)", setsize = 20.sp
                )
            }

            createSliders(
                proteinpercent,
                0.0f,
                100.0f - carbpercent,
                R.color.sky_blue,
                R.color.light_gray
            ) { newValue ->
                updateSliders(
                    "protein",
                    newValue,
                    carbpercent,
                    proteinpercent,
                    fatpercent,
                    { carbpercent = it },
                    { proteinpercent = it },
                    { fatpercent = it })
                adjustAndRefreshValues(newValue) { proteinpercent = it }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText().TextWithDiffColor(
                    text = stringResource(R.string.fat),
                    setsize = 22.sp
                )

                CustomTextField().TextFieldWithBorder(
                    value = fatpercent,
                    onValueChange = { newValue ->
                        fatpercent = newValue
                        updateGrams()
                    },
                    label = stringResource(R.string.exampercent),
                    width = 100.dp,
                    modifier = Modifier
                )

                CustomText().TextWithDiffColor(
                    text = "%(${
                        String.format(
                            Locale.US,
                            "%.1f",
                            fatgram
                        )
                    }克)", setsize = 20.sp
                )
            }

            createSliders(
                fatpercent,
                0.0f,
                100.0f - carbpercent,
                R.color.light_green,
                R.color.light_gray
            ) { newValue ->
                updateSliders(
                    "fat",
                    newValue,
                    carbpercent,
                    proteinpercent,
                    fatpercent,
                    { carbpercent = it },
                    { proteinpercent = it },
                    { fatpercent = it })
                fatpercent = newValue
                updateGrams()
            }

        }

        //SaveButton
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(
                0.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomButton().CreateButton(
                text = stringResource(R.string.save),
                color = R.color.primarycolor,
                onClick = OnClick@{
                    //檢查卡路里數值不得為小於1200
                    if (calorie <= 1200f) {
                        scope.launch {
                            CustomSnackBar().CreateSnackBar(
                                message = calorieErr,
                                snackbarHostState = snackBarHostState
                            )
                            Log.d(tag, "calorieErr SnackBar")
                        }
                        return@OnClick
                    }

                    //檢查日期不能同一天和空值
                    val startdate = editPlanVM.planSetState.value.startDateTime
                    val enddate = editPlanVM.planSetState.value.endDateTime
                    if (startdate == enddate || startdate == null || enddate == null) {
                        scope.launch {
                            CustomSnackBar().CreateSnackBar(
                                message = dateErr,
                                snackbarHostState = snackBarHostState
                            )
                            Log.d(tag, "dateErr SnackBar")
                        }
                        return@OnClick
                    }

                    //檢查三個目標相加不為100
                    if ((carbpercent + proteinpercent + fatpercent) != 100f) {
                        scope.launch {
                            CustomSnackBar().CreateSnackBar(
                                message = percentErr,
                                snackbarHostState = snackBarHostState
                            )
                        }
                        return@OnClick
                    }

                    editPlanVM.updateCarbGoal(df.format(carbpercent).toFloat())
                    editPlanVM.updateProteinGoal(df.format(proteinpercent).toFloat())
                    editPlanVM.updateFatGoal(df.format(fatpercent).toFloat())
                    editPlanVM.updateUserId(currentuserId)
                    editPlanVM.updateFinishState(0)
                    editPlanVM.updateCaloriesGoal(calorie)
                    scope.launch {
                        if (editPlanVM.insertPlan()) {
                            CustomSnackBar().CreateSnackBar(
                                message = insertSuccess,
                                snackbarHostState = snackBarHostState
                            )
                            Log.d(tag, "insertSuccess")
                            navController.navigate(PlanPage.DietPlan.name)
                        } else {
                            CustomSnackBar().CreateSnackBar(
                                message = insertFailed,
                                snackbarHostState = snackBarHostState
                            )
                            Log.d(tag, "insertFailed")
                        }

                    }
                }
            )
        }

    }

}

fun updateSliders(changedSlider: String,
                  newValue: Float,
                  carbpercent: Float,
                  proteinpercent: Float,
                  fatpercent: Float,
                  oncarbpercentChange: (Float) -> Unit,
                  onproteinpercentChange: (Float) -> Unit,
                  onfatpercentChange: (Float) -> Unit
) {
    when (changedSlider) {
        "carb" -> {
            oncarbpercentChange(newValue)
            val remaining = 100f - carbpercent
            if (proteinpercent + fatpercent == 0f) {
                onproteinpercentChange(remaining)
                onfatpercentChange(0f)
            } else {
                // 將剩餘百分比按比例分配給 proteinpercent 和 fatpercent
                onproteinpercentChange((remaining * (proteinpercent / (proteinpercent + fatpercent))).coerceIn(0f, remaining))
                onfatpercentChange((remaining - proteinpercent).coerceIn(0f, remaining))
            }
        }

        "protein" -> {
            onproteinpercentChange(newValue.coerceIn(0f, 100f - carbpercent))
            onfatpercentChange((100f - carbpercent - proteinpercent).coerceIn(0f, 100f - carbpercent))
        }

        "fat" -> {
            onfatpercentChange(newValue.coerceIn(0f, 100f - carbpercent))
            onproteinpercentChange((100f - carbpercent - fatpercent).coerceIn(0f, 100f - carbpercent))
        }
    }
}

@Preview(locale = "zh-rTW")
@Composable
fun CustomEditPlanPreview() {
    HealthHelperTheme {
//        CustomEditPlan(PlanPage.Custom,
//            EditPlanVM = viewModel(),
//            ManagePlanVM = viewModel(),
//            scope = CoroutineScope(),
//            snackbarHostState = SnackbarHostState(),
//            navcontroller = rememberNavController(),
//            planVM = viewModel(),
//        )
    }
}