package com.example.healthhelper.plan.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.plan.PlanPage
import com.example.healthhelper.plan.ui.CustomText
import com.example.healthhelper.plan.ui.CreateToggleButton
import com.example.healthhelper.ui.theme.HealthHelperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagePlan(
    navcontroller: NavHostController = rememberNavController(),
    planpannel: String
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var activatepannel by remember { mutableStateOf(planpannel) }
    var activatecolor by remember { mutableIntStateOf(R.color.primarycolor) }
    var activatetextcolor by remember { mutableIntStateOf(R.color.white) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.primarycolor),
                    titleContentColor = colorResource(id = R.color.black)
                ),
                title = {
                    CustomText().TextWithDiffColor(
                        text = PlanPage.ManagePlan.getPlanTitle(context),
                        setsize = 30.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navcontroller.popBackStack() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow),
                            contentDescription = "arrow",
                            modifier = Modifier
                                .scale(2.2f)
                                .graphicsLayer(scaleX = -1f),
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.edit),
                            contentDescription = "arrow",
                            modifier = Modifier
                                .scale(1.0f),
                            tint = colorResource(id = R.color.black)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.backgroundcolor))
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when(activatepannel){
                PlanPage.MyPlan.name ->{
                    CreateToggleButton(
                        onLeftClick = {},
                        onRightClick = {
                            activatepannel = PlanPage.CompletedPlan.name
                        },
                        leftText = PlanPage.MyPlan.getPlanTitle(context),
                        rightText = PlanPage.CompletedPlan.getPlanTitle(context)
                    )
                }
                PlanPage.CompletedPlan.name ->{
                    CreateToggleButton(
                        onLeftClick = {
                            activatepannel = PlanPage.MyPlan.name
                        },
                        onRightClick = {
                        },
                        leftButtonColor = R.color.light_gray,
                        rightButtonColor = R.color.primarycolor,
                        leftTextColor = R.color.darkgray,
                        rightTextColor = R.color.white,
                        leftText = PlanPage.MyPlan.getPlanTitle(context),
                        rightText = PlanPage.CompletedPlan.getPlanTitle(context)
                    )
                }
            }

        }

    }
}


@Preview
@Composable
fun ManageScreenPreview() {
    HealthHelperTheme {
        ManagePlan(planpannel = PlanPage.MyPlan.name)

    }
}