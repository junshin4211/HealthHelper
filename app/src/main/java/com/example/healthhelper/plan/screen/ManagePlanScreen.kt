package com.example.healthhelper.plan.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.plan.PlanPage
import com.example.healthhelper.plan.ui.CreateToggleButton
import com.example.healthhelper.plan.ui.CustomIcon
import com.example.healthhelper.plan.ui.CustomList
import com.example.healthhelper.plan.viewmodel.ManagePlanVM
import com.example.healthhelper.plan.viewmodel.PlanVM
import com.example.healthhelper.ui.theme.HealthHelperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagePlan(
    navcontroller: NavHostController = rememberNavController(),
    planViewModel: PlanVM,
    onShowDelete: @Composable () -> Unit,
) {
    val context = LocalContext.current
    var activatepannel by remember { mutableStateOf(planViewModel.panneelname) }
    val plan by planViewModel.planState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.backgroundcolor)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (activatepannel) {
            PlanPage.MyPlan.name -> {
                CreateToggleButton(
                    onLeftClick = {},
                    onRightClick = {
                        planViewModel.panneelname = PlanPage.CompletedPlan.name
                        activatepannel = planViewModel.panneelname
                    },
                    leftText = PlanPage.MyPlan.getPlanTitle(context),
                    rightText = PlanPage.CompletedPlan.getPlanTitle(context)
                )
            }

            PlanPage.CompletedPlan.name -> {
                CreateToggleButton(
                    onLeftClick = {
                        planViewModel.panneelname = PlanPage.MyPlan.name
                        activatepannel = planViewModel.panneelname
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

        CustomList().PlanList(
            plans = plan,
            onItemClick = { },
            leadingicon = {
                Image(
                    painter = painterResource(id = R.drawable.protein),
                    contentDescription = "plantype",
                    modifier = Modifier.padding(16.dp)
                )
            },
            trialingicon = {
                onShowDelete()
            }
        )
    }

}


@Preview
@Composable
fun ManageScreenPreview() {
    HealthHelperTheme {
        //ManagePlan()

    }
}