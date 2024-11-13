package com.example.healthhelper.dietary.frame


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.dietary.components.bar.appbar.topappbar.DietAppTopBar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchHintFrame(
    navController: NavHostController,
){
    Scaffold(
        topBar = {
            DietAppTopBar(
                onClick = {navController.navigateUp()},
                title = {
                    Text(
                        text = stringResource(R.string.diet_app_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                hasShareButton = false,

            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(color = colorResource(R.color.backgroundcolor)),
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