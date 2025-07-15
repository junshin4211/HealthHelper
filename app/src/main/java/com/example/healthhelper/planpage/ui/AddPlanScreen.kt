package com.example.healthhelper.planpage.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.stringResource
import com.example.healthhelper.R
import com.example.healthhelper.planpage.domain.model.DietPlanRegistry
import com.example.healthhelper.planpage.domain.model.DietPlanType
import com.example.healthhelper.planpage.domain.model.MacroInfo
import com.example.healthhelper.planpage.domain.model.NutritionType
import com.example.healthhelper.planpage.domain.usecase.calculateDateMillisRange
import com.example.healthhelper.planpage.domain.usecase.calculateNutritionGoals
import com.example.healthhelper.planpage.domain.usecase.calculateNutritionGrams
import com.example.healthhelper.planpage.domain.usecase.formatMillisToDateString
import com.example.healthhelper.planpage.ui.components.CreateDropDownMenu
import com.example.healthhelper.planpage.ui.components.DateRangePickerDialog
import com.example.healthhelper.planpage.ui.components.DonutChart
import java.util.Locale

enum class DateRangeTitle(@StringRes val title: Int){
    AWeek(title = R.string.AWeek),
    HalfMonth(title = R.string.halfMonth),
    AMonth(title = R.string.AMonth),
    ThreeMonth(title = R.string.threeMonth),
    SixMonth(title = R.string.sixMonth);
}

