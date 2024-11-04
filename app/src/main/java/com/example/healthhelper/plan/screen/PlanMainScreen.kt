package com.example.healthhelper.plan.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
import com.example.healthhelper.plan.PlanPage
import com.example.healthhelper.plan.usecase.PlanUCImpl
import com.example.healthhelper.plan.viewmodel.PlanVM
import com.example.healthhelper.screen.TabViewModel
import com.example.healthhelper.ui.theme.HealthHelperTheme

@Composable
fun PlanMain(context: Context = LocalContext.current,
             navcontroller: NavHostController = rememberNavController(),
             tabViewModel: TabViewModel = viewModel(),
             planViewModel: PlanVM,
) {
    val tag = "tag_PlanMain"
    tabViewModel.setTabVisibility(true)
    val formatter = PlanUCImpl()::dateTimeFormat;

    val myPlan by planViewModel.myPlanState.collectAsState()
    val completePlan by planViewModel.completePlanState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.backgroundcolor)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalDivider(
            color = colorResource(id = R.color.darkgray),
            thickness = 2.dp
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .padding(top = 5.dp, end = 5.dp)
                .fillMaxWidth()
        ) {
            CreateBar(context, navcontroller)
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 5.dp, start = 10.dp, end = 5.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = PlanPage.MyPlan.getPlanTitle(context),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = colorResource(R.color.black),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.2.sp
                )
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .clickable {
                    navcontroller.navigate(PlanPage.CheckPlan.name)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.myplanimg),
                contentDescription = "myplanimg",
                modifier = Modifier
                    .width(350.dp)
                    .height(188.dp),
                contentScale = ContentScale.FillBounds
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    //放SQL最新計畫的名稱
                    text = myPlan.categoryName,

                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600),
                        color = colorResource(R.color.primarycolor),
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        //.padding(start = 15.dp)
                )


                Text(
                    //放SQL最新計畫的時間
                    text = "${formatter(myPlan.startDateTime)}~${formatter(myPlan.endDateTime)}",

                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600),
                        color = colorResource(R.color.primarycolor),
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }


        }

        TextButton(
            onClick = {
                planViewModel.panneelname = PlanPage.MyPlan.name
                navcontroller.navigate(PlanPage.ManagePlan.name)
            },
            modifier = Modifier
                .height(40.dp)
                .align(Alignment.End)
        ) {
            Text(
                text = "more‧‧‧",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = colorResource(R.color.black),
                    letterSpacing = 0.2.sp
                )
            )
        }

        HorizontalDivider(
            color = colorResource(id = R.color.darkgray),
            thickness = 2.dp
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 5.dp, start = 10.dp, end = 5.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = PlanPage.CompletedPlan.getPlanTitle(context),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = colorResource(R.color.black),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.2.sp
                )
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .clickable {
                    navcontroller.navigate(PlanPage.CheckPlan.name)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.myplanimg),
                contentDescription = "myplanimg",
                modifier = Modifier
                    .width(350.dp)
                    .height(188.dp),
                contentScale = ContentScale.FillBounds
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    //放SQL最近已完成計畫的名稱
                    text = completePlan.categoryName,

                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600),
                        color = colorResource(R.color.primarycolor),
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                    //.padding(start = 15.dp)
                )

                Text(
                    //放SQL最新計畫的時間
                    text = "${formatter(completePlan.startDateTime)}~${formatter(completePlan.endDateTime)}",

                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600),
                        color = colorResource(R.color.primarycolor),
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }

        }

        TextButton(
            onClick = {
                planViewModel.panneelname = PlanPage.CompletedPlan.name
                navcontroller.navigate(PlanPage.ManagePlan.name)
            },
            modifier = Modifier
                .height(40.dp)
                .align(Alignment.End)
        ) {
            Text(
                text = "more‧‧‧",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = colorResource(R.color.black),
                    letterSpacing = 0.2.sp
                )
            )
        }
    }
}


@Composable
fun CreateBar(context: Context,navcontroller: NavHostController) {
    var tabindex by remember { mutableIntStateOf(5) }
    TabRow(
        selectedTabIndex = tabindex,
        containerColor = colorResource(id = R.color.backgroundcolor),
        divider = {
            HorizontalDivider(
                color = colorResource(R.color.darkgray), thickness = 2.dp
            )
        }
    ) {
        PlanPage.entries.forEachIndexed { index, description ->
            if (index >= 5)
            {
                Tab(
                    text = {
                        Text(
                            text = description.getPlanTitle(context),
                            color = colorResource(id = R.color.primarycolor),
                            fontWeight = FontWeight(600)
                        )
                    },
                    selected = tabindex == index,
                    onClick = {
                        tabindex = index
                        navcontroller.navigate(description.name)
                    },
                    icon = {
                        when (index) {
                            5 -> Icon(
                                painter = painterResource(R.drawable.protein),
                                contentDescription = description.getPlanTitle(context),
                                tint = colorResource(id = R.color.primarycolor)
                            )

                            6 -> Icon(
                                painter = painterResource(R.drawable.lowcarb),
                                contentDescription = description.getPlanTitle(context),
                                tint = colorResource(id = R.color.primarycolor)
                            )

                            7 -> Icon(
                                painter = painterResource(R.drawable.ketone),
                                contentDescription = description.getPlanTitle(context),
                                tint = colorResource(id = R.color.primarycolor)
                            )

                            8 -> Icon(
                                painter = painterResource(R.drawable.mediterra),
                                contentDescription = description.getPlanTitle(context),
                                tint = colorResource(id = R.color.primarycolor)
                            )

                            9 -> Icon(
                                painter = painterResource(R.drawable.custom),
                                contentDescription = description.getPlanTitle(context),
                                tint = colorResource(id = R.color.primarycolor)
                            )
                        }
                    }
                )
            }

        }
    }
}


@Preview
@Composable
fun PlanMainScreenPreview() {
    HealthHelperTheme {
        //PlanMain(planViewModel = viewModel(), myPlan = emptyList(), completePlan = emptyList())

    }
}