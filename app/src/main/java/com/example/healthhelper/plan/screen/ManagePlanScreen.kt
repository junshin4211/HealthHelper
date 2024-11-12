package com.example.healthhelper.plan.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.plan.PlanPage
import com.example.healthhelper.plan.model.PlanModel
import com.example.healthhelper.plan.ui.CreateToggleButton
import com.example.healthhelper.plan.ui.CustomDialog
import com.example.healthhelper.plan.ui.CustomIcon
import com.example.healthhelper.plan.ui.CustomList
import com.example.healthhelper.plan.ui.CustomSnackBar
import com.example.healthhelper.plan.usecase.PlanUCImpl
import com.example.healthhelper.plan.viewmodel.CheckPlanVM
import com.example.healthhelper.plan.viewmodel.ManagePlanVM
import com.example.healthhelper.plan.viewmodel.PlanVM
import com.example.healthhelper.screen.TabViewModel
import com.example.healthhelper.ui.theme.HealthHelperTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ManagePlan(
    planVM: PlanVM,
    managePlanVM: ManagePlanVM,
    showdelete: Boolean,
    tabVM: TabViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    checkPlanVM: CheckPlanVM,
    navcontroller: NavHostController
) {
    tabVM.setTabVisibility(false)
    val context = LocalContext.current
    val tag = "tag_ManagePlan"
    val planUCImpl = remember { PlanUCImpl() }

    var activatepannel by remember { mutableStateOf(planVM.panneelname) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedPlan by remember { mutableStateOf(PlanModel()) }

    val deletePlanSuccess = stringResource(R.string.deleteplansuccess)
    val deletePlanFailed = stringResource(R.string.deleteplanfailed)

    planUCImpl.fetchList(managePlanVM)

    //get plan list
    val myPlanList by managePlanVM.myPlanListState.collectAsState(initial = emptyList())
    Log.d(tag, "get list $myPlanList")
    val completePlanList by managePlanVM.completePlanListState.collectAsState(initial = emptyList())
    Log.d(tag, "get list $completePlanList")

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
                        planVM.panneelname = PlanPage.CompletedPlan.name
                        activatepannel = planVM.panneelname
                    },
                    leftText = PlanPage.MyPlan.getPlanTitle(context),
                    rightText = PlanPage.CompletedPlan.getPlanTitle(context)
                )

                CustomList().ItemList(
                    inputList = myPlanList,
                    onItemClick = {
                        checkPlanVM.setSelectedPlan(it)
                        navcontroller.navigate(PlanPage.CheckPlan.name)
                    },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.protein),
                            contentDescription = "planType",
                            modifier = Modifier
                                .padding(16.dp)
                                .scale(1.5f)
                        )
                    },
                    trialingIcon = {
                        when (showdelete) {
                            true -> {
                                CustomIcon().CreateDelete(
                                    size = 1.0f,
                                    onDeleteClick = {
                                        selectedPlan = it
                                        showDeleteDialog = true
                                    }
                                )
                            }

                            else -> {
                                CustomIcon().CreateArrow(
                                    isRight = true,
                                    size = 3.0f,
                                    color = R.color.black_300
                                )
                            }
                        }
                    }
                )
            }

            PlanPage.CompletedPlan.name -> {
                CreateToggleButton(
                    onLeftClick = {
                        planVM.panneelname = PlanPage.MyPlan.name
                        activatepannel = planVM.panneelname
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

                CustomList().ItemList(
                    inputList = completePlanList,
                    onItemClick = {
                        checkPlanVM.setSelectedPlan(it)
                        navcontroller.navigate(PlanPage.CheckPlan.name)
                    },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.protein),
                            contentDescription = "planType",
                            modifier = Modifier
                                .padding(16.dp)
                                .scale(1.5f)
                        )
                    },
                    trialingIcon = {
                        when (showdelete) {
                            true -> {
                                CustomIcon().CreateDelete(
                                    size = 1.0f,
                                    onDeleteClick = {
                                        selectedPlan = it
                                        showDeleteDialog = true
                                    }
                                )
                            }

                            else -> {
                                CustomIcon().CreateArrow(
                                    isRight = true,
                                    size = 3.0f,
                                    color = R.color.black_300
                                )
                            }
                        }
                    }
                )
            }
        }
        if (showDeleteDialog)
        {
            CustomDialog().DeleteDataDialog(
                title = R.string.deleteplantitle,
                onConfirm = {
                    scope.launch {
                        val isSuccess = managePlanVM.deletePlan(
                            plan = selectedPlan,
                            userDietPlanID = selectedPlan.userDietPlanId,
                            finishState = 0
                        )
                        if (isSuccess) {
                            CustomSnackBar().CreateSnackBar(
                                message = deletePlanSuccess,
                                snackbarHostState = snackbarHostState
                            )
                            Log.d(tag, deletePlanSuccess)
                        } else {
                            CustomSnackBar().CreateSnackBar(
                                message = deletePlanFailed,
                                snackbarHostState = snackbarHostState
                            )
                            Log.d(tag, deletePlanFailed)
                        }
                    }
                    showDeleteDialog = false
                },
                onDismiss = {
                    showDeleteDialog = false
                }
            )
        }
    }

}


@Preview
@Composable
fun ManageScreenPreview() {
    HealthHelperTheme {
        //ManagePlan()

    }
}