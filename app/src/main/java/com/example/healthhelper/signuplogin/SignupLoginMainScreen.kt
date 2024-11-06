package com.example.healthhelper.signuplogin
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.StateFlow


@Composable
fun LoginMain() {
    val navController = rememberNavController()
    val loginViewModel: LoginVM = androidx.lifecycle.viewmodel.compose.viewModel()

    NavHost(navController = navController, startDestination = "LoginScreen") {
        composable("LoginScreen") {
            LoginScreen(navController)
        }
        composable("SignUpScreen") {
            SignUpScreen(navController)
        }

        composable("UpdateInfoScreen") {
            // 從 UserManager 獲取用戶資料
            val user = UserManager.getUser() ?: User()
            UpdateInfoScreen(user, navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginMain() {
    LoginMain()
}
