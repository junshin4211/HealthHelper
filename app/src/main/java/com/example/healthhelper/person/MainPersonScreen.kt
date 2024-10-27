package com.example.healthhelper.person

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun MainPersonScreen(
    navController: NavHostController = rememberNavController(),
) {
//    val backStackEntry by navController.currentBackStackEntryAsState()
    NavHost(
        navController = navController,
        startDestination = PersonScreenEnum.personScreen.name,
    ) {
        composable(route = PersonScreenEnum.personScreen.name) {
            PersonScreen(
                navController = navController
            )
        }
        composable(route = PersonScreenEnum.cameraPreviewScreen.name) {
            RequestCameraPermission(onGrant = {
                CameraPreviewScreen(
                    navController = navController
                )
            })
        }
        composable(route = PersonScreenEnum.pickPhotoScreen.name) {
            PickPhotoScreen(navController)
        }
        composable(route = PersonScreenEnum.weightScreen.name) {
            WeightScreen(navController)
        }
        composable(route = PersonScreenEnum.weightSettingScreen.name) {
            WeightSettingScreen(navController)
        }
        composable(route = PersonScreenEnum.weightReviseScreen.name) {
            WeightReviseScreen(navController)
        }
        composable(route = PersonScreenEnum.achivementScreen.name) {
            AchievementScreen(navController)
        }

    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestCameraPermission(
    onGrant: @Composable () -> Unit
) {
    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )
    if (cameraPermissionState.status.isGranted) {
        onGrant()
    } else {
        LaunchedEffect(Unit) {
            cameraPermissionState.launchPermissionRequest()
        }
    }
}
