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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.person.personVM.AchievementViewModel
import com.example.healthhelper.person.personVM.CloudPhotoUploadVM
import com.example.healthhelper.person.personVM.UserPhotoUploadVM
import com.example.healthhelper.person.personVM.WeightViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun MainPersonScreen(
    navController: NavHostController = rememberNavController(),
    weightViewModel: WeightViewModel = viewModel(),
    achievementVM: AchievementViewModel = viewModel(),
    cloudPhotoUploadVM: CloudPhotoUploadVM = viewModel(),
    userPhotoUploadVM: UserPhotoUploadVM = viewModel(),
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    NavHost(
        navController = navController,
        startDestination = PersonScreenEnum.personScreen.name,
    ) {
        composable(route = PersonScreenEnum.personScreen.name) {
            PersonScreen(
                navController = navController,
                achievementVM = achievementVM,
                userPhotoUploadVM = userPhotoUploadVM
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
        composable(route = PersonScreenEnum.photoPreviewScreen.name) {
            PhotoPreviewScreen(
                imageUri = imageUri,
                onAcceptClick = {
                    navController.popBackStack(
                        PersonScreenEnum.personScreen.name,
                        false
                    )
                },
                onRejectClick = { navController.popBackStack() }
            )
        }
        composable(route = PersonScreenEnum.pickPhotoScreen.name) {
            PickPhotoScreen(
                navController,
                cloudPhotoUploadVM = cloudPhotoUploadVM,
                userPhotoUploadVM = userPhotoUploadVM
            )
        }
        composable(route = PersonScreenEnum.weightScreen.name) {
            WeightScreen(navController, weightViewModel = weightViewModel)
        }
        composable(route = PersonScreenEnum.weightSettingScreen.name) {
            WeightSettingScreen(navController, weightViewModel = weightViewModel)
        }
        composable(route = "${PersonScreenEnum.weightReviseScreen.name}/{recordId}") { backStackEntry ->
            val recordId = backStackEntry.arguments?.getString("recordId")
            WeightReviseScreen(
                navController,
                weightViewModel = weightViewModel,
                recordId = recordId
            )
        }
        composable(route = PersonScreenEnum.achivementScreen.name) {
            AchievementScreen(achievementVM = achievementVM, navController = navController)
        }

    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestCameraPermission(
    onGrant: @Composable () -> Unit,
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
