package com.example.healthhelper.plan.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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
import com.example.healthhelper.plan.Bar
import com.example.healthhelper.plan.PlanPage
import com.example.healthhelper.screen.TabViewModel
import com.example.healthhelper.ui.theme.HealthHelperTheme

@Composable
fun PlanMain(context: Context = LocalContext.current,
             navcontroller: NavHostController = rememberNavController(),
             tabViewModel: TabViewModel = viewModel()
) {
    tabViewModel.setTabVisibility(true)
    var planpannel by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.backgroundcolor)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .padding(start = 10.dp, bottom = 10.dp)
        ) {
            Text(
                text = PlanPage.DietPlan.getPlanTitle(context),
                style = TextStyle(
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = colorResource(id = R.color.black),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.2.sp
                )
            )
        }

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
                .padding(top = 5.dp),
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
                    text = "${Bar.HighProtein.getbar(context)}飲食",

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
                    text = "2024/10/18~2024/10/31",

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
            TextButton(
                onClick = {
                    planpannel = PlanPage.MyPlan.name
                    navcontroller.navigate("${PlanPage.ManagePlan.name}/$planpannel")
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
                .padding(top = 5.dp),
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
                    text = "${Bar.HighProtein.getbar(context)}飲食",

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
                    text = "2024/10/18~2024/10/31",

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
            TextButton(
                onClick = {
                    planpannel = PlanPage.CompletedPlan.name
                    navcontroller.navigate("${PlanPage.ManagePlan.name}/$planpannel")
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
}


@Composable
fun CreateBar(context: Context,navcontroller: NavHostController) {
    var tabindex by remember { mutableIntStateOf(0) }
    TabRow(
        selectedTabIndex = tabindex,
        containerColor = colorResource(id = R.color.backgroundcolor),
        divider = {
            HorizontalDivider(
                color = colorResource(R.color.darkgray), thickness = 2.dp
            )
        }
    ) {
        Bar.entries.forEachIndexed { index, description ->
            Tab(
                text = {
                    Text(
                        text = description.getbar(context),
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
                        0 -> Icon(
                            painter = painterResource(R.drawable.protein),
                            contentDescription = description.getbar(context),
                            tint = colorResource(id = R.color.primarycolor)
                        )

                        1 -> Icon(
                            painter = painterResource(R.drawable.lowcarb),
                            contentDescription = description.getbar(context),
                            tint = colorResource(id = R.color.primarycolor)
                        )

                        2 -> Icon(
                            painter = painterResource(R.drawable.ketone),
                            contentDescription = description.getbar(context),
                            tint = colorResource(id = R.color.primarycolor)
                        )

                        3 -> Icon(
                            painter = painterResource(R.drawable.mediterra),
                            contentDescription = description.getbar(context),
                            tint = colorResource(id = R.color.primarycolor)
                        )

                        4 -> Icon(
                            painter = painterResource(R.drawable.custom),
                            contentDescription = description.getbar(context),
                            tint = colorResource(id = R.color.primarycolor)
                        )
                    }
                }
            )
        }
    }


}

@Preview
@Composable
fun PlanMainScreenPreview() {
    HealthHelperTheme {
        PlanMain()

    }
}