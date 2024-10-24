package com.example.healthhelper.plan.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
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
import com.example.healthhelper.plan.ui.CreateDatePicker
import com.example.healthhelper.plan.ui.CreateDropDownMenu
import com.example.healthhelper.plan.ui.CustomButton
import com.example.healthhelper.plan.ui.CustomText
import com.example.healthhelper.plan.ui.CustomTextField
import com.example.healthhelper.screen.TabViewModel
import com.example.healthhelper.ui.theme.HealthHelperTheme

@Composable
fun EditPlan(planname:String,
             navcontroller: NavHostController = rememberNavController(),
             tabViewModel: TabViewModel = viewModel()
){
    val scrollstate = rememberScrollState()
    //tabViewModel.setTabVisibility(false)
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }
    var carbgram by remember { mutableFloatStateOf(0.0f) }
    var proteingram by remember { mutableFloatStateOf(0.0f) }
    var fatgram by remember { mutableFloatStateOf(0.0f) }
    var carbpercent by remember { mutableIntStateOf(0) }
    var proteinpercent by remember { mutableIntStateOf(0) }
    var fatpercent by remember { mutableIntStateOf(0) }
    var calorie by remember { mutableFloatStateOf(0f) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.backgroundcolor))
                .verticalScroll(scrollstate)
                .padding(top = 10.dp)
        ){
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 10.dp)
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

                CreateDatePicker(dateSelected = selectedDate, dateStart = false)
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Column (
                    modifier = Modifier.padding(start = 30.dp, end = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start
                ){
                    for(i in 0..2){
                        when(i){
                            0 -> CustomText().TextWithDiffColor(R.color.red01,
                                "${stringResource(R.string.carb)} $carbgram 克",15.sp)
                            1 -> CustomText().TextWithDiffColor(R.color.sky_blue,
                                "${stringResource(R.string.protein)} $proteingram 克",15.sp)
                            2 -> CustomText().TextWithDiffColor(R.color.light_green,
                                "${stringResource(R.string.fat)} $fatgram 克",15.sp)
                        }
                    }
                }

                Column (
                ){
                    Image(
                        painter = painterResource(R.drawable.myplanimg),
                        contentDescription = "",
                        modifier = Modifier
                    )
                }
            }

            Column (
                modifier = Modifier.padding(top = 10.dp, start = 13.dp, end = 10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CustomText().TextWithDiffColor(R.color.primarycolor,stringResource(R.string.dailygoal),26.sp)
                }

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically

                ){
                    CustomText().TextWithDiffColor(R.color.black,stringResource(R.string.calories),22.sp)
                    CustomTextField().TextFieldWithBorder(
                        value = calorie,
                        onValueChange = {newvalue -> calorie = newvalue },
                        convertFromString = {it.toFloatOrNull()},
                        label = stringResource(R.string.examcalorie),
                        width = 130.dp
                    )
                    CustomText().TextWithDiffColor(R.color.black,stringResource(R.string.cals),16.sp)
                }

                CreateDesciption(
                    stringResource(R.string.carb),
                    carbpercent,
                    carbgram,
                    stringResource(R.string.carbdescripttitle),
                    stringResource(R.string.carbdescription)
                )

                HorizontalDivider(
                    thickness = 2.dp,
                    color = colorResource(R.color.very_light_gray)
                )

                CreateDesciption(
                    stringResource(R.string.protein),
                    proteinpercent,
                    proteingram,
                    stringResource(R.string.proteindescripttitle),
                    stringResource(R.string.proteindescription)
                )

                HorizontalDivider(
                    thickness = 2.dp,
                    color = colorResource(R.color.very_light_gray)
                )

                CreateDesciption(
                    stringResource(R.string.fat),
                    fatpercent,fatgram,
                    stringResource(R.string.fatdescripttitle),
                    stringResource(R.string.fatdescription)
                )

                HorizontalDivider(
                    thickness = 2.dp,
                    color = colorResource(R.color.very_light_gray)
                )

            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp, alignment = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ){
                CustomButton().CreateButton(
                    text = stringResource(R.string.save),
                    color = R.color.primarycolor,
                    onClick = {  }
                )
            }

        }

    }

@Composable
fun CreateDesciption(
    type:String,
    carbgram:Int,
    carbpercent:Float,
    descriptiontitle:String,
    description:String){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically

        ){
            CustomText().TextWithDiffColor(text = type, setsize = 25.sp)
            CustomText().TextWithDiffColor(text = "$carbpercent%(${carbgram}克)", setsize = 20.sp)
        }
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically

        ){
            CustomText().TextWithDiffColor(text = descriptiontitle, setsize = 20.sp)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically

        ){
            CustomText().TextWithDiffColor(text = description, setsize = 14.sp)
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