// TODO 從把GRAM remember移到外面開始
@Composable
fun AddPlan(
    navController: NavHostController = rememberNavController(),
    @StringRes title: Int
) {
    val appBarTitle by remember { mutableStateOf(title) }
    HealthHelperTheme {
        Scaffold(
            topBar = {
                DietSettingsTopBar(onBackClick = { navController.navigateUp() }, title = appBarTitle)
            },
            // 使用我們 Theme 中定義的背景色
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->
            DietSettingsContent(
                modifier = Modifier.padding(paddingValues),
                title = title
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DietSettingsTopBar(onBackClick: () -> Unit, @StringRes title: Int) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(title)+"飲食計畫",
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
                    contentDescription = "返回"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent, // 讓 TopAppBar 背景與主畫面融合
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

@Composable
private fun DietSettingsContent(
    modifier: Modifier = Modifier,
    @StringRes title: Int
    ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val defaultChooseText = stringResource(R.string.noChoose)

        var selectedStartDate by remember { mutableStateOf("") }
        var selectedEndDate by remember { mutableStateOf("") }

        // 新增狀態來保存選中的日期毫Số (Long?)，用於傳遞回 DateRangePickerDialog
        var savedSelectedStartDateMillis by remember { mutableStateOf<Long?>(null) }
        var savedSelectedEndDateMillis by remember { mutableStateOf<Long?>(null) }

        // 顯示日期選擇
        var showDateRangePicker by remember { mutableStateOf(false) }

        var inputCalories by remember { mutableStateOf(1500) }

        val selectedPlanName: DietPlanType? = DietPlanType.fromResId(title)

        // 更新日期相關狀態的通用函數
        fun updateDateStates(startMillis: Long?, endMillis: Long?) {
            savedSelectedStartDateMillis = startMillis
            savedSelectedEndDateMillis = endMillis

            selectedStartDate = startMillis?.let { formatMillisToDateString(it) } ?: defaultChooseText
            selectedEndDate = endMillis?.let { formatMillisToDateString(it) } ?: defaultChooseText
        }

        // 設定週期
        SectionTitle(title = stringResource(R.string.set_plan_time_title), modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.height(8.dp))
        PeriodDropdown(
            onDateRangeSelected = { title ->
                val datePair = calculateDateMillisRange(title)
                if (datePair != null)
                {
                    updateDateStates(datePair.first, datePair.second)
                }else{
                    updateDateStates(null, null)
                }
            }
        )
        // 顯示日期選擇
        if (showDateRangePicker){
            DateRangePickerDialog(
                initialSelectedStartDateMillis = savedSelectedStartDateMillis,
                initialSelectedEndDateMillis = savedSelectedEndDateMillis,
                onConfirm ={ pair ->
                    updateDateStates(pair.first, pair.second)
                    showDateRangePicker = false
                },
                onDismiss = {
                    showDateRangePicker = false
                })
        }

        Spacer(modifier = Modifier.height(16.dp))
        // 開始
        DateSelector(label = "開始日期", date = selectedStartDate) {
            showDateRangePicker = true
        }
        Spacer(modifier = Modifier.height(16.dp))
        // 結束
        DateSelector(label = "結束日期", date = selectedEndDate) {
            showDateRangePicker = true
        }
        Spacer(modifier = Modifier.height(24.dp))

        // 營養素圖表
        NutritionChartSection(
            calories = inputCalories,
            planTitle = title
        )
        Spacer(modifier = Modifier.height(12.dp))

        // 每日目標
        SectionTitle(title = "每日目標", modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.height(16.dp))
        // 卡路里輸入
        CalorieInput(
            calorie = inputCalories,
            onSetCalorie = { inputCalories = it }
        )
        Spacer(modifier = Modifier.height(24.dp))

        // 詳細說明
        NutritionType.entries.forEach{type ->
//            val planname = DietPlanType.entries.find { it.displayNameRes == title }
            if (selectedPlanName != null) {
                DietPlanRegistry.getNutritionDetail(selectedPlanName,type)?.let { content ->
                    val percentage = calculateNutritionGoals(selectedPlanName.displayNameRes, type.displayNameRes)?.toInt() ?: 0

                    MacroDetailItem(
                        name = stringResource(id = type.displayNameRes),
                        percentage = percentage,
                        grams = 0f,
                        title = content.getTitle(),
                        details = content.getDescriptionPoints().joinToString("")
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { /* TODO: 處理儲存邏輯 */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            // 使用 Material 3 預設的藍色按鈕
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF))
        ) {
            Text("儲存", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
private fun PeriodDropdown(onDateRangeSelected:(DateRangeTitle) -> Unit) {
    var currentSelect by remember { mutableStateOf<DateRangeTitle?>(DateRangeTitle.entries.firstOrNull()) }
    CreateDropDownMenu(
        options = DateRangeTitle.entries,
        selectedOption = currentSelect,
        onOptionSelected = { selectedOption ->
            onDateRangeSelected(selectedOption)
            // TODO Handle option selection
        },
        getDisplayText = { options -> stringResource(id = options.title) }
    )
}

@Composable
private fun DateSelector(
    label: String,
    date: String,
    onClick: () -> Unit) {
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

@Composable
private fun NutritionChartSection(
    calories: Int,
    @StringRes planTitle: Int
) {
    var fatGramText by remember { mutableFloatStateOf(0f)  }
    var carbGramText by remember { mutableFloatStateOf(0f) }
    var proteinGramText by remember { mutableFloatStateOf(0f) }

    Log.d("AddPlan_NutritionChartSection", "Recomposing calories: $calories")

    LaunchedEffect(calories) {
        Log.d("AddPlan_NutritionChartSection", "LaunchedEffect calories: $calories")
        calculateNutritionGrams(
            calories = calories.toFloat(),
            plan = planTitle,
            onSetNutritionGram = { fatGram, carbGram, proteinGram ->
                Log.d("AddPlan_NutritionChartSection", "LaunchedEffect set fatGram: $fatGram carbGram: $carbGram proteinGram: $proteinGram")
                fatGramText = fatGram
                carbGramText = carbGram
                proteinGramText = proteinGram
            }
        )
    }

    // 數據的單一來源 (Single Source of Truth)
    val macroInfo = remember(carbGramText, proteinGramText, fatGramText){
        listOf(
        MacroInfo("碳水化合物", carbGramText, Color(0xFF304FFE)), // 藍色
        MacroInfo("蛋白質", proteinGramText, Color(0xFFD50000)),  // 紅色
        MacroInfo("脂肪", fatGramText, Color(0xFF00C853)) // 綠色
    ) }

//    // 從 macroInfo 動態生成圖表數據
//    val chartData = remember(macroInfo) { macroInfo.map {
//        ChartData(value = it.grams, color = it.color)
//    } }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column(modifier = Modifier.weight(1f)) {
            macroInfo.forEach { info ->
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

        // 右側環圈圖
        Box(
            modifier = Modifier.size(160.dp), // 稍微增大尺寸以容納百分比文字
            contentAlignment = Alignment.Center
        ) {
            DonutChart(
                planTitle = planTitle,
                modifier = Modifier.fillMaxSize()
            ) // 傳遞動態生成的數據
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalorieInput(
    calorie: Int,
    onSetCalorie: (Int) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "卡路里",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
        OutlinedTextField(
            value = calorie.toString(),
            onValueChange = {newValue ->
                val newCalorie = newValue.toIntOrNull() ?:0
                Log.d("AddPlan_CalorieInput", "Recomposing calories: $newCalorie")
                onSetCalorie(newCalorie)
            },
            placeholder = { Text("e.g. 1500") },
            modifier = Modifier.weight(1f),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                // 背景色
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                // 邊框顏色
                unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f),
                focusedBorderColor = MaterialTheme.colorScheme.primary,
            )
        )
        Text(text = "大卡", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun MacroDetailItem(
    name: String,
    percentage: Int,
    grams: Float,
    title: String,
    details: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$name ${percentage}% (${grams}公克)",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = details,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}



@Preview(showBackground = true, device = "id:pixel_6")
@Composable
fun AddPlanPreview() {
    AddPlan(title = R.string.add_plan_default_title)
}