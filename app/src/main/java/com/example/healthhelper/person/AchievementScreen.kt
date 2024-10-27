package com.example.healthhelper.person

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.person.model.AchievementData
import com.example.healthhelper.person.widget.CustomAchievTabRow
import com.example.healthhelper.person.widget.CustomTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementScreen(navController: NavHostController) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabLabels = listOf(
        stringResource(R.string.totalAcievement),
        stringResource(R.string.foodAcievementList),
        stringResource(R.string.weightAcievementList),
        stringResource(R.string.planAcievementList),
    )
    var isShowDetailDialog by remember { mutableStateOf(false) }
    var selectedAchievement by remember { mutableStateOf<AchievementData?>(null) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.myAchievement),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                scrollBehavior = scrollBehavior,
                actions = {}
            )
        },
        containerColor = colorResource(R.color.backgroundcolor)
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomAchievTabRow(
                selectedTabIndex = selectedTab,
                onTabSelected = { selectedTab = it },
                tabLabels = tabLabels
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                when (selectedTab) {
                    0 -> TotalAchievementList()
                    1 -> FoodAchievementList(
                        foodAchievements = generateSampleAchievements(),
                        onItemClick = { achievement ->
                            selectedAchievement = achievement
                        }
                    )

                    2 -> WeightAchievementList()
                    3 -> PlanAchievementList()
                }
                selectedAchievement?.let { achievement ->
                    AchievementDetailDialog(
                        achievementData = achievement,
                        onDismiss = { selectedAchievement = null }
                    )
                }

//                Text(
//                    stringResource(R.string.weightAcievement),
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Text(
//                    stringResource(R.string.weightAcievementContent),
//                    fontSize = 12.sp,
//                    color = Color.DarkGray
//                )
//                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
//                Text(
//                    stringResource(R.string.planAcievement),
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Text(
//                    stringResource(R.string.planAcievementContent),
//                    fontSize = 12.sp,
//                    color = Color.DarkGray
//                )
//                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            }

        }
    }

}

@Composable
fun AchievementDetailDialog(
    achievementData: AchievementData,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(colorResource(R.color.backgroundcolor))
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement =Arrangement.End){
                IconButton(onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.close)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = achievementData.aIcon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = achievementData.aname,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(text = achievementData.acontent, fontSize = 16.sp)
                Text(
                    text = stringResource(R.string.finishDate) + " ${achievementData.finishDate}",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp)
                )

            }
        }
    }
}


@Composable
fun TotalAchievementList() {

}

@Composable
fun FoodAchievementList(
    foodAchievements: List<AchievementData>,
    onItemClick: (AchievementData) -> Unit,
) {
    Text(
        stringResource(R.string.foodAcievement),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
    Text(
        stringResource(R.string.foodAcievementContent),
        fontSize = 12.sp,
        color = Color.DarkGray
    )
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
    ) {
        items(foodAchievements) { foodAchievement ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onItemClick(foodAchievement) }
            ) {
                Image(
//                    bitmap = foodAchievement.aIcon.asImageBitmap(),
                    painter = painterResource(foodAchievement.aIcon),
                    contentDescription = "",
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(text = foodAchievement.aname)
            }
        }
    }
    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
}

@Composable
fun WeightAchievementList() {
}

@Composable
fun PlanAchievementList() {
}


fun generateSampleAchievements(): List<AchievementData> {
    return listOf(
        AchievementData(
            id = "1",
            atype = "food",
            aIcon = R.drawable.baseline_photo_24,
            aname = "健康飲食達人",
            acontent = "成功記錄了30天的健康飲食。",
            finishDate = "2023-10-01"
        ),
        AchievementData(
            id = "2",
            atype = "food",
            aIcon = R.drawable.baseline_photo_24,
            aname = "素食挑戰者",
            acontent = "成功完成了7天的素食挑戰。",
            finishDate = "2023-09-15"
        ),
        AchievementData(
            id = "3",
            atype = "food",
            aIcon = R.drawable.baseline_photo_24,
            aname = "新手廚師",
            acontent = "學會了10道健康食譜。",
            finishDate = "2023-08-20"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AchievementScreenPreview() {
    AchievementScreen(rememberNavController())
}