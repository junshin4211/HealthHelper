package com.example.healthhelper.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.R
import com.example.healthhelper.dietary.frame.DietDiaryMainFrame
import com.example.healthhelper.main.TabViewModel
import com.example.healthhelper.ui.theme.HealthHelperTheme
import com.example.ihealth.MainMapSearchScreen

@RequiresApi(Build.VERSION_CODES.R)
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
//                1 -> DietDiaryMainFrame()
//                2 -> Community()
//                3 -> Plan()
                4 -> MainMapSearchScreen()
//            }
            }

            if (tabVisibility.value) {
                TabRow(
                    selectedTabIndex = tabIndex,
                    containerColor = colorResource(id = R.color.blue01),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            text = {
                                Text(
                                    title, fontSize = 10.sp
                                )
                            },
                            selected = tabIndex == index,
                            onClick = { tabIndex = index },
                            selectedContentColor = colorResource(id = R.color.white),
                            icon = {
                                when (index) {
                                    0 -> Icon(
                                        painter = painterResource(R.drawable.management),
                                        contentDescription = title
                                    )

                                    1 -> Icon(
                                        painter = painterResource(R.drawable.community),
                                        contentDescription = title
                                    )

                                    2 -> Icon(
                                        painter = painterResource(R.drawable.diary),
                                        contentDescription = title
                                    )

                                    3 -> Icon(
                                        painter = painterResource(R.drawable.plan),
                                        contentDescription = title
                                    )

                                    4 -> Icon(
                                        painter = painterResource(R.drawable.map),
                                        contentDescription = title
                                    )

                                }
                            }
                        )
                    }

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