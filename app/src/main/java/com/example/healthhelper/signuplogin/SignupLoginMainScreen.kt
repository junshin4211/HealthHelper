package com.example.healthhelper.signuplogin
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.screen.Main


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun LoginMain() {
    val navController = rememberNavController()
  //  val loginViewModel: LoginVM = androidx.lifecycle.viewmodel.compose.viewModel()

    NavHost(navController = navController, startDestination = "LoginScreen") {
        composable("LoginScreen") {
            LoginScreen(navController)
        }

        composable("SignUpScreen") {
            SignUpScreen(navController)
        }

//        composable("UpdateInfoScreen") {
//            // 從 UserManager 獲取用戶資料
//            val user = UserManager.getUser() ?: User()
//            UpdateInfoScreen(user, navController)
//        }

        composable("Main") {
            val user = UserManager.getUser() ?: User()
            Main(user,navController)
        }


        composable("UpdateInfoScreen") {
            val user = UserManager.getUser() ?: User()
            UpdateInfoScreen(user,navController)
        }


    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewLoginMain() {
//    LoginMain()
//}
