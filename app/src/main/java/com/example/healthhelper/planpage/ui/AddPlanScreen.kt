package com.example.healthhelper.planpage.ui

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
import androidx.compose.material.icons.filled.ExpandMore
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
import com.example.healthhelper.planpage.ui.components.DonutChart

// 圖表數據模型
data class ChartData(val value: Float, val color: Color)

// 圖表旁的圖例數據模型
data class MacroInfo(val name: String, val grams: Int, val color: Color)


@Composable
fun AddPlan(
    navController: NavHostController = rememberNavController()
) {
    HealthHelperTheme {
        Scaffold(
            topBar = {
                DietSettingsTopBar(onBackClick = { /* TODO: 處理返回事件 */ })
            },
            // 使用我們 Theme 中定義的背景色
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->
            DietSettingsContent(
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DietSettingsTopBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                "低碳水飲食計畫",
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
private fun DietSettingsContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 設定週期
        SectionTitle(title = "設定你的週期", modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.height(8.dp))
        PeriodDropdown()
        Spacer(modifier = Modifier.height(16.dp))
        DateSelector(label = "開始日期", date = "2024/10/08") { /* TODO: 開啟日期選擇器 */ }
        Spacer(modifier = Modifier.height(16.dp))
        DateSelector(label = "結束日期", date = "2024/10/15") { /* TODO: 開啟日期選擇器 */ }
        Spacer(modifier = Modifier.height(24.dp))

        // 營養素圖表
        NutritionChartSection()
        Spacer(modifier = Modifier.height(12.dp))

        // 每日目標
        SectionTitle(title = "每日目標", modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.height(16.dp))
        CalorieInput()
        Spacer(modifier = Modifier.height(24.dp))

        // 詳細說明
        MacroDetailItem(
            name = "碳水化合物",
            percentage = 20,
            grams = 75,
            recommendation = "10-20%",
            details = listOf(
                "主要來自蔬菜、少量水果和堅果等高纖維低升糖食物。",
                "儘量避免精製穀物、糖和澱粉類食物（如麵包、米飯、馬鈴薯）。"
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        MacroDetailItem(
            name = "蛋白質",
            percentage = 20,
            grams = 75,
            recommendation = "20-30%",
            details = listOf(
                "來自瘦肉、家禽、魚類、蛋類和乳製品等高品質的蛋白質來源。",
                "蛋白質有助於維持肌肉質量並增加飽腹感。"
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        MacroDetailItem(
            name = "脂肪",
            percentage = 60,
            grams = 100,
            recommendation = "60-70%",
            details = listOf(
                "健康的脂肪來源包括：橄欖油、椰子油、酪梨、堅果和種子、脂肪魚（如鮭魚和鯖魚）等。",
                "脂肪提供持續的能量並幫助減少對碳水的依賴。"
            )
        )

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
private fun PeriodDropdown() {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("選擇週期", color = Color.Gray)
            Icon(
                imageVector = Icons.Default.ExpandMore,
                contentDescription = "展開",
                tint = Color.Gray
            )
        }
    }
}

@Composable
private fun DateSelector(label: String, date: String, onClick: () -> Unit) {
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
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
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
private fun NutritionChartSection() {
    // 數據的單一來源 (Single Source of Truth)
    val macroInfo = listOf(
        MacroInfo("碳水化合物", 75, Color(0xFF304FFE)), // 藍色
        MacroInfo("蛋白質", 75, Color(0xFFD50000)),  // 紅色
        MacroInfo("脂肪", 150, Color(0xFF00C853)) // 綠色 - 為了符合20/20/60的比例，這裡改成150克
    )

    // 從 macroInfo 動態生成圖表數據
    // ChartData 的 value 現在是實際的克數
    val chartData = macroInfo.map {
        ChartData(value = it.grams.toFloat(), color = it.color)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // 左側圖例 (保持不變)
        Column(modifier = Modifier.weight(1f)) {
            macroInfo.forEach { info ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${info.name} ",
                        color = info.color,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${info.grams}克",
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
            DonutChart(data = chartData, modifier = Modifier.fillMaxSize()) // 傳遞動態生成的數據
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalorieInput() {
    var text by remember { mutableStateOf("") }
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
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("e.g. 1500") },
            modifier = Modifier.width(150.dp),
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
    grams: Int,
    recommendation: String,
    details: List<String>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$name ${percentage}% (${grams}公克)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "建議每日攝取比例：$recommendation",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        details.forEach { detail ->
            Row(modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)) {
                Text(text = "• ", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Text(text = detail, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
        }
    }
}


@Preview(showBackground = true, device = "id:pixel_6")
@Composable
fun AddPlanPreview() {
    AddPlan()
}