package com.example.healthhelper.planpage.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthhelper.ui.theme.HealthHelperTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.R
import com.example.healthhelper.planpage.data.model.AddPlanModel
import com.example.healthhelper.planpage.data.remote.DependencyProvider
import com.example.healthhelper.planpage.domain.model.CategoryID
import com.example.healthhelper.planpage.domain.model.ChartData
import com.example.healthhelper.planpage.domain.model.DateRangeTitle
import com.example.healthhelper.planpage.domain.model.DietPlanType
import com.example.healthhelper.planpage.domain.model.MacroInfo
import com.example.healthhelper.planpage.domain.model.NutritionType
import com.example.healthhelper.planpage.domain.usecase.calculateDateMillisRange
import com.example.healthhelper.planpage.domain.usecase.calculateNutrition
import com.example.healthhelper.planpage.domain.usecase.calculateNutritionGrams
import com.example.healthhelper.planpage.domain.usecase.formatMillisToDateString
import com.example.healthhelper.planpage.domain.usecase.formatMillisToISO
import com.example.healthhelper.planpage.domain.usecase.validationPlanMessage
import com.example.healthhelper.planpage.ui.components.Button_Plan
import com.example.healthhelper.planpage.ui.components.CreateDropDownMenu
import com.example.healthhelper.planpage.ui.components.CustomAlertDialog
import com.example.healthhelper.planpage.ui.components.DateRangePickerDialog
import com.example.healthhelper.planpage.ui.components.DonutChart
import com.example.healthhelper.planpage.ui.components.NutritionSlider
import com.example.healthhelper.planpage.ui.components.OutlinedTextField_Plan
import com.example.healthhelper.planpage.ui.components.Title
import com.example.healthhelper.planpage.ui.viewmodel.AddPlanUiState
import com.example.healthhelper.planpage.ui.viewmodel.AddPlanViewModel
import com.example.healthhelper.planpage.ui.viewmodel.AppViewModelFactory
import com.example.healthhelper.signuplogin.UserManager
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun AddCustomPlan(
    navController: NavHostController = rememberNavController(),
    @StringRes title: Int,
) {
    // 獲取repository,載入viewModel
    val planRepository = DependencyProvider.planRepository
    val viewModelFactory = remember { AppViewModelFactory(planRepository) }
    val viewModel: AddPlanViewModel = viewModel(factory = viewModelFactory)

    // 取得新增狀態
    val uiState by viewModel.addPlanState.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }
    val appBarTitle by remember { mutableIntStateOf(title) }

    // 根据uiState狀態來判斷是否新增計畫成功
    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is AddPlanUiState.Success -> {
                snackBarHostState.showSnackbar(
                    "計劃已成功儲存！",
                    duration = SnackbarDuration.Short
                )

                viewModel.refreshAddPlanState() // 重置狀態，避免重複顯示

                navController.previousBackStackEntry?.savedStateHandle?.set("plan_added", true) // 設定關鍵字,讓主頁知道planlist有更新

                navController.popBackStack() // 導航回去
            }

            is AddPlanUiState.Error -> {
                snackBarHostState.showSnackbar(
                    "錯誤: ${state.message}",
                    duration = SnackbarDuration.Short
                )

                viewModel.refreshAddPlanState()
            }

            is AddPlanUiState.Loading -> {

                Log.d("AddPlanScreen", "Plan creation in progress...")
                // UI 可以在按鈕或其他地方顯示加載指示
            }

            AddPlanUiState.Idle -> { /* 初始或已重置狀態 */
            }
        }
    }

    HealthHelperTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) },
            topBar = {
                AddPlanTopBar(
                    onBackClick = { navController.popBackStack() },
                    title = appBarTitle
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->

            DietSettingsContent(
                modifier = Modifier.padding(paddingValues),
                title = title,
                snackBarHostState = snackBarHostState,
                isSaving = uiState is AddPlanUiState.Loading,
                onSaveClick = { addPlanData ->
                    viewModel.submitPlan(addPlanData)
                }
            )
        }
    }
}

