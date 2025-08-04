package com.example.healthhelper.planpage.ui

// Material 3 imports
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Button // M3 Button

// Pull Refresh (與 M3 兼容)

// 其他必要的 imports
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape // 通用 Shape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.HomeWork // 示例圖示
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.planpage.data.Result as ApiResult
import com.example.healthhelper.planpage.data.model.PlanModel
import com.example.healthhelper.planpage.data.remote.DependencyProvider
import com.example.healthhelper.planpage.ui.usecase.FinishState
import com.example.healthhelper.planpage.domain.model.PlanCategory
import com.example.healthhelper.planpage.domain.usecase.filterAndSortPlan
import com.example.healthhelper.planpage.domain.usecase.formatDateString
import com.example.healthhelper.planpage.ui.components.CustomAlertDialog
import com.example.healthhelper.planpage.ui.components.LoadingIndicator
import com.example.healthhelper.planpage.ui.components.Title
import com.example.healthhelper.planpage.ui.viewmodel.AppViewModelFactory
import com.example.healthhelper.planpage.ui.viewmodel.ManagePlanUiState
import com.example.healthhelper.planpage.ui.viewmodel.ManagePlanViewModel
import com.example.healthhelper.ui.theme.HealthHelperTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagePlan(
    navController: NavHostController,
    @StringRes planType: Int
) {
    val context = LocalContext.current

    // 獲取repository,載入viewModel
    val planRepository = DependencyProvider.planRepository
    val viewModelFactory = remember { AppViewModelFactory(planRepository) }
    val viewModel: ManagePlanViewModel = viewModel(factory = viewModelFactory)

    // 取得使用者計畫
    val planListState by viewModel.managePlanState.collectAsStateWithLifecycle()
    // 取得刪除狀態
    val deletePlanState by viewModel.deletePlanState.collectAsStateWithLifecycle()
    // 是否為刪除模式
    var isDeleteMode by remember { mutableStateOf(false) }

    val snackBarHostState = remember { SnackbarHostState() }

    // 監聽刪除狀態
    LaunchedEffect(deletePlanState) {
        when (val state = deletePlanState) {
            is ManagePlanUiState.Success -> {
                // 刪除成功
                snackBarHostState.showSnackbar(
                    message = context.getString(R.string.deleteplansuccess),
                    duration = SnackbarDuration.Short
                )

                // 設定關鍵字通知主頁計畫列表有更新
                navController.previousBackStackEntry?.savedStateHandle?.set("plan_delete", true)
                // 成功後重置 deletePlanState 到初始狀態，防止重複觸發
                viewModel.resetDeletePlanState()
                // 刷新計劃列表
                viewModel.refreshUserPlans()
            }

            is ManagePlanUiState.Error -> {
                // 刪除失敗
                snackBarHostState.showSnackbar(
                    message = state.message,
                    duration = SnackbarDuration.Long // 錯誤信息可以顯示久一點
                )

                // 失敗後也重置 deletePlanState 到初始狀態
                viewModel.resetDeletePlanState() // 你需要在 ViewModel 中添加這個方法
            }

            is ManagePlanUiState.Loading -> {
                // 可以在此處顯示一個全局的刪除進度指示器，如果需要的話
                // 但通常 Snackbar 已經足夠，或者刪除操作很快
            }

            is ManagePlanUiState.Idle -> {
                // 初始狀態或重置後的狀態，不需要做任何事
            }
        }
    }

    HealthHelperTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) },
            topBar = {
                ManageTopBar(
                    navController = navController,
                    isDeleteMode = isDeleteMode,
                    onSetDeleteMode = { isDeleteMode = it }
                )
            }
        ) { paddingValues ->

            //判斷是否取得資料
            when (val state = planListState) {
                is ApiResult.Loading -> {
                    LoadingIndicator()
                }

                is ApiResult.Success -> {
                    val planList = state.data

                    ManagePlanContent(
                        modifier = Modifier.padding(paddingValues),
                        snackBarHostState = snackBarHostState,
                        planType = planType,
                        isDeleteMode = isDeleteMode,
                        onDeleteClick = { planId, finishState ->
                            viewModel.deletePlan(planId, finishState)
                        },
                        planList = planList
                    )
                }

                is ApiResult.Error -> {
                    Log.d("PlanMain", "Error: ${state.message}")
                    ErrorState(message = state.message ?: "Unknown error") {
                        viewModel.refreshUserPlans()
                    }
                }
            }
        }
    }

}

