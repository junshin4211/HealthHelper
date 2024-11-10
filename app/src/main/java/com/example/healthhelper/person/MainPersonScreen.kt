package com.example.healthhelper.person

import android.Manifest
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.healthhelper.screen.TabViewModel
import com.example.healthhelper.signuplogin.LoginScreen
import com.example.healthhelper.signuplogin.LoginVM
import com.example.healthhelper.signuplogin.UpdateInfoScreen
import com.example.healthhelper.signuplogin.UpdateInfoVM
import com.example.healthhelper.signuplogin.UserManager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun MainPersonScreen(
    onLogout: () -> Unit,
    navController: NavHostController = rememberNavController(),
    weightViewModel: WeightViewModel = viewModel(),
    userPhotoUploadVM: UserPhotoUploadVM = viewModel(),
    achievementVM: AchievementViewModel = viewModel(),
    cloudPhotoUploadVM: CloudPhotoUploadVM = viewModel(),
    tabViewModel: TabViewModel = viewModel(),
    loginVM: LoginVM = viewModel(),
    updateInfoVM: UpdateInfoVM = viewModel()
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var userId by remember { mutableIntStateOf(UserManager.getUser().userId ) }

    NavHost(
        navController = navController,
        startDestination = PersonScreenEnum.personScreen.name,
    ) {
        composable(route = PersonScreenEnum.personScreen.name) {
            PersonScreen(
                navController = navController,
                achievementVM = achievementVM,
                userPhotoUploadVM = userPhotoUploadVM,
                tabViewModel = tabViewModel,
                onLogout = onLogout
            )
        }
        composable(route = PersonScreenEnum.cameraPreviewScreen.name) {
            RequestCameraPermission(onGrant = {
                CameraPreviewScreen(
                    onPictureTaken = { uri: Uri? ->
                        imageUri = uri
                        navController.navigate(PersonScreenEnum.photoPreviewScreen.name)
                    },
                    navController = navController,
                    tabViewModel = tabViewModel
                )
            })
        }
        composable(route = PersonScreenEnum.photoPreviewScreen.name) {
            PhotoPreviewScreen(
                navController = navController,
                imageUri = imageUri,
                onRejectClick = { navController.popBackStack() },
                userPhotoUploadVM = userPhotoUploadVM,
                cloudPhotoUploadVM = cloudPhotoUploadVM,
            )
        }
        composable(route = PersonScreenEnum.updateInfoScreen.name) {
//            val viewModel: UpdateInfoVM = viewModel()
//            val loginVM: LoginVM = viewModel()
            UpdateInfoScreen(
//                user: User,
                navController = navController,
                viewModel = updateInfoVM,
                loginVM = loginVM,
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
            WeightSettingScreen(userId = userId, navController, weightViewModel = weightViewModel)
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
