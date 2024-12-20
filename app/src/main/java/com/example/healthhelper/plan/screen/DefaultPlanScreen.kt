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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.healthhelper.plan.usecase.PlanUCImpl
import com.example.healthhelper.plan.viewmodel.EditPlanVM
import com.example.healthhelper.screen.TabViewModel
import com.example.healthhelper.signuplogin.UserManager
import com.example.healthhelper.ui.theme.HealthHelperTheme
import com.himanshoe.charty.common.toChartDataCollection
import com.himanshoe.charty.pie.model.PieData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun EditPlan(
    planname: PlanPage,
    tabViewModel: TabViewModel = viewModel(),
    EditPlanVM: EditPlanVM,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    navcontroller: NavHostController = rememberNavController(),
) {
    val tag = "tag_EditPlan"
    val scrollstate = rememberScrollState()
    tabViewModel.setTabVisibility(false)
    val context = LocalContext.current
    val planUCImpl = remember { PlanUCImpl() }
    val currentuserId = UserManager.getUser().userId

    val calorieErr = stringResource(R.string.calorieerror)
    val dateErr = stringResource(R.string.dateerror)
    val insertSuccess = stringResource(R.string.insertplansuccess)
    val insertFailed = stringResource(R.string.insertplanfail)

    var selectedDate by remember { mutableStateOf("") }
    //display nutrient gram value
    var carbgram by remember { mutableFloatStateOf(0.0f) }
    var proteingram by remember { mutableFloatStateOf(0.0f) }
    var fatgram by remember { mutableFloatStateOf(0.0f) }

    //display nutrient percent value
    var carbpercent by remember { mutableFloatStateOf(0.0f) }
    var proteinpercent by remember { mutableFloatStateOf(0.0f) }
    var fatpercent by remember { mutableFloatStateOf(0.0f) }

    //display calorie value
    var calorie by remember { mutableFloatStateOf(0f) }

//    var descriptiontitle by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }

    //set nutrient percent value by planname
    planUCImpl.InitialDefaultGoal(
        planName = planname,
        onSetGoal = { fat, carb, protein ->
            //show percent on UI
            fatpercent = fat
            carbpercent = carb
            proteinpercent = protein
            //save the goal to state
            EditPlanVM.updateFatGoal(fat)
            EditPlanVM.updateCarbGoal(carb)
            EditPlanVM.updateProteinGoal(protein)
        },
        onSetCateId = { cateId ->
            EditPlanVM.updateCategoryId(cateId)
        }
    )

    planUCImpl.percentToGram("carb", calorie, carbpercent) { carbgram = it }
    planUCImpl.percentToGram("protein", calorie, proteinpercent) { proteingram = it }
    planUCImpl.percentToGram("fat", calorie, fatpercent) { fatgram = it }


    // UI
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
                    color = colorResource(id = R.color.black),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.2.sp
                )
            )

            CreateDatePicker(dateSelected = selectedDate, EditPlanVM = EditPlanVM)

            Text(
                text = stringResource(R.string.enddate),
                style = TextStyle(
                    fontSize = 22.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = colorResource(id = R.color.black),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.2.sp
                )
            )

            CreateDatePicker(
                dateSelected = selectedDate,
                dateStart = false,
                EditPlanVM = EditPlanVM
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
                    stringResource(R.string.dailygoal),
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
                    R.color.black,
                    stringResource(R.string.calories),
                    22.sp
                )
                CustomTextField().TextFieldWithBorder(
                    value = calorie,
                    onValueChange = { newvalue -> calorie = newvalue },
                    label = stringResource(R.string.examcalorie),
                    width = 130.dp
                )
                CustomText().TextWithDiffColor(R.color.black, stringResource(R.string.cals), 16.sp)
            }

            CreateDesciption(
                planname = planname,
                type = R.string.carb,
                gram = carbgram,
                percent = carbpercent,
            )

            HorizontalDivider(
                thickness = 2.dp,
                color = colorResource(R.color.very_light_gray)
            )

            CreateDesciption(
                planname = planname,
                type = R.string.protein,
                gram = proteingram,
                percent = proteinpercent,
            )

            HorizontalDivider(
                thickness = 2.dp,
                color = colorResource(R.color.very_light_gray)
            )

            CreateDesciption(
                planname = planname,
                type = R.string.fat,
                gram = fatgram,
                percent = fatpercent,
            )

            HorizontalDivider(
                thickness = 2.dp,
                color = colorResource(R.color.very_light_gray)
            )

        }

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
                textcolor = R.color.white,
                color = R.color.primarycolor,
                onClick = OnClick@{
                    //save plan
                    if (calorie <= 1200f) {
                        scope.launch {
                            CustomSnackBar().CreateSnackBar(
                                message = calorieErr,
                                snackbarHostState = snackbarHostState
                            )
                            Log.d(tag,"calorieErr SnackBar")
                        }
                        return@OnClick
                    }

                    val startdate = EditPlanVM.planSetState.value.startDateTime
                    val enddate = EditPlanVM.planSetState.value.endDateTime
                    if (startdate == enddate || startdate == null || enddate == null) {
                        scope.launch {
                            CustomSnackBar().CreateSnackBar(
                                message = dateErr,
                                snackbarHostState = snackbarHostState
                            )
                            Log.d(tag,"dateErr SnackBar")
                        }
                        return@OnClick
                    }

                    EditPlanVM.updateUserId(currentuserId)
                    EditPlanVM.updateFinishState(0)
                    EditPlanVM.updateCaloriesGoal(calorie)
                    scope.launch {
                        if (EditPlanVM.insertPlan())
                        {
                            CustomSnackBar().CreateSnackBar(
                                message = insertSuccess,
                                snackbarHostState = snackbarHostState
                            )
                            Log.d(tag,"insertSuccess")
                            navcontroller.navigate(PlanPage.DietPlan.name)
                        }else{
                            CustomSnackBar().CreateSnackBar(
                                message = insertFailed,
                                snackbarHostState = snackbarHostState
                            )
                            Log.d(tag,"insertFailed")
                        }

                    }
                }
            )
        }

    }

}

