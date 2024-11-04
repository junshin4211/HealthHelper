package com.example.healthhelper.plan.screen

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.plan.PlanPage
import com.example.healthhelper.plan.ui.CustomText
import com.example.healthhelper.plan.viewmodel.EditPlanVM
import com.example.healthhelper.plan.viewmodel.ManagePlanVM
import com.example.healthhelper.plan.viewmodel.PlanVM
import com.example.healthhelper.screen.TabViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Plan(
    navController: NavHostController = rememberNavController(),
    tabViewModel: TabViewModel = viewModel(),
    planVM: PlanVM = viewModel(),
    managePlanVM: ManagePlanVM = viewModel(),
    EditPlanVM: EditPlanVM = viewModel()
) {
    val tag = "tag_PlanNav"
    val context = LocalContext.current
    val backStackEntry by navController.currentBackStackEntryAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var showdeleteicon by remember { mutableStateOf(planVM.showdelete) }
    val currentScreen = PlanPage.valueOf(
        backStackEntry?.destination?.route ?: PlanPage.DietPlan.name
    )

    tabViewModel.setTabVisibility(true)
    Scaffold(
        topBar = {
            PlanAppBar(
                currentScreen = currentScreen,
                context = context,
                cannavigateback = navController.previousBackStackEntry != null,
                navController = navController,
                scrollBehavior = scrollBehavior,
                onShowDelete = {
                    showdeleteicon = planVM.showdelete
                },
                planViewModel = planVM
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = PlanPage.DietPlan.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = PlanPage.DietPlan.name) {
                PlanMain(
                    navcontroller = navController,
                    tabViewModel = tabViewModel,
                    planViewModel = planVM
                )
            }
            composable(route = PlanPage.HighProtein.name) {
                EditPlan(
                    PlanPage.HighProtein,
                    navcontroller = navController,
                    tabViewModel,
                    EditPlanVM
                )
            }
            composable(route = PlanPage.LowCarb.name) {
                EditPlan(
                    PlanPage.LowCarb,
                    navcontroller = navController,
                    tabViewModel,
                    EditPlanVM
                )
            }
            composable(route = PlanPage.Ketone.name) {
                EditPlan(
                    PlanPage.Ketone,
                    navcontroller = navController,
                    tabViewModel,
                    EditPlanVM
                )
            }
            composable(route = PlanPage.Mediterra.name) {
                EditPlan(
                    PlanPage.Mediterra,
                    navcontroller = navController,
                    tabViewModel,
                    EditPlanVM
                )
            }
            composable(route = PlanPage.Custom.name) {
                CustomEditPlan(
                    PlanPage.Custom.getPlanTitle(context),
                    navcontroller = navController,
                    tabViewModel,
                    EditPlanVM
                )
            }
            composable(route = PlanPage.ManagePlan.name) {
                ManagePlan(
                    planVM = planVM,
                    managePlanVM = managePlanVM,
                    showdeleteicon
                )
            }
            composable(route = PlanPage.CheckPlan.name)
            {
                CheckPlan(
                    navcontroller = navController,
                    tabViewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanAppBar(
    currentScreen: PlanPage,
    cannavigateback: Boolean,
    context: Context,
    onShowDelete: () -> Unit,
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    planViewModel: PlanVM
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.primarycolor),
            titleContentColor = colorResource(id = R.color.black)
        ),
        title = {
            val titleText =
                when (currentScreen) {
                    PlanPage.ManagePlan -> {
                        PlanPage.ManagePlan.getPlanTitle(context) // 自定義標題
                    }

                    PlanPage.DietPlan -> {
                        PlanPage.DietPlan.getPlanTitle(context)
                    }

                    else -> {
                        currentScreen.getPlanTitle(context) + "飲食計畫" // 其他頁面的標題
                    }
                }
            CustomText().TextWithDiffColor(
                text = titleText,
                setsize = 30.sp
            )
        },
        navigationIcon = {
            if (cannavigateback) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow),
                        contentDescription = "arrow",
                        modifier = Modifier
                            .scale(2.2f)
                            .graphicsLayer(scaleX = -1f),
                    )
                }
            }

        },
        actions = {
            if (currentScreen == PlanPage.ManagePlan) {
                IconButton(
                    onClick = {
                        planViewModel.showdelete = !planViewModel.showdelete
                        onShowDelete()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.edit),
                        contentDescription = "edit",
                        modifier = Modifier
                            .scale(1.0f),
                        tint = colorResource(id = R.color.black)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Preview
@Composable
fun planNavPreview() {
    Plan()
}
