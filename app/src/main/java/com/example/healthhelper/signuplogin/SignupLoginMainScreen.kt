package com.example.healthhelper.signuplogin

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.healthhelper.person.personVM.LoginState
import com.example.healthhelper.screen.Main


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun LoginMain() {
    val context = LocalContext.current
    var showRegister by remember { mutableStateOf(false) }
    var isLogin by remember { LoginState.isLogin }
    val encryptedPreferences = getEncryptedPreferences(context)
    val loginVM: LoginVM = viewModel()

    LaunchedEffect(Unit) {
        val savedAccount = encryptedPreferences.getString("account", "") ?: ""
        val savedPassword = encryptedPreferences.getString("password", "") ?: ""

        if (savedAccount.isNotEmpty() && savedPassword.isNotEmpty()) {
            loginVM.updateAccount(savedAccount)
            loginVM.updatePassword(savedPassword)
            loginVM.submitLogin(context,
                onSuccess = {
                    LoginState.isLogin.value = true
                },
                onError = { errorMessage ->
                    Log.d("Auto-login failed::","$errorMessage")
                }
            )
        }
    }
    if (isLogin) {
        Main(
            onLogout = {
                UserManager.clearUser()
                clearPreferences(context)
                (context as? ViewModelStoreOwner)?.viewModelStore?.clear()
                isLogin = false
                showRegister = false
            }
        )
    } else {
        if (showRegister) {
            SignUpScreen(
                onRegisterSuccess = { isLogin = true },
                onBackToLogin = { showRegister = false },
            )
        } else {
            LoginScreen(
                viewModel = loginVM,
                onLoginSuccess = { isLogin = true },
                onRegisterClick = { showRegister = true }
            )
        }
    }
}
fun getEncryptedPreferences(context: Context): SharedPreferences {
    val masterKeyAlias = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    return EncryptedSharedPreferences.create(
        context,
        "encrypted_user",
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}
private fun clearPreferences(context: Context) {
    val sharedPreferences = getEncryptedPreferences(context)
    sharedPreferences.edit()
        .remove("account")
        .remove("password")
        .apply()
}
//@Preview(showBackground = true)
//@Composable
//fun PreviewLoginMain() {
//    LoginMain()
//}