//topbar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddPlanTopBar(onBackClick: () -> Unit, @StringRes title: Int) {
    TopAppBar(
        title = {
            Title(
                title = stringResource(title) + stringResource(R.string.plan),
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
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

// 新增頁面主頁內容
@Composable
private fun DietSettingsContent(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    snackBarHostState: SnackbarHostState,
    isSaving: Boolean,
    onSaveClick: (AddPlanModel) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        val currentUserId = UserManager.getUser().userId // userId

        // 開始結束日期文字顯示
        var selectedStartDate by remember { mutableStateOf("") }
        var selectedEndDate by remember { mutableStateOf("") }

        // 新增狀態來保存選中的日期，用於傳遞回 DateRangePickerDialog
        var savedSelectedStartDateMillis by remember { mutableStateOf<Long?>(null) } // startDateTime
        var savedSelectedEndDateMillis by remember { mutableStateOf<Long?>(null) } // endDateTime

        // 顯示日期選擇
        var showDateRangePicker by remember { mutableStateOf(false) }

        // 顯示確認Dialog
        var showConfirm by remember { mutableStateOf(false) }

        var inputCalories by remember { mutableIntStateOf(1500) } // Caloriegoal

        // 營養公克數
        var calculatedCarbGram by remember { mutableFloatStateOf(187.50f) }
        var calculatedProteinGram by remember { mutableFloatStateOf(112.50f) }
        var calculatedFatGram by remember { mutableFloatStateOf(33.33f) }

        // 營養比例
        var calculatedCarbPercent by remember { mutableFloatStateOf(50f) }
        var calculatedProteinPercent by remember { mutableFloatStateOf(30f) }
        var calculatedFatPercent by remember { mutableFloatStateOf(20f) }

        // 更新日期函數
        fun updateDateStates(startMillis: Long?, endMillis: Long?) {
            savedSelectedStartDateMillis = startMillis
            savedSelectedEndDateMillis = endMillis

            selectedStartDate =
                startMillis?.let { formatMillisToDateString(it) } ?: ""
            selectedEndDate = endMillis?.let { formatMillisToDateString(it) } ?: ""
        }

        Spacer(modifier = Modifier.height(8.dp))
        // 週期選單
        PeriodDropdown(
            title = stringResource(R.string.set_plan_time_title),
            onDateRangeSelected = { title ->
                val datePair = calculateDateMillisRange(title)
                if (datePair != null) {
                    updateDateStates(datePair.first, datePair.second)
                } else {
                    updateDateStates(null, null)
                }
            }
        )
        // 顯示日期選擇
        if (showDateRangePicker) {
            DateRangePickerDialog(
                initialSelectedStartDateMillis = savedSelectedStartDateMillis,
                initialSelectedEndDateMillis = savedSelectedEndDateMillis,
                onConfirm = { pair ->
                    updateDateStates(pair.first, pair.second)
                    showDateRangePicker = false
                },
                onDismiss = {
                    showDateRangePicker = false
                })
        }

        Spacer(modifier = Modifier.height(16.dp))
        // 開始
        DateSelector(label = stringResource(R.string.startDate), date = selectedStartDate) {
            showDateRangePicker = true
        }
        Spacer(modifier = Modifier.height(16.dp))
        // 結束
        DateSelector(label = stringResource(R.string.endDate), date = selectedEndDate) {
            showDateRangePicker = true
            Log.d(
                "DateSelector_Click",
                "DateSelector for StartDate clicked, showDateRangePicker = $showDateRangePicker"
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        // 營養素圖表
        NutritionChartSection(
            carbGoal = calculatedCarbPercent,
            proteinGoal = calculatedProteinPercent,
            fatGoal = calculatedFatPercent,
            carbGramText = calculatedCarbGram,
            proteinGramText = calculatedProteinGram,
            fatGramText = calculatedFatGram
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 卡路里輸入
        CalorieInputSection(
            title = stringResource(R.string.dailyGoal),
            calorie = inputCalories,
            onSetCalorie = { inputCalories = it }
        )
        Spacer(modifier = Modifier.height(24.dp))

        // 營養滑桿
        NutritionSliderSection(
            inputCalories = inputCalories,
            currentCarbGram = calculatedCarbGram,
            currentFatGram = calculatedFatGram,
            currentProteinGram = calculatedProteinGram,
            currentCarbPercent = calculatedCarbPercent,
            currentFatPercent = calculatedFatPercent,
            currentProteinPercent = calculatedProteinPercent,
            onSetGram = { fat, carb, pro ->
                calculatedFatGram = fat
                calculatedCarbGram = carb
                calculatedProteinGram = pro
            }
        ) { fat, carb, pro ->
            Log.d("AddCustomPlan", "NutritionSliderSection set goal : F:$fat, C:$carb, P:$pro")
            calculatedCarbPercent = carb
            calculatedProteinPercent = pro
            calculatedFatPercent = fat
        }

        Spacer(modifier = Modifier.height(32.dp))
        // 儲存
        Button_Plan(
            modifier = Modifier.width(200.dp),
            onClick = {
                // 檢查日期和卡路里是否有輸入
                val isValidDate =
                    savedSelectedStartDateMillis != null && savedSelectedEndDateMillis != null
                val isValidCalories = inputCalories > 0
                val total = calculatedCarbPercent + calculatedProteinPercent + calculatedFatPercent
                val isValidGoals =
                    calculatedCarbPercent >= 0f && calculatedProteinPercent >= 0f && calculatedFatPercent >= 0f && total == 100f

                if (isValidDate && isValidCalories && isValidGoals) {
                    showConfirm = true
                } else {
                    val errorMessage = when {
                        !isValidDate -> context.getString(R.string.invalidDateTime)
                        !isValidCalories -> context.getString(R.string.invalidCaloriesGoal)
                        !isValidGoals -> context.getString(R.string.invalidNutritionGoal)
                        else -> "未知錯誤"
                    }
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            errorMessage,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        ) {
            if (isSaving) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else {
                Text(
                    stringResource(R.string.save),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        // 點擊儲存後顯示確認對話
        if (showConfirm) {
            CustomAlertDialog(
                onDismissRequest = { showConfirm = false },
                title = {
                    Text(
                        text = stringResource(R.string.savePlan_alert_title),
                        color = Color.Red
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.savePlan_alert_text),
                        color = Color.Red
                    )
                },
                onConfirm = {
                    // 檢查計畫類別ID轉換正確
                    val categoryId = CategoryID.getCateId(title)

                    if (categoryId == null) {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                "無效計畫名稱",
                                duration = SnackbarDuration.Short
                            )
                        }
                        showConfirm = false
                        return@CustomAlertDialog
                    }

                    // 檢查全部資料
                    val isValid = validationPlanMessage(
                        userId = currentUserId,
                        startDateTime = savedSelectedStartDateMillis,
                        endDateTime = savedSelectedEndDateMillis,
                        categoryId = categoryId,
                        finishstate = 0,
                        fatgoal = calculatedFatPercent,
                        carbongoal = calculatedCarbPercent,
                        proteingoal = calculatedProteinPercent,
                        Caloriesgoal = inputCalories.toFloat()
                    )

                    if (isValid != null) {
                        val errorMessage = context.getString(isValid)
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                errorMessage,
                                duration = SnackbarDuration.Short
                            )
                        }
                        showConfirm = false
                        return@CustomAlertDialog
                    }

                    // 轉換日期為後端格式
                    val startDate = formatMillisToISO(savedSelectedStartDateMillis)
                    val endDate = formatMillisToISO(savedSelectedEndDateMillis)

                    if (startDate == null || endDate == null) {
                        Log.e("AddPlanScreen", "日期轉換錯誤")
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                "日期轉換錯誤",
                                duration = SnackbarDuration.Short
                            )
                        }
                        showConfirm = false
                        return@CustomAlertDialog // 阻止繼續執行
                    }

                    val addPlanData = AddPlanModel(
                        userId = currentUserId,
                        startDateTime = startDate,
                        endDateTime = endDate,
                        categoryId = categoryId,
                        finishstate = 0,
                        fatgoal = calculatedFatPercent,
                        carbongoal = calculatedCarbPercent,
                        proteingoal = calculatedProteinPercent,
                        Caloriesgoal = inputCalories.toFloat()
                    )
                    onSaveClick(addPlanData)
                    showConfirm = false
                }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

// 週期下拉式選單
@Composable
private fun PeriodDropdown(onDateRangeSelected: (DateRangeTitle) -> Unit, title: String) {
    var currentSelect by remember { mutableStateOf<DateRangeTitle?>(DateRangeTitle.entries.firstOrNull()) }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Title(modifier = Modifier.align(Alignment.Start), title = title)

        CreateDropDownMenu(
            options = DateRangeTitle.entries,
            selectedOption = currentSelect,
            onOptionSelected = { selectedOption ->
                currentSelect = selectedOption
                onDateRangeSelected(selectedOption)
            },
            getDisplayText = { options -> stringResource(id = options.title) }
        )
    }
}

// 日期選擇內容
@Composable
private fun DateSelector(
    label: String,
    date: String,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(0.5.dp, Color.DarkGray)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = date, modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "選擇日期",
                    tint = Color.Gray
                )
            }
        }
    }
}

// 圖表
@Composable
private fun NutritionChartSection(
    carbGoal: Float,
    proteinGoal: Float,
    fatGoal: Float,
    carbGramText: Float,
    proteinGramText: Float,
    fatGramText: Float
) {

    val nutritioninfo = remember(carbGramText, proteinGramText, fatGramText) {
        listOf(
            MacroInfo("碳水化合物", carbGramText, Color(0xFF304FFE), goal = carbGoal), // 藍色
            MacroInfo("蛋白質", proteinGramText, Color(0xFFD50000), goal = proteinGoal),  // 紅色
            MacroInfo("脂肪", fatGramText, Color(0xFF03A144), goal = fatGoal) // 綠色
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column(modifier = Modifier.weight(1f)) {
            nutritioninfo.forEach { info ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${info.name} ",
                        color = info.color,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = String.format(Locale.US, "%.2f克", info.grams),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // 右側圓圈圖
        Box(
            modifier = Modifier.size(160.dp),
            contentAlignment = Alignment.Center
        ) {
            DonutChart(
                data = nutritioninfo.map { ChartData(it.goal ?: 0f, it.color) },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

// 卡路里
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalorieInputSection(
    title: String,
    calorie: Int,
    onSetCalorie: (Int) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        Title(modifier = Modifier.align(Alignment.Start), title = title)

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = stringResource(R.string.calories),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )

            OutlinedTextField_Plan(
                value = calorie.toString(),
                onValueChange = { newValue ->
                    val newCalorie = newValue.toIntOrNull() ?: 0  //不能為負
                    Log.d("AddPlan_CalorieInput", "Recomposing calories: $newCalorie")
                    onSetCalorie(newCalorie)
                },
                placeholder = { Text(stringResource(R.string.examCalorie)) },
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(120.dp)
            )

            Text(text = "大卡", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

// 營養說明
@Composable
fun NutritionSliderSection(
    inputCalories: Int,
    currentFatGram: Float,
    currentCarbGram: Float,
    currentProteinGram: Float,
    currentCarbPercent: Float,
    currentProteinPercent: Float,
    currentFatPercent: Float,
    onSetGram: (fatGram: Float, carbGram: Float, proteinGram: Float) -> Unit,
    onSetGoal: (fatPercent: Float, carbPercent: Float, proteinPercent: Float) -> Unit
) {
    var carbPercent by remember { mutableFloatStateOf(currentCarbPercent) }
    var proteinPercent by remember { mutableFloatStateOf(currentProteinPercent) }
    var fatPercent by remember { mutableFloatStateOf(currentFatPercent) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        // 營養目標標題
        Title(
            title = stringResource(R.string.nutrition_calculator),
            modifier = Modifier.align(Alignment.Start)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceBright)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                NutritionSlider(
                    label = stringResource(R.string.carb),
                    percent = carbPercent,
                    gram = currentCarbGram,
                    themeColor = Color(0xFF304FFE)
                ) { value ->
                    // 計算營養比例
                    calculateNutrition(
                        nutritionType = NutritionType.CARBOHYDRATE,
                        calories = inputCalories,
                        changedValue = value,
                        carbPercent = carbPercent,
                        fatPercent = fatPercent,
                        proteinPercent = proteinPercent,
                        onsetGoal = { fat, carb, pro ->
                            carbPercent = carb
                            proteinPercent = pro
                            fatPercent = fat

                            onSetGoal(fat, carb, pro)
                        }
                    ) { fat, carb, pro ->
                        onSetGram(fat, carb, pro)
                    }
                }

                NutritionSlider(
                    label = stringResource(R.string.protein),
                    percent = proteinPercent,
                    gram = currentProteinGram,
                    themeColor = Color(0xFFD50000),
                ) { value ->
                    // 計算營養比例
                    calculateNutrition(
                        nutritionType = NutritionType.PROTEIN,
                        calories = inputCalories,
                        changedValue = value,
                        carbPercent = carbPercent,
                        fatPercent = fatPercent,
                        proteinPercent = proteinPercent,
                        onsetGoal = { fat, carb, pro ->
                            carbPercent = carb
                            proteinPercent = pro
                            fatPercent = fat
                            onSetGoal(fat, carb, pro)
                        }
                    ) { fat, carb, pro ->
                        onSetGram(fat, carb, pro)
                    }
                }

                NutritionSlider(
                    label = stringResource(R.string.fat),
                    percent = fatPercent,
                    gram = currentFatGram,
                    themeColor = Color(0xFF03A144),
                ) { value ->
                    // 計算營養比例
                    calculateNutrition(
                        nutritionType = NutritionType.FAT,
                        calories = inputCalories,
                        changedValue = value,
                        carbPercent = carbPercent,
                        fatPercent = fatPercent,
                        proteinPercent = proteinPercent,
                        onsetGoal = { fat, carb, pro ->
                            Log.d(
                                "AddCustomPlan",
                                "calculateNutrition onSetGoal called: F=$fat, C=$carb, P=$pro"
                            )
                            carbPercent = carb
                            proteinPercent = pro
                            fatPercent = fat
                            onSetGoal(fat, carb, pro)
                        }
                    ) { fat, carb, pro ->
                        Log.d(
                            "AddCustomPlan",
                            "calculateNutrition onSetGram called: F=$fat, C=$carb, P=$pro"
                        )
                        onSetGram(fat, carb, pro)
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true, device = "id:pixel_6")
@Composable
fun AddCustomPlanPreview() {
    AddCustomPlan(title = R.string.custom)
}