package com.example.healthhelper.planpage.ui

import android.content.res.Configuration
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.R
import com.example.healthhelper.planpage.data.Result
import com.example.healthhelper.planpage.data.model.PlanModel
import com.example.healthhelper.planpage.data.remote.DependencyProvider
import com.example.healthhelper.planpage.domain.model.DietPlanType
import com.example.healthhelper.planpage.domain.usecase.filterAndSortPlan
import com.example.healthhelper.planpage.domain.usecase.transformDate
import com.example.healthhelper.planpage.navigation.Screen
import com.example.healthhelper.planpage.ui.components.LoadingIndicator
import com.example.healthhelper.planpage.ui.viewmodel.AppViewModelFactory
import com.example.healthhelper.planpage.ui.viewmodel.PlanMainViewModel
import com.example.healthhelper.screen.TabViewModel
import com.example.healthhelper.ui.theme.HealthHelperTheme


@Composable
fun PlanMain(
    navController: NavHostController = rememberNavController(),
    tabViewModel: TabViewModel = viewModel(),
) {
    HealthHelperTheme {
        Scaffold(
            topBar = { PlanTopBar() }
        ) { paddingValues ->
            val planRepository = DependencyProvider.planRepository
            val viewModelFactory = remember { AppViewModelFactory(planRepository) }
            val viewModel: PlanMainViewModel = viewModel(factory = viewModelFactory)
            val planState by viewModel.planMainState.collectAsStateWithLifecycle()

            //判斷是否取得資料
            when (val state = planState) {
                is Result.Loading -> {
                    LoadingIndicator()
                }

                is Result.Success -> {
                    val planList = state.data
                    PlanContent(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController,
                        tabViewModel = tabViewModel,
                        planList = planList
                    )
                }

                is Result.Error -> {
                    Log.d("PlanMain", "Error: ${state.message}")
                    ErrorDisplay(message = state.message ?: "Unknown error")
                }
            }

        }
    }
}


@Composable
fun ErrorDisplay(message: String) {
    Text("Error: $message", color = MaterialTheme.colorScheme.error)
}

