package com.example.healthhelper.signuplogin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthhelper.R

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: LoginVM,
) {
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    val textFieldColors = TextFieldDefaults.colors(
        errorContainerColor = Color(0xFFFFCDD2),  // 淺紅色代表錯誤
        focusedIndicatorColor = Color(0xFFD75813),
        unfocusedIndicatorColor = Color(0xFFD75813),
        unfocusedContainerColor = Color.White,
        focusedContainerColor = Color.White,
        focusedLabelColor = Color.Gray, // 標籤在聚焦時的顏色
        // unfocusedLabelColor = Color.Gray // 標籤在未聚焦時的顏色
    )

    val encryptedPreferences = getEncryptedPreferences(context)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDB86A))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(150.dp))

            Text(
                text = "會員登入",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // 帳號輸入框
            TextField(
                value = uiState.formState.account,
                onValueChange = { viewModel.updateAccount(it) },
                label = { Text("帳號") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                colors = textFieldColors
            )

            // 密碼輸入框
            TextField(
                value = uiState.formState.password,
                onValueChange = { viewModel.updatePassword(it) },
                label = { Text("密碼") },
                visualTransformation = if (uiState.formState.passwordVisible)
                    VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                        Icon(
                            painter = if (uiState.formState.passwordVisible)
                                painterResource(id = R.drawable.eyeshow)
                            else painterResource(id = R.drawable.eyenoshow),
                            contentDescription = if (uiState.formState.passwordVisible)
                                "隱藏密碼" else "顯示密碼",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Gray
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 按鈕區域
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 註冊按鈕
                Button(
                    onClick = {
//                        navController.navigate("SignUpScreen")
                        onRegisterClick()
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(48.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    colors = ButtonDefaults.buttonColors(Color(0xFFD75813))
                ) {
                    Text(text = "註冊", fontSize = 18.sp, color = Color.White)
                }

                // 登入按鈕
                Button(
                    onClick = {
                        viewModel.submitLogin(
                            context = context,
                            onSuccess = { userId ->
                                // 登入成功後直接導航到更新頁面
//                                navController.navigate("Main")
                                encryptedPreferences.edit()
                                    .putString("account", uiState.formState.account)
                                    .putString("password", uiState.formState.password)
                                    .apply()
                                onLoginSuccess()
                                Toast.makeText(context, "登入成功", Toast.LENGTH_SHORT).show()
                            },
                            onError = { error ->
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(48.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFAEAD1)),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color(0xFFD75813)
                        )
                    } else {
                        Text(text = "登入", fontSize = 18.sp, color = Color(0xFFD75813))
                    }
                }
            }
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun LoginPreview() {
//    val navController = rememberNavController()
//    LoginScreen(navController = navController)
//}