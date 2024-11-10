package com.example.healthhelper.person.screen

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.person.model.Achievement
import com.example.healthhelper.person.personVM.AchievementViewModel
import com.example.healthhelper.person.widget.CustomAchievTabRow
import com.example.healthhelper.person.widget.CustomTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementScreen(
    achievementVM: AchievementViewModel,
    navController: NavHostController,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabLabels = listOf(
        stringResource(R.string.totalAcievement),
        stringResource(R.string.foodAcievementList),
        stringResource(R.string.weightAcievementList),
        stringResource(R.string.planAcievementList),
    )
    var selectedAchievement by remember { mutableStateOf<Achievement?>(null) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.myAchievement),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                scrollBehavior = scrollBehavior,
                actions = {
                    Spacer(modifier = Modifier.size(40.dp))
                }
            )
        },
        containerColor = colorResource(R.color.backgroundcolor)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomAchievTabRow(
                selectedTabIndex = selectedTab,
                onTabSelected = { selectedTab = it },
                tabLabels = tabLabels
            )
            AchievementContent(
                selectedTab = selectedTab,
                achievementVM = achievementVM,
                onItemClick = { selectedAchievement = it }
            )
            selectedAchievement?.let {
                AchievementDetailDialog(achievementData = it) { selectedAchievement = null }
            }
        }
    }
}

@Composable
fun AchievementContent(
    selectedTab: Int,
    achievementVM: AchievementViewModel,
    onItemClick: (Achievement) -> Unit,
) {
    val foodAchievements = achievementVM.getAchievementsByType(listOf(1))
    val weightAchievements = achievementVM.getAchievementsByType(listOf(2))
    val planAchievements = achievementVM.getAchievementsByType(listOf(3, 4, 5, 6))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (selectedTab) {
            0 -> {
                AchievementList(
                    title = stringResource(R.string.foodAcievement),
                    description = stringResource(R.string.foodAcievementContent),
                    achievements = foodAchievements,
                    onItemClick = onItemClick
                )
                AchievementList(
                    title = stringResource(R.string.weightAcievement),
                    description = stringResource(R.string.weightAcievementContent),
                    achievements = weightAchievements,
                    onItemClick = onItemClick
                )
                AchievementList(
                    title = stringResource(R.string.planAcievement),
                    description = stringResource(R.string.planAcievementContent),
                    achievements = planAchievements,
                    onItemClick = onItemClick
                )
            }

            1 -> AchievementList(
                title = stringResource(R.string.foodAcievement),
                description = stringResource(R.string.foodAcievementContent),
                achievements = foodAchievements,
                onItemClick = onItemClick
            )

            2 -> AchievementList(
                title = stringResource(R.string.weightAcievement),
                description = stringResource(R.string.weightAcievementContent),
                achievements = weightAchievements,
                onItemClick = onItemClick
            )

            3 -> AchievementList(
                title = stringResource(R.string.planAcievement),
                description = stringResource(R.string.planAcievementContent),
                achievements = planAchievements,
                onItemClick = onItemClick
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AchievementList(
    title: String,
    description: String,
    achievements: List<Achievement>,
    onItemClick: (Achievement) -> Unit,
) {
    Column {
        Text(
            title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            description,
            fontSize = 12.sp,
            color = Color.DarkGray
        )
        if (achievements.isEmpty() || achievements == null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = stringResource(R.string.noAchievement),
                    tint = Color.Gray
                )
                Text(
                    text = stringResource(R.string.noAchievement),
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

        } else {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                achievements.forEach { achievement ->
                    AchievementItem(
                        modifier = Modifier.fillMaxWidth(1f / 3f),
                        achievement = achievement,
                        onClick = { onItemClick(achievement) },
                    )
                }
            }
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    }


}

@Composable
fun AchievementItem(
    achievement: Achievement,
    onClick: (Achievement) -> Unit,
    modifier: Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .clickable { onClick(achievement) }
    ) {
        achievement.photo?.let { base64String ->
            val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
            )
        } ?: Image(
            painter = painterResource(id = R.drawable.baseline_photo_24),
            contentDescription = null,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(text = achievement.aname)
    }
}

@Composable
fun AchievementDetailDialog(
    achievementData: Achievement,
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
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.close)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                achievementData.photo?.let { base64String ->

                    val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(90.dp)
                            .padding(bottom = 8.dp)
                    )
                } ?: Image(
                    painter = painterResource(id = R.drawable.baseline_photo_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = achievementData.aname,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = achievementData.content,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = stringResource(R.string.finishDate) + " ${achievementData.finishtime}",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
