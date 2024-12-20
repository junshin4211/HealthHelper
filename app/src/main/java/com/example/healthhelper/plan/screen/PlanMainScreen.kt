package com.example.healthhelper.plan.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.healthhelper.plan.PlanPage
import com.example.healthhelper.plan.model.PlanModel
import com.example.healthhelper.plan.ui.CustomText
import com.example.healthhelper.plan.usecase.PlanUCImpl
import com.example.healthhelper.plan.viewmodel.CheckPlanVM
import com.example.healthhelper.plan.viewmodel.ManagePlanVM
import com.example.healthhelper.plan.viewmodel.PlanVM
import com.example.healthhelper.screen.TabViewModel
import com.example.healthhelper.ui.theme.HealthHelperTheme
import kotlinx.coroutines.launch

@Composable
fun PlanMain(context: Context = LocalContext.current,
             navcontroller: NavHostController = rememberNavController(),
             tabVM: TabViewModel = viewModel(),
             planVM: PlanVM,
             checkPlanVM: CheckPlanVM,
             managePlanVM: ManagePlanVM
) {
    val tag = "tag_PlanMain"
    tabVM.setTabVisibility(true)
    val formatter = PlanUCImpl()::dateTimeFormat;
    val planUCImpl = PlanUCImpl()
    val scope = rememberCoroutineScope()
    val scrollstate = rememberScrollState()
    var isplandata by remember { mutableStateOf(false) }
    var iscompletedata by remember { mutableStateOf(false) }
    var myplanimg by remember { mutableIntStateOf(0) }
    var completeplanimg by remember { mutableIntStateOf(0) }

    val myplanlist by managePlanVM.myPlanListState.collectAsState(initial = emptyList())
    val completeplanlist by managePlanVM.completePlanListState.collectAsState(initial = emptyList())
    val myPlan by planVM.myPlanState.collectAsState()
    val completePlan by planVM.completePlanState.collectAsState()
    LaunchedEffect(myplanlist,completeplanlist) {
        if (myplanlist.isNotEmpty()) {
            Log.d(tag, "isAfter ${myPlan.endDateTime}")
            myplanlist.forEach { plan ->
                val date = plan.endDateTime
                if (planUCImpl.isTimeAfterToday(date!!)) {
                    scope.launch {
                        checkPlanVM.updatePlan(plan.userDietPlanId, 1)
                    }
                }
            }
            isplandata = true
        }
        if (completeplanlist.isNotEmpty()) {
            iscompletedata = true
        }
    }

    setimagebyplantype(
        myplan = myPlan,
        completeplan = completePlan,
        onsetplanimage = { myplanimg = it },
        onsetcompleteplanimage = { completeplanimg = it }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollstate)
            .background(color = colorResource(id = R.color.backgroundcolor))
            .padding(bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .padding(top = 5.dp, end = 5.dp)
                .fillMaxWidth()
        ) {
            CreateBar(context, navcontroller)
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = colorResource(R.color.primarycolor), thickness = 2.dp
        )

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

        if (isplandata)
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .clickable {
                        checkPlanVM.setSelectedPlan(myPlan)
                        navcontroller.navigate(PlanPage.CheckPlan.name)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = myplanimg),
                    contentDescription = "myplanimg",
                    modifier = Modifier
                        .width(350.dp)
                        .height(188.dp)
                        .clip(RoundedCornerShape(20.dp)),
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
                            fontSize = 20.sp,
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
                            fontSize = 20.sp,
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
                    planVM.panneelname = PlanPage.MyPlan.name
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
        else
        {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .padding(top = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                CustomText().TextWithDiffColor(
                    setcolor = R.color.primarycolor,
                    text = stringResource(R.string.no_plantext),
                    setsize = 40.sp
                )
            }
        }

        HorizontalDivider(
            color = colorResource(id = R.color.primarycolor),
            thickness = 2.dp,
        )

        if (iscompletedata)
        {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 5.dp ,start = 10.dp, end = 5.dp)
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
                        checkPlanVM.setSelectedPlan(completePlan)
                        navcontroller.navigate(PlanPage.CheckPlan.name)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = completeplanimg),
                    contentDescription = "completeplanimg",
                    modifier = Modifier
                        .width(350.dp)
                        .height(188.dp)
                        .clip(RoundedCornerShape(20.dp)),
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
                            fontSize = 20.sp,
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
                            fontSize = 20.sp,
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
                    planVM.panneelname = PlanPage.CompletedPlan.name
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
        }else{
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .padding(top = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                CustomText().TextWithDiffColor(
                    setcolor = R.color.primarycolor,
                    text = stringResource(R.string.no_plantext),
                    setsize = 40.sp
                )
            }
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
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent,
                thickness = 2.dp
            )
        }
    ) {
        PlanPage.entries.forEachIndexed { index, description ->
            if (index >= 5) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
//                    if (index != 5) {
//                        Divider(
//                            color = colorResource(id = R.color.primarycolor),
//                            modifier = Modifier
//                                .height(40.dp)
//                                .width(2.dp)
//                        )
//                    }

                    Tab(
                        selected = tabindex == index,
                        onClick = {
                            tabindex = index
                            navcontroller.navigate(description.name)
                        },
                        text = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(vertical = 4.dp)
                            ) {
                                when (index) {
                                    5 -> Icon(
                                        painter = painterResource(R.drawable.protein),
                                        contentDescription = description.getPlanTitle(context),
                                        tint = colorResource(id = R.color.black_300),
                                        modifier = Modifier.size(30.dp)
                                    )

                                    6 -> Icon(
                                        painter = painterResource(R.drawable.lowcarb),
                                        contentDescription = description.getPlanTitle(context),
                                        tint = colorResource(id = R.color.black_300),
                                        modifier = Modifier.size(30.dp)
                                    )

                                    7 -> Icon(
                                        painter = painterResource(R.drawable.ketone),
                                        contentDescription = description.getPlanTitle(context),
                                        tint = colorResource(id = R.color.black_300),
                                        modifier = Modifier.size(30.dp)
                                    )

                                    8 -> Icon(
                                        painter = painterResource(R.drawable.mediterra),
                                        contentDescription = description.getPlanTitle(context),
                                        tint = colorResource(id = R.color.black_300),
                                        modifier = Modifier.size(30.dp)
                                    )

                                    9 -> Icon(
                                        painter = painterResource(R.drawable.custom),
                                        contentDescription = description.getPlanTitle(context),
                                        tint = colorResource(id = R.color.black_300),
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                                Text(
                                    text = description.getPlanTitle(context),
                                    color = colorResource(id = R.color.black_300),
                                    fontWeight = FontWeight(600)
                                )
                            }
                        }
                    )
                }
            }
        }
    }

}

