package com.example.healthhelper.dietary.components.bar.appbar.topappbar

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.dietary.components.iconbutton.navigationicon.HamburgerNavigationIcon
import com.example.healthhelper.dietary.gson.toJson
import com.example.healthhelper.dietary.util.sharedata.ShareData
import com.example.healthhelper.dietary.viewmodel.DiaryViewModel


@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietAppTopBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    ),
    hasShareButton: Boolean = false,
) {
    val currentContext = LocalContext.current
    val diaries by remember { mutableStateOf(DiaryViewModel.diaries) }
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.diet_app_title),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = {
            HamburgerNavigationIcon()
        },

        actions = {
            if (
                hasShareButton
            ) {
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

        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        scrollBehavior = scrollBehavior,
    )
}