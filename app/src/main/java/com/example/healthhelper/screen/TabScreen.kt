package com.example.healthhelper.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.R
import com.example.healthhelper.dietary.frame.DietDiaryMainFrame
import com.example.healthhelper.dietary.screen.DietDiaryMainScreen
import com.example.healthhelper.healthyMap.MainMapSearchScreen
import com.example.healthhelper.ui.theme.HealthHelperTheme

@Composable
fun Main(tabViewModel: TabViewModel = viewModel()) {
    val tabVisibility = tabViewModel.tabVisibility.collectAsState()
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        stringResource(id = R.string.management),
        stringResource(id = R.string.community),
        stringResource(id = R.string.diary),
        stringResource(id = R.string.plan),
        stringResource(id = R.string.map)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            when (tabIndex) {
//                0 -> Management()
                //          1 -> Community()
                2 -> {
                    DietDiaryMainScreen()
                }
//                3 -> Plan()
                4 -> MainMapSearchScreen()
//            }
            }
        }
        if (tabVisibility.value) {
            TabRow(
                selectedTabIndex = tabIndex,
                containerColor = colorResource(id = R.color.footer),
                modifier = Modifier
                    .height(70.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 15.dp,
                            topEnd = 15.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                title,
                                fontSize = 10.sp,
                                fontWeight = FontWeight(600),
                                modifier = Modifier
                                    .scale(1.4f)
                                    .padding(top = 20.dp),
                            )
                        },
                        selected = tabIndex == index,
                        onClick = {
                            tabIndex = index
                        },
                        unselectedContentColor = colorResource(id = R.color.footericon),
                        selectedContentColor = colorResource(id = R.color.footerselect),
                        icon = {
                            when (index) {
                                0 -> Icon(
                                    painter = painterResource(R.drawable.management),
                                    contentDescription = title,
                                    modifier = Modifier
                                        .scale(1.2f)
                                )

                                1 -> Icon(
                                    painter = painterResource(R.drawable.community),
                                    contentDescription = title,
                                    modifier = Modifier
                                        .scale(1.2f)
                                )

                                2 -> Icon(
                                    painter = painterResource(R.drawable.diary),
                                    contentDescription = title,
                                    modifier = Modifier
                                        .scale(1.2f)
                                )

                                3 -> Icon(
                                    painter = painterResource(R.drawable.plan),
                                    contentDescription = title,
                                    modifier = Modifier
                                        .scale(1.2f)
                                )

                                4 -> Icon(
                                    painter = painterResource(R.drawable.map),
                                    contentDescription = title,
                                    modifier = Modifier
                                        .scale(1.2f)
                                )

                            }
                        }
                    )
                }

            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HealthHelperTheme {
        Main()
    }
}