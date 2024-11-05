package com.example.healthhelper.plan.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.plan.DateRange
import com.example.healthhelper.plan.PlanPage
import com.example.healthhelper.plan.ui.CreateDatePicker
import com.example.healthhelper.plan.ui.CreateDropDownMenu
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
import com.example.healthhelper.ui.theme.HealthHelperTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@Composable
fun CustomEditPlan(
    planname: PlanPage,
    tabViewModel: TabViewModel = viewModel(),
    EditPlanVM: EditPlanVM,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    navcontroller: NavHostController = rememberNavController(),
    planVM: PlanVM,
    ManagePlanVM: ManagePlanVM,
) {
    val tag = "tag_CustomEditPlan"
    val scrollstate = rememberScrollState()
    tabViewModel.setTabVisibility(false)
    val context = LocalContext.current
    val fetchSingle = PlanUCImpl()::fetchSingle
    val fetchList = PlanUCImpl()::fetchList
    val df = DecimalFormat("#.#")

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

    //set customplan cateId
    PlanUCImpl().customPlanInitial(
        planName = planname,
        onSetCateId = { cateId ->
            EditPlanVM.updateCategoryId(cateId)
        }
    )

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
                modifier = Modifier.padding(start = 30.dp, end = 80.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start
            ) {
                for (i in 0..2) {
                    when (i) {
                        0 -> CustomText().TextWithDiffColor(
                            R.color.red01,
                            "${stringResource(R.string.carb)} $carbgram 克", 15.sp
                        )

                        1 -> CustomText().TextWithDiffColor(
                            R.color.sky_blue,
                            "${stringResource(R.string.protein)} $proteingram 克", 15.sp
                        )

                        2 -> CustomText().TextWithDiffColor(
                            R.color.light_green,
                            "${stringResource(R.string.fat)} $fatgram 克", 15.sp
                        )
                    }
                }
            }

            Column(
            ) {
                Image(
                    painter = painterResource(R.drawable.myplanimg),
                    contentDescription = "",
                    modifier = Modifier
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
                        // 將剩餘值按比例分配給 proteinPercent 和 fatPercent
                        proteinpercent = remaining * 0.5f
                        fatpercent = remaining * 0.5f
                        val total = carbpercent + proteinpercent + fatpercent
                        val adjustment = 100f - total
                        carbpercent += adjustment
                        PlanUCImpl().percentToGram("carb",calorie,carbpercent){carbgram = it}
                    },
                    label = stringResource(R.string.exampercent),
                    width = 100.dp,
                    modifier = Modifier
                )

                CustomText().TextWithDiffColor(text = "%(${carbgram}克)", setsize = 20.sp)
            }

            createSliders(
                carbpercent,
                0f,
                100f,
                R.color.primarycolor,
                R.color.light_gray
            ) {newValue ->
                carbpercent = newValue
                val remaining = 100f - carbpercent
                // 將剩餘值按比例分配給 proteinPercent 和 fatPercent
                proteinpercent = remaining * 0.5f
                fatpercent = remaining * 0.5f
                val total = carbpercent + proteinpercent + fatpercent
                val adjustment = 100f - total
                carbpercent += adjustment
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
                        // 更新 fatPercent，使總和保持 100
                        fatpercent = remaining.coerceIn(0f, 100f)
                    },
                    label = stringResource(R.string.exampercent),
                    width = 100.dp,
                    modifier = Modifier
                )

                CustomText().TextWithDiffColor(text = "%(${proteingram}克)", setsize = 20.sp)
            }

            createSliders(
                proteinpercent,
                0f,
                100f,
                R.color.primarycolor,
                R.color.light_gray
            ) {newValue ->
                proteinpercent = newValue
                val remaining = 100f - carbpercent - proteinpercent
                // 更新 fatPercent，使總和保持 100
                fatpercent = remaining.coerceIn(0f, 100f)
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
                    },
                    label = stringResource(R.string.exampercent),
                    width = 100.dp,
                    modifier = Modifier
                )

                CustomText().TextWithDiffColor(text = "%(${fatgram}克)", setsize = 20.sp)
            }

            createSliders(
                fatpercent,
                0f,
                100f,
                R.color.primarycolor,
                R.color.light_gray
            ) {newValue ->
                fatpercent = newValue
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
                                snackbarHostState = snackbarHostState
                            )
                            Log.d(tag, "calorieErr SnackBar")
                        }
                        return@OnClick
                    }

                    //檢查日期不能同一天和空值
                    val startdate = EditPlanVM.planSetState.value.startDateTime
                    val enddate = EditPlanVM.planSetState.value.endDateTime
                    if (startdate == enddate || startdate == null || enddate == null) {
                        scope.launch {
                            CustomSnackBar().CreateSnackBar(
                                message = dateErr,
                                snackbarHostState = snackbarHostState
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
                                snackbarHostState = snackbarHostState
                            )
                        }
                        return@OnClick
                    }

                    EditPlanVM.updateCarbGoal(df.format(carbpercent).toFloat())
                    EditPlanVM.updateProteinGoal(df.format(proteinpercent).toFloat())
                    EditPlanVM.updateFatGoal(df.format(fatpercent).toFloat())
                    EditPlanVM.updateUserId(2)
                    EditPlanVM.updateFinishState(0)
                    EditPlanVM.updateCaloriesGoal(calorie)
                    scope.launch {
                        if (EditPlanVM.insertPlan()) {
                            CustomSnackBar().CreateSnackBar(
                                message = insertSuccess,
                                snackbarHostState = snackbarHostState
                            )
                            Log.d(tag, "insertSuccess")
                            fetchSingle(planVM)
                            fetchList(ManagePlanVM)
                            navcontroller.navigate(PlanPage.DietPlan.name)
                        } else {
                            CustomSnackBar().CreateSnackBar(
                                message = insertFailed,
                                snackbarHostState = snackbarHostState
                            )
                            Log.d(tag, "insertFailed")
                        }

                    }
                }
            )
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