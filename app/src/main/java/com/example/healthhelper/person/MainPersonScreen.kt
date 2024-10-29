package com.example.healthhelper.person

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
    var imageUri by remember { mutableStateOf<Uri?>(null) }

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
                    onPictureTaken = { uri: Uri? ->
                        imageUri = uri
                        navController.navigate(PersonScreenEnum.photoPreviewScreen.name)
                    },
                    onCancelClick = {
                        imageUri = null
                        navController.popBackStack(PersonScreenEnum.personScreen.name, false)
                    }
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
            AchievementScreen(navController = navController)
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