fun PlanModel.isEmpty(): Boolean {
    return userDietPlanId == 0
            && startDateTime == null
            && endDateTime == null
            && categoryId == 0
            && categoryName.isBlank()
}

fun setimagebyplantype(
    myplan: PlanModel,
    completeplan: PlanModel,
    onsetplanimage: (Int) -> Unit,
    onsetcompleteplanimage: (Int) -> Unit
){
    when(myplan.categoryId) {
        1 -> onsetplanimage(R.drawable.highproteinimg)
        2 -> onsetplanimage(R.drawable.lowcarbimg)
        3 -> onsetplanimage(R.drawable.ketoneimg)
        4 -> onsetplanimage(R.drawable.mediterraimg)
        5 -> onsetplanimage(R.drawable.customimg)
    }
    when(completeplan.categoryId) {
        1 -> onsetcompleteplanimage(R.drawable.highproteinimg)
        2 -> onsetcompleteplanimage(R.drawable.lowcarbimg)
        3 -> onsetcompleteplanimage(R.drawable.ketoneimg)
        4 -> onsetcompleteplanimage(R.drawable.mediterraimg)
        5 -> onsetplanimage(R.drawable.customimg)
    }
}


@Preview
@Composable
fun PlanMainScreenPreview() {
    HealthHelperTheme {
        //PlanMain(planViewModel = viewModel(), myPlan = emptyList(), completePlan = emptyList())

    }
}