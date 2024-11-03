package com.example.healthhelper.dietary.components.bar.appbar.topappbar

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.attr.viewmodel.DefaultColorViewModel
import com.example.healthhelper.dietary.components.iconbutton.navigationicon.NavigateUpNavigationIcon
import com.example.healthhelper.dietary.gson.toJson
import com.example.healthhelper.dietary.util.sharedata.ShareData
import com.example.healthhelper.dietary.viewmodel.DiaryViewModel


@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietAppTopBar(
    navController: NavHostController,
    diaryViewModel: DiaryViewModel = viewModel(),
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    ),
    hasShareButton: Boolean = false,
) {
    val currentContext = LocalContext.current
    val diaries by diaryViewModel.data.collectAsState()
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = DefaultColorViewModel.topAppBarColors,
        title = title,
        navigationIcon = {
            NavigateUpNavigationIcon(navController)
        },

        actions = {
            if (hasShareButton) {
                IconButton(
                    onClick = {
                        val jsonString = diaries.toJson()
                        ShareData(
                            currentContext,
                            data = jsonString,
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = stringResource(R.string.share_icon),
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )
}