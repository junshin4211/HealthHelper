package com.example.healthhelper.planpage.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.isEmpty
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.planpage.data.model.PlanModel
import com.example.healthhelper.planpage.data.Result as ApiResult
import com.example.healthhelper.planpage.data.remote.DependencyProvider
import com.example.healthhelper.planpage.domain.usecase.transformDate
import com.example.healthhelper.planpage.ui.components.ErrorState
import com.example.healthhelper.planpage.ui.components.LoadingIndicator
import com.example.healthhelper.planpage.ui.components.Title
import com.example.healthhelper.planpage.ui.viewmodel.AppViewModelFactory
import com.example.healthhelper.planpage.ui.viewmodel.PlanDetailViewModel
import com.example.healthhelper.ui.theme.HealthHelperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanDetail(
    userDietPlanId: Int,
    categoryName: String,
    navController: NavHostController
) {

    val planRepository = DependencyProvider.planRepository
    val viewModelFactory = remember { AppViewModelFactory(planRepository) }
    val viewModel: PlanDetailViewModel = viewModel(factory = viewModelFactory)

    val detailScreenState by viewModel.detailScreenState.collectAsStateWithLifecycle()

    LaunchedEffect(userDietPlanId) {
        viewModel.loadPlanAndDiaries(userDietPlanId, false) // 初始加載，getRefresh=false
    }

    HealthHelperTheme {
        Scaffold(
            topBar = {
                PlanDetailTopBar(
                    onBackClick = { navController.popBackStack() },
                    planCategoryName = categoryName
                )
            }
        ) { paddingValues ->
            when {
                detailScreenState.isLoading -> {
                    // 顯示全局的加載指示器
                    LoadingIndicator(
                        text = "Loading data..."
                    )
                }

                detailScreenState.errorMessage != null &&
                        detailScreenState.planDetailResult !is ApiResult.Success && // 確保不是日記加載失敗但計劃成功的情況
                        detailScreenState.diaryListResult !is ApiResult.Success -> { // 如果兩者都失敗才顯示頂級錯誤
                    // 顯示全局錯誤信息
                    ErrorState(detailScreenState.errorMessage!!) {
                        viewModel.loadPlanAndDiaries(userDietPlanId, true)
                    }

                }

                else -> {
                    // 數據加載完成（可能部分成功，部分失敗）
                    // 顯示計劃詳情
                    when (val planResult = detailScreenState.planDetailResult) {
                        is ApiResult.Success -> {
                            val plan = planResult.data
                            PlanDetailContent(
                                modifier = Modifier
                                    .padding(paddingValues)
                                    .verticalScroll(rememberScrollState()),
                                plan = plan
                            )
                        }

                        is ApiResult.Error -> {
                            Text("Failed to load plan details: ${planResult.message}")
                            // 可以提供一個只重試計劃詳情的按鈕
                        }

                        is ApiResult.Loading -> {
                            LoadingIndicator(
                                text = "Loading plan details...",
                            )
                        }
                    }

                    // 顯示日記列表
                    when (val diaryResult = detailScreenState.diaryListResult) {
                        is ApiResult.Success -> {
                            val diaryList = diaryResult.data
                            if (diaryList.isEmpty()) {
                                Text("No diary entries found for this plan period.")
                            } else {
                                // TODO Display diary entries
                            }
                        }

                        is ApiResult.Error -> {
                            Text("Failed to load diary entries: ${diaryResult.message}")
                            // 可以提供一個只重試日記列表的按鈕
                        }

                        is ApiResult.Loading -> {
                            LoadingIndicator(
                                text = "Loading diary entries..."
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanDetailTopBar(onBackClick: () -> Unit, planCategoryName: String) {
    TopAppBar(
        title = {
            Title(
                title = planCategoryName,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.primary
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    modifier = Modifier.scale(2.0f),
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
//            titleContentColor = MaterialTheme.colorScheme.onBackground,
//            navigationIconContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

@Composable
fun PlanDetailContent(modifier: Modifier = Modifier, plan: PlanModel) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val startDate = runCatching { transformDate(plan.startDateTime) }.getOrDefault("錯誤日期")
        val endDate = runCatching { transformDate(plan.endDateTime) }.getOrDefault("錯誤日期")

        // 整體進度
//        OverallProgressSection(
//            progress = plan.overallProgress,
//            startDate = plan.startDateLabel,
//            endDate = plan.endDateLabel
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(
//            text = plan.progressDescription,
//            style = MaterialTheme.typography.bodySmall,
//            color = Color.Gray,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.padding(horizontal = 16.dp)
//        )
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // 時間切換按鈕
//        TimeToggleButtons()
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // 平均卡路里
//        SectionTitle(title = "平均卡路里", icon = Icons.Filled.LocalFireDepartment)
//        Spacer(modifier = Modifier.height(8.dp))
//        CaloriesProgress(current = plan.averageCalories, max = plan.maxCalories)
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // 營養素分布
//        NutritionDistributionSection(distributions = plan.nutritionDistribution)
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // 詳細營養成分
//        SectionTitle(title = "營養成分")
//        Spacer(modifier = Modifier.height(16.dp))
//        plan.detailedNutrients.forEach { nutrient ->
//            DetailedNutrientItem(nutrient = nutrient)
//            Spacer(modifier = Modifier.height(12.dp))
//        }
    }
}

@Composable
fun OverallProgressSection(progress: Float, startDate: String, endDate: String) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = startDate, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        Text(text = "完成率", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Text(text = endDate, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}

@Composable
fun TimeToggleButtons() {
    var selectedIndex by remember { mutableStateOf(0) }
    val options = listOf("全部時間", "日期")

    SegmentedButtonToggleGroup(
        options = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { newIndex -> selectedIndex = newIndex }
    )
}

@Composable
fun <T> SegmentedButtonToggleGroup(
    options: List<T>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    selectedTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    unselectedTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)) //
            .background(unselectedColor)
    ) {
        options.forEachIndexed { index, option ->
            Button(
                onClick = { onSelectedIndexChange(index) },
                modifier = Modifier.weight(1f),
                shape = when (index) {
                    0 -> RoundedCornerShape(
                        topStart = 8.dp,
                        bottomStart = 8.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp
                    )

                    options.lastIndex -> RoundedCornerShape(
                        topStart = 0.dp,
                        bottomStart = 0.dp,
                        topEnd = 8.dp,
                        bottomEnd = 8.dp
                    )

                    else -> RoundedCornerShape(0.dp)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedIndex == index) selectedColor else unselectedColor,
                    contentColor = if (selectedIndex == index) selectedTextColor else unselectedTextColor
                ),
                elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp)
            ) {
                Text(text = option.toString())
            }
        }
    }
}


@Composable
fun SectionTitle(title: String, icon: ImageVector? = null) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CaloriesProgress(current: Float, max: Float) {
    val progress = if (max > 0) (current / max).coerceIn(0f, 1f) else 0f
    Column {
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp)), // 圓角
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${current.toInt()}/${max.toInt()} 大卡",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

//@Composable
//fun NutritionDistributionSection(distributions: List<NutritionData>) {
//    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
//        // 左側營養素文字和狀態
//        Column(modifier = Modifier.weight(1f)) {
//            distributions.forEach { nutrition ->
//                NutritionInfoItem(nutrition)
//                Spacer(modifier = Modifier.height(8.dp))
//            }
//        }
//        Spacer(modifier = Modifier.width(16.dp))
//        // 右側環形圖
//        DonutChart(
//            modifier = Modifier.size(120.dp),
//            slices = distributions.map {
//                DonutSlice(
//                    it.percentage,
//                    it.color,
//                    "${(it.percentage * 100).toInt()}%"
//                )
//            }
//        )
//    }
//}

//@Composable
//fun NutritionInfoItem(nutrition: NutritionData) {
//    Column {
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Box(
//                modifier = Modifier
//                    .size(8.dp)
//                    .background(nutrition.color, CircleShape)
//            )
//            Spacer(modifier = Modifier.width(4.dp))
//            Text(
//                text = nutrition.name,
//                style = MaterialTheme.typography.bodyMedium,
//                fontWeight = FontWeight.SemiBold
//            )
//        }
//        Text(
//            text = nutrition.status,
//            style = MaterialTheme.typography.bodySmall,
//            color = if (nutrition.status.startsWith("超過")) Color.Red else Color.Gray,
//            modifier = Modifier.padding(start = 12.dp) // 對齊
//        )
//    }
//}

data class DonutSlice(val value: Float, val color: Color, val label: String)

@Composable
fun DonutChart(
    modifier: Modifier = Modifier,
    slices: List<DonutSlice>,
    strokeWidth: Dp = 20.dp
) {
    Canvas(modifier = modifier) {
        val totalValue = slices.sumOf { it.value.toDouble() }.toFloat()
        if (totalValue == 0f) return@Canvas

        var startAngle = -90f // Start from the top

        slices.forEach { slice ->
            val sweepAngle = (slice.value / totalValue) * 360f
            drawArc(
                color = slice.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx())
            )
            startAngle += sweepAngle
        }
        // 你可以在這裡繪製中間的百分比標籤，但會比較複雜，需要計算每個標籤的位置
    }
}


//@Composable
//fun DetailedNutrientItem(nutrient: NutrientDetail) {
//    val progress = if (nutrient.maxValue > 0) (nutrient.currentValue / nutrient.maxValue).coerceIn(
//        0f,
//        1f
//    ) else 0f
//    val progressColor =
//        if (nutrient.currentValue > nutrient.maxValue) Color.Red else MaterialTheme.colorScheme.primaryContainer
//
//    Column {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(text = nutrient.name, style = MaterialTheme.typography.bodyMedium)
//            Text(
//                text = "${nutrient.currentValue.toInt()}${nutrient.unit} ${nutrient.percentageString}",
//                style = MaterialTheme.typography.bodyMedium,
//                color = if (nutrient.currentValue > nutrient.maxValue) Color.Red else MaterialTheme.colorScheme.onSurface
//            )
//        }
//        Spacer(modifier = Modifier.height(4.dp))
//        LinearProgressIndicator(
//            progress = { progress },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(8.dp)
//                .clip(RoundedCornerShape(4.dp)),
//            color = progressColor,
//            trackColor = MaterialTheme.colorScheme.surfaceVariant
//        )
//    }
//}

// --- Previews ---
