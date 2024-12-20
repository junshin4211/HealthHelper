package com.example.healthhelper.plan.screen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.example.healthhelper.plan.viewmodel.CheckPlanVM
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
    EditPlanVM: EditPlanVM = viewModel(),
    checkPlanVM: CheckPlanVM = viewModel()
) {
    val tag = "tag_PlanNav"
    val context = LocalContext.current
    val backStackEntry by navController.currentBackStackEntryAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
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
                PlanVM = planVM,
                checkPlanVM = checkPlanVM
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            HorizontalDivider(
                color = colorResource(id = R.color.primarycolor),
                thickness = 2.dp
            )

            NavHost(
                navController = navController,
                startDestination = PlanPage.DietPlan.name,
                modifier = Modifier
                    .fillMaxSize()
//                    .padding(innerPadding)
            ) {
                composable(route = PlanPage.DietPlan.name) {
                    PlanMain(
                        navcontroller = navController,
                        tabVM = tabViewModel,
                        planVM = planVM,
                        checkPlanVM = checkPlanVM,
                        managePlanVM = managePlanVM
                    )
                }
                composable(route = PlanPage.HighProtein.name) {
                    EditPlan(
                        planname = PlanPage.HighProtein,
                        tabViewModel = tabViewModel,
                        EditPlanVM = EditPlanVM,
                        scope = scope,
                        snackbarHostState = snackbarHostState,
                        navcontroller = navController,
                    )
                }
                composable(route = PlanPage.LowCarb.name) {
                    EditPlan(
                        planname = PlanPage.LowCarb,
                        tabViewModel = tabViewModel,
                        EditPlanVM = EditPlanVM,
                        scope = scope,
                        snackbarHostState = snackbarHostState,
                        navcontroller = navController,
                    )
                }
                composable(route = PlanPage.Ketone.name) {
                    EditPlan(
                        planname = PlanPage.Ketone,
                        tabViewModel = tabViewModel,
                        EditPlanVM = EditPlanVM,
                        scope = scope,
                        snackbarHostState = snackbarHostState,
                        navcontroller = navController,
                    )
                }
                composable(route = PlanPage.Mediterra.name) {
                    EditPlan(
                        planname = PlanPage.Mediterra,
                        tabViewModel = tabViewModel,
                        EditPlanVM = EditPlanVM,
                        scope = scope,
                        snackbarHostState = snackbarHostState,
                        navcontroller = navController,
                    )
                }
                composable(route = PlanPage.Custom.name) {
                    CustomEditPlan(
                        planname = PlanPage.Custom,
                        tabVM = tabViewModel,
                        editPlanVM = EditPlanVM,
                        scope = scope,
                        snackBarHostState = snackbarHostState,
                        navController = navController,
                    )
                }
                composable(route = PlanPage.ManagePlan.name) {
                    ManagePlan(
                        planVM = planVM,
                        managePlanVM = managePlanVM,
                        showdelete = showdeleteicon,
                        tabVM = tabViewModel,
                        scope = scope,
                        snackbarHostState = snackbarHostState,
                        checkPlanVM = checkPlanVM,
                        navcontroller = navController
                    )
                }
                composable(route = PlanPage.CheckPlan.name)
                {
                    CheckPlan(
                        tabVM = tabViewModel,
                        planVM = planVM,
                        managePlanVM = managePlanVM,
                        checkVM = checkPlanVM
                    )
                }
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
    PlanVM: PlanVM,
    checkPlanVM: CheckPlanVM
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.backgroundcolor),
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
                    PlanPage.CheckPlan -> {
                        checkPlanVM.getCateGoryName()
                    }
                    else -> {
                        currentScreen.getPlanTitle(context) + "飲食計畫" // 其他頁面的標題
                    }
                }
            CustomText().TextWithDiffColor(
                setcolor = R.color.primarycolor,
                text = titleText,
                setsize = 24.sp
            )
        },
        navigationIcon = {
            if (cannavigateback) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                        checkPlanVM.clear()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow),
                        contentDescription = "arrow",
                        modifier = Modifier
                            .scale(2.2f)
                            .graphicsLayer(scaleX = -1f),
                        tint = colorResource(R.color.primarycolor)
                    )
                }
            }

        },
        actions = {
            if (currentScreen == PlanPage.ManagePlan) {
                IconButton(
                    onClick = {
                        PlanVM.showdelete = !PlanVM.showdelete
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
