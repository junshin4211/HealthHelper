package com.example.healthhelper.planpage.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.R
import com.example.healthhelper.screen.TabViewModel
import com.example.healthhelper.ui.theme.HealthHelperTheme

@Composable
fun PlanMain(
    navController: NavHostController = rememberNavController(),
    tabViewModel: TabViewModel = viewModel()
) {
    HealthHelperTheme{
        Scaffold(
            topBar = { PlanTopBar() }
        ) { paddingValues ->
            PlanContent(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
                tabViewModel = tabViewModel
            )
        }
    }
}

@Composable
fun PlanContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    tabViewModel: TabViewModel
) {
    //setting bottom bar visibility
    tabViewModel.setTabVisibility(true)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // 使用主題背景色
    ) {
        DietFilterBar()
        HorizontalDivider(thickness = 2.dp)
        PlanSection(title = "我的計畫") {
            MyPlanCard()
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(thickness = 2.dp)
        PlanSection(title = "已完成") {
            CompletedPlanCard()
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
fun DietFilterBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterItem(painter = painterResource(id = R.drawable.protein), label = "高蛋白")
        FilterItem(painter = painterResource(id = R.drawable.lowcarb), label = "低碳水")
        FilterItem(painter = painterResource(id = R.drawable.ketone), label = "生酮")
        FilterItem(painter = painterResource(id = R.drawable.mediterra), label = "地中海")
        FilterItem(painter = painterResource(id = R.drawable.custom), label = "自訂")
    }
}

@Composable
fun FilterItem(painter: Painter, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary, // 使用主題顏色
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, color = MaterialTheme.colorScheme.primary, fontSize = 12.sp)
    }
}

@Composable
fun PlanSection(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground // 使用主題文字顏色
        )
        Spacer(modifier = Modifier.height(12.dp))
        content()
    }
}

@Composable
fun MyPlanCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.highproteinimg),
                    contentDescription = "高蛋白飲食",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "高蛋白飲食",
                    color = MaterialTheme.colorScheme.secondary, // 使用主題次要顏色
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "more...",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun CompletedPlanCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.lowcarbimg),
                contentDescription = "低碳水計畫",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "低碳水計畫",
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "2024/8/1~2024/8/31",
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp
                    )
                }
                Text(
                    text = "more...",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}


@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun PlanMainPreview() {
    PlanMain()
}