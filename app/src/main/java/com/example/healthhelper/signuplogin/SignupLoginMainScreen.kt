package com.example.healthhelper.signuplogin

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import coil.compose.AsyncImage
import com.example.healthhelper.R
import com.example.healthhelper.person.personVM.LoginState
import com.example.healthhelper.screen.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun LoginMain() {
    val context = LocalContext.current
    var showRegister by remember { mutableStateOf(false) }
    var isLogin by remember { LoginState.isLogin }
    val encryptedPreferences = getEncryptedPreferences(context)
    val loginVM: LoginVM = viewModel()
    var isLoading by remember { mutableStateOf(false) }

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
                    Log.d("Auto-login failed::", "$errorMessage")
                }
            )
        } else {
            isLogin = false
        }
    }
    if (isLogin == null) {
        isLoading = true
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.backgroundcolor)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            RotatingImage(
                painterResource = painterResource(R.drawable.logoremoveback),
                contentDescription = stringResource(R.string.logo)
            )
        }
    } else if (isLogin == true) {
        isLoading = false
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
                onRegisterSuccess = { showRegister = false },
                onBackToLogin = { },
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

@Composable
fun RotatingImage(
    painterResource: androidx.compose.ui.graphics.painter.Painter,
    contentDescription: String?,
    rotationSpeed: Long = 1000L, // 控制旋轉速度 (毫秒)
) {
    var rotationAngle by remember { mutableStateOf(0f) }

    // 旋轉動畫效果
    val animatedAngle by animateFloatAsState(
        targetValue = rotationAngle,
        animationSpec = tween(
            durationMillis = rotationSpeed.toInt(),
            easing = LinearEasing
        )
    )

    LaunchedEffect(Unit) {
            while (true) {
                rotationAngle += 360f
                delay(rotationSpeed)
            }
    }

    // 顯示旋轉的圖片
    Image(
        painter = painterResource,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(150.dp)
            .rotate(animatedAngle)
    )
}
//@Preview(showBackground = true)
//@Composable
//fun PreviewLoginMain() {
//    LoginMain()
//}