@Composable
fun PlanContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    tabViewModel: TabViewModel,
    planList: List<PlanModel>
) {
    var onGoingPlanImage by remember { mutableIntStateOf(R.drawable.customimg) }
    var onGoingPlanName by remember { mutableStateOf("") }
    var onGoingPlanDate by remember { mutableStateOf("") }

    var completedPlanImage by remember { mutableIntStateOf(R.drawable.customimg) }
    var completedPlanName by remember { mutableStateOf("") }
    var completedPlanDate by remember { mutableStateOf("") }

    //setting bottom bar visibility
    tabViewModel.setTabVisibility(true)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // 使用主題背景色
    ) {
        DietFilterBar(navController = navController)
        HorizontalDivider(thickness = 2.dp)
        PlanSection(title = "我的計畫",navController = navController) {
            PlanCard(
                navController = navController,
                planList = planList,
                imageDisplay = onGoingPlanImage,
                nameDisplay = onGoingPlanName,
                dateDisplay = onGoingPlanDate,
                isFinish = false,
                onSetPlan = { image, name, date ->
                    onGoingPlanImage = image
                    onGoingPlanName = name
                    onGoingPlanDate = date
                })
        }
//        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(thickness = 2.dp)
        PlanSection(title = "已完成",navController = navController) {
            PlanCard(
                navController = navController,
                planList = planList,
                imageDisplay = completedPlanImage,
                nameDisplay = completedPlanName,
                dateDisplay = completedPlanDate,
                isFinish = true,
                onSetPlan = { image, name, date ->
                    completedPlanImage = image
                    completedPlanName = name
                    completedPlanDate = date
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.plan),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.onPrimary // 使用主題顏色
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun DietFilterBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterItem(
            painter = painterResource(id = R.drawable.protein),
            label = DietPlanType.HIGH_PROTEIN,
            navController = navController,
            )
        FilterItem(
            painter = painterResource(id = R.drawable.lowcarb),
            label = DietPlanType.LOW_CARB_HYDRATE,
            navController = navController)
        FilterItem(
            painter = painterResource(id = R.drawable.ketone),
            label = DietPlanType.KETONE,
            navController = navController)
        FilterItem(
            painter = painterResource(id = R.drawable.mediterra),
            label = DietPlanType.MEDITERRA,
            navController = navController)
        FilterItem(
            painter = painterResource(id = R.drawable.custom),
            label = DietPlanType.CUSTOM,
            isCustom = true,
            navController = navController)
    }
}

@Composable
fun FilterItem(painter: Painter,
               label: DietPlanType,
               isCustom: Boolean = false,
               navController: NavHostController)
{
    Column(
        modifier = Modifier.clickable {
            if (!isCustom)
            {
                navController.navigate(Screen.AddPlan.createRoute(label.displayNameRes))
            }else{
                navController.navigate(Screen.AddCustomPlan.createRoute(label.displayNameRes))
            }
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = stringResource(label.displayNameRes),
            tint = MaterialTheme.colorScheme.primary, // 使用主題顏色
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = stringResource(label.displayNameRes), color = MaterialTheme.colorScheme.primary, fontSize = 12.sp)
    }
}

@Composable
fun PlanSection(title: String,
                navController: NavHostController,
                content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            //.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground // 使用主題文字顏色
        )
        Spacer(modifier = Modifier.height(8.dp))
        content()
        TextButton(
            onClick = {navController.navigate(Screen.ManagePlan.route)},
            modifier = Modifier
                .align(Alignment.End)
                .scale(0.8f)
        ) {
            Text(
                text = "more...",
                color = colorResource(id = R.color.blue01),
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun PlanCard(
    navController: NavHostController,
    planList: List<PlanModel> = emptyList(),
    isFinish: Boolean,
    imageDisplay: Int,
    nameDisplay: String,
    dateDisplay: String,
    onSetPlan: (image: Int, name: String, date: String) -> Unit,
) {
    LaunchedEffect(planList,isFinish) {
        Log.d("PlanMain", "LaunchedEffect triggered due to planList change.")
        if (planList.isEmpty()) {
            onSetPlan(R.drawable.customimg, "尚無任何無計畫", "")
        } else {
            val firstPlan = filterAndSortPlan(planList,isFinish).first()
            val startDate = try { transformDate(firstPlan.startDateTime) } catch (e: Exception) { "錯誤日期" }
            val endDate = try { transformDate(firstPlan.endDateTime) } catch (e: Exception) { "錯誤日期" }
            val imageRes = when (firstPlan.categoryId) {
                1 -> R.drawable.highproteinimg
                2 -> R.drawable.lowcarbimg
                3 -> R.drawable.ketoneimg
                4 -> R.drawable.mediterraimg
                else -> R.drawable.customimg
            }
            Log.d("PlanMain", "firstPlan: $firstPlan finishstate: ${firstPlan.finishstate}")
            onSetPlan(imageRes, "${firstPlan.categoryName}計畫", "$startDate ~ $endDate")
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {navController.navigate(Screen.PlanDetail.route)},
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(id = imageDisplay),
                    contentDescription = "我的計畫圖片",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
                if (isFinish)
                {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Current Plan",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .size(32.dp)
                        .background(Color.White, CircleShape)
                )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                    Text(
                        text = nameDisplay,
                        color = MaterialTheme.colorScheme.secondary, // 使用主題次要顏色
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = dateDisplay,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp
                    )

            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_5", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PlanMainPreview() {
    //PlanMain()
    HealthHelperTheme {
        PlanContent(
            modifier = Modifier,
            navController = rememberNavController(),
            tabViewModel = viewModel(),
            planList = emptyList()
        )
    }
}