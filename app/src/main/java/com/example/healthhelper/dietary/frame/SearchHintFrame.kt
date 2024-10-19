package com.example.healthhelper.dietary.frame

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.dietary.components.bar.appbar.topappbar.DietAppTopBar
import com.example.healthhelper.screen.Main

@RequiresApi(Build.VERSION_CODES.R)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchHintFrame(
    navController: NavHostController,
){
    Scaffold(
        topBar = {
            DietAppTopBar(
                navController = navController,
                hasShareButton = false,
            )
        },
        bottomBar = {
            Main()
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Text(
                    text = stringResource(R.string.search_hint_title),
                    fontSize = 36.sp,
                )

                Text(
                    text = stringResource(R.string.search_algorithm_name),
                    fontSize = 26.sp,
                )

                Text(
                    text = stringResource(R.string.search_algorithm_intro),
                    fontSize = 18.sp,
                )
            }
        }
    )
}