@Composable
fun CreateDesciption(
    planname: PlanPage,
    type: Int,
    gram: Float,
    percent: Float,
) {
    var descriptiontitle by remember { mutableIntStateOf(0) }
    var description by remember { mutableIntStateOf(0) }
    setDescription(
        planname,
        onsetTitle = {descriptiontitle = it},
        onsetDescription = {description = it},
        type = type
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically

        ) {
            CustomText().TextWithDiffColor(text = stringResource(type), setsize = 25.sp)
            CustomText().TextWithDiffColor(text = "$percent%(${String.format(Locale.US, "%.1f", gram)}克)", setsize = 20.sp)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically

        ) {
            CustomText().TextWithDiffColor(text = stringResource(descriptiontitle), setsize = 14.sp)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically

        ) {
            CustomText().TextWithDiffColor(text = stringResource(description), setsize = 14.sp)
        }
    }
}

fun setDescription(
    planname: PlanPage,
    type: Int,
    onsetTitle: (title: Int) -> Unit,
    onsetDescription: (description: Int) -> Unit
) {
    // 根據 type 設置 title 和 description 的 resource id
    val (titleResId, descriptionResId) = when (type) {
        R.string.carb -> {
            when (planname) {
                PlanPage.LowCarb -> R.string.lowcarb_carb_title to R.string.lowcarb_carb_description
                PlanPage.HighProtein -> R.string.highpro_carb_title to R.string.highpro_carb_description
                PlanPage.Ketone -> R.string.ketone_carb_title to R.string.ketone_carb_description
                PlanPage.Mediterra -> R.string.mediterra_carb_title to R.string.mediterra_carb_description
                else -> R.string.defaulttitle to R.string.defaultdescription

            }
        }
        R.string.protein -> {
            when (planname) {
                PlanPage.LowCarb -> R.string.lowcarb_pro_title to R.string.lowcarb_pro_description
                PlanPage.HighProtein -> R.string.highpro_pro_title to R.string.highpro_pro_description
                PlanPage.Ketone -> R.string.ketone_pro_title to R.string.ketone_pro_description
                PlanPage.Mediterra -> R.string.mediterra_pro_title to R.string.mediterra_pro_description
                else -> R.string.defaulttitle to R.string.defaultdescription
            }
        }
        R.string.fat -> {
            when (planname) {
                PlanPage.LowCarb -> R.string.lowcarb_fat_title to R.string.lowcarb_fat_descipt
                PlanPage.HighProtein -> R.string.highpro_fat_title to R.string.highpro_fat_description
                PlanPage.Ketone -> R.string.ketone_fat_title to R.string.ketone_fat_description
                PlanPage.Mediterra -> R.string.meditera_fat_title to R.string.mediterra_fat_description
                else -> R.string.defaulttitle to R.string.defaultdescription
            }
        }
        else -> R.string.defaulttitle to R.string.defaultdescription
    }

    // 設置 title 和 description
    onsetTitle(titleResId)
    onsetDescription(descriptionResId)
}



@Preview(locale = "zh-rTW")
@Composable
fun EditPlanPreview() {
    HealthHelperTheme {
       // EditPlan(PlanPage.LowCarb, EditPlanVM = viewModel(), scope = CoroutineScope, snackbarHostState)
    }
}