// topbar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageTopBar(
    navController: NavHostController,
    isDeleteMode: Boolean,
    onSetDeleteMode: (Boolean) -> Unit,
) {
    TopAppBar(
        title = {
            Title(
                title = stringResource(R.string.managePlan),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    modifier = Modifier.scale(1.5f),
                    contentDescription = stringResource(id = R.string.back_button)
                )
            }
        },
        actions = {
            IconButton(onClick = { onSetDeleteMode(!isDeleteMode) })
            {
                Icon(
                    painter = painterResource(R.drawable.edit),
                    contentDescription = stringResource(R.string.edit_button),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

// 管理頁面主頁內容
@Composable
fun ManagePlanContent(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState,
    @StringRes planType: Int,
    isDeleteMode: Boolean,
    onDeleteClick: (planId: Int, finishState: Int) -> Unit,
    planList: List<PlanModel> = emptyList()
) {
    val scope = rememberCoroutineScope()

    // 根據主頁點進來的我的計畫或已完成計畫切換不同tab
    var selectedTab: PlanCategory? by remember { mutableStateOf(PlanCategory.fromResId(planType)) }

    // 我的計畫列表與已完成計畫列表
    var myPlanList by remember(planList) {
        mutableStateOf(filterAndSortPlan(planList, isFinish = false))
    }
    var completedPlanList by remember(planList) {
        mutableStateOf(filterAndSortPlan(planList, isFinish = true))
    }

    // 是否顯示刪除
    var showConfirm by remember { mutableStateOf(false) }
    // 紀錄用戶愈刪除計畫
    var userDeletePlan: PlanModel? by remember { mutableStateOf(null) }

    Column(
        modifier = modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selectedTab != null) {
            val currentSelectedTab = selectedTab!!

            // 切換panel
            PlanTabs(selectedTab = currentSelectedTab) { tab ->
                selectedTab = tab
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 計畫列表顯示區
            when (currentSelectedTab) {
                PlanCategory.MyPlans -> {
                    PlanListDisplay(
                        modifier = Modifier.weight(1f),
                        plans = myPlanList,
                        isDeleteMode = isDeleteMode,
                        emptyListMessage = stringResource(R.string.no_plantext),
                        onDeleteClick = { plan ->
                            userDeletePlan = plan
                            showConfirm = true
                        },
                        onPlanClick = { /* TODO: Handle plan click, e.g., navigate to plan details */ }
                    )
                }

                PlanCategory.CompletedPlans -> {
                    PlanListDisplay(
                        modifier = Modifier.weight(1f),
                        plans = completedPlanList,
                        isDeleteMode = isDeleteMode,
                        emptyListMessage = stringResource(R.string.no_plantext),
                        onDeleteClick = {plan ->
                            userDeletePlan = plan
                            showConfirm = true
                        },
                        onPlanClick = { /* TODO: Handle plan click, e.g., navigate to plan details */ }
                    )
                }
            }
        } else {
            // 計畫列表為空
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.no_plantext),
                    fontSize = 50.sp,
                    color = Color.LightGray
                )
            }
        }

        // 顯示刪除計畫Dialog
        if (showConfirm) {
            CustomAlertDialog(
                onDismissRequest = { showConfirm = false },
                title = {
                    Text(
                        text = stringResource(R.string.delete_plan_alert_title),
                        color = Color.Red
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.delete_plan_alert_text),
                        color = Color.Red
                    )
                },
                onConfirm = {

                    if (userDeletePlan == null) {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                "未知計畫錯誤，請重試",
                                duration = SnackbarDuration.Short
                            )
                        }
                        showConfirm = false
                    } else {
                        userDeletePlan?.let { plan ->
                            onDeleteClick(plan.userDietPlanId, FinishState.Delete.state)
                        }
                        showConfirm = false
                    }
                }
            )
        }

    }
}

// 切換panel
@Composable
fun PlanTabs(
    selectedTab: PlanCategory,
    onSetSelectedTab: (PlanCategory) -> Unit
) {
    val tabs = PlanCategory.entries

    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        modifier = Modifier.clip(RoundedCornerShape(20.dp)),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.primary, // M3
        indicator = { Spacer(modifier = Modifier.height(0.dp)) }
    ) {
        tabs.forEach { tab ->
            Tab(
                selected = tab == selectedTab,
                onClick = { onSetSelectedTab(tab) },
                text = {
                    Text(
                        text = when (tab) {
                            PlanCategory.MyPlans -> stringResource(id = R.string.myPlan)
                            PlanCategory.CompletedPlans -> stringResource(id = R.string.completedPlan)
                        },
                        fontWeight = if (tab == selectedTab) FontWeight.Bold else FontWeight.Normal,
                    )
                },
                modifier = Modifier.background(
                    if (tab == selectedTab) Color(0xFFFFA726) // 選中時的橙色背景
                    else Color(0xFFF0F0F0) // 未選中時的淺灰色背景 (示例)
                ),
                selectedContentColor = MaterialTheme.colorScheme.onSurface,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// 計畫列表
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PlanListDisplay(
    modifier: Modifier = Modifier,
    plans: List<PlanModel>,
    isDeleteMode: Boolean,
    emptyListMessage: String,
    onDeleteClick: (plan: PlanModel) -> Unit,
    onPlanClick: (plan: PlanModel) -> Unit
) {
    if (plans.isEmpty()) {
        EmptyState(message = emptyListMessage)
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                items = plans,
                key = { plan -> plan.userDietPlanId }
            ) { plan ->

                PlanItemCard(
                    plan = plan,
                    isDeleteMode = isDeleteMode,
                    onDeleteClick = { onDeleteClick(plan) },
                    onPlanClick = { onPlanClick(plan) }
                )
            }
        }
    }
}

// 單項計畫內容
@Composable
fun PlanItemCard(
    plan: PlanModel,
    isDeleteMode: Boolean,
    onDeleteClick: () -> Unit,
    onPlanClick: () -> Unit
) {

    val planName = plan.categoryName

    val planStartDate = formatDateString(plan.startDateTime) ?: "2999/99/99"

    val planEndDate = formatDateString(plan.endDateTime) ?: "2999/99/99"

    Card(
        modifier = Modifier
            .width(300.dp)
            .height(80.dp)
            .padding(8.dp)
            .clickable(onClick = onPlanClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.HomeWork,
                contentDescription = "Plan type icon",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                // 計畫名稱
                Text(
                    text = planName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))
                // 日期
                Text(
                    text = "$planStartDate ~ $planEndDate",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            // 切換圖標
            if (isDeleteMode) {
                Icon(
                    painterResource(R.drawable.delete),
                    contentDescription = "Delete plan",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.clickable {
                        onDeleteClick()
                    }
                )
            } else {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "View plan details",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}


@Composable
fun EmptyState(message: String, modifier: Modifier = Modifier) {
    Box( /* ... */) {
        Text(message, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) // M3
    }
}

@Composable
fun ErrorState(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Error: $message", color = MaterialTheme.colorScheme.error) // M3
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) { // M3 Button
            Text("Retry")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManagePlanPreview() {
    HealthHelperTheme {
        ManagePlan(navController = rememberNavController(), planType = R.string.myPlan)
    }
}