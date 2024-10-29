package com.example.healthhelper.signuplogin
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController



@Composable
fun LoginMain() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "LoginScreen") {
        composable("LoginScreen") {
            LoginScreen(navController)
        }
        composable("SignUpScreen") {
            SignUpScreen(navController)
        }

//        composable("Plan") {
//           Plan()
//        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginMain() {
    LoginMain()
}
