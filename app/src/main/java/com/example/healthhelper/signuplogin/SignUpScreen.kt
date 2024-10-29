package com.example.healthhelper.signuplogin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignUpScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: SignUpViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    val textFieldColors = TextFieldDefaults.colors(
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Gray,
        disabledTextColor = Color.Gray,
        errorTextColor = Color.Red,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        disabledContainerColor = Color.LightGray,
        errorContainerColor = Color(0xFFFFCDD2),  // 淺紅色代表錯誤
        cursorColor = Color(0xFFD75813),
        errorCursorColor = Color.Red,
        focusedIndicatorColor = Color(0xFFD75813),
        unfocusedIndicatorColor = Color(0xFFD75813),
        disabledIndicatorColor = Color.Gray,
        errorIndicatorColor = Color.Red,
        focusedLeadingIconColor = Color.Black,
        unfocusedLeadingIconColor = Color.Gray,
        disabledLeadingIconColor = Color.LightGray,
        errorLeadingIconColor = Color.Red,
        focusedTrailingIconColor = Color.Black,
        unfocusedTrailingIconColor = Color.Gray,
        disabledTrailingIconColor = Color.LightGray,
        errorTrailingIconColor = Color.Red,
        focusedLabelColor = Color.Gray,
        unfocusedLabelColor = Color.Gray,
        disabledLabelColor = Color.LightGray,
        errorLabelColor = Color.Red,
        focusedPlaceholderColor = Color.Gray,
        unfocusedPlaceholderColor = Color.Gray,
        disabledPlaceholderColor = Color.LightGray,
        errorPlaceholderColor = Color.Red,
        focusedSupportingTextColor = Color.Gray,
        unfocusedSupportingTextColor = Color.Gray,
        disabledSupportingTextColor = Color.LightGray,
        errorSupportingTextColor = Color.Red,
        focusedPrefixColor = Color.Black,
        unfocusedPrefixColor = Color.Gray,
        disabledPrefixColor = Color.LightGray,
        errorPrefixColor = Color.Red,
        focusedSuffixColor = Color.Black,
        unfocusedSuffixColor = Color.Gray,
        disabledSuffixColor = Color.LightGray,
        errorSuffixColor = Color.Red
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFEED))
            .offset(y = 50.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.member_signup_title),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD75813),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // 帳號
            TextField(
                value = uiState.formState.account,
                onValueChange = { viewModel.updateAccount(it) },
                label = { Text("帳號") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(3.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                colors = textFieldColors
            )

            // 密碼
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
                    .border(3.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                colors = textFieldColors
            )

            // 確認密碼
            TextField(
                value = uiState.formState.confirmPassword,
                onValueChange = { viewModel.updateConfirmPassword(it) },
                label = { Text("確認密碼") },
                visualTransformation = if (uiState.formState.confirmPasswordVisible)
                    VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { viewModel.toggleConfirmPasswordVisibility() }) {
                        Icon(
                            painter = if (uiState.formState.confirmPasswordVisible)
                                painterResource(id = R.drawable.eyeshow)
                            else painterResource(id = R.drawable.eyenoshow),
                            contentDescription = if (uiState.formState.confirmPasswordVisible)
                                "隱藏密碼" else "顯示密碼",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Gray
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(3.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                colors = textFieldColors,
                isError = uiState.formState.passwordErrorMessage.isNotEmpty()
            )

            // 性別下拉選單
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(3.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .clickable { viewModel.toggleGenderDropdown() }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (uiState.formState.gender.isEmpty()) "性別"
                        else uiState.formState.gender,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.dropdown),
                        contentDescription = "下拉選單",
                        modifier = Modifier.size(20.dp)
                    )
                }

                DropdownMenu(
                    expanded = uiState.formState.expanded,
                    onDismissRequest = { viewModel.toggleGenderDropdown() }
                ) {
                    DropdownMenuItem(text = { Text("男") },
                        onClick = {
                            viewModel.updateGender("男")
                        })
                    DropdownMenuItem(text = { Text("女") },
                        onClick = {
                            viewModel.updateGender("女")
                        })
                    DropdownMenuItem(text = { Text("不提供") },
                        onClick = {
                            viewModel.updateGender("不提供")
                        })
                }
            }

            // 註冊按鈕
            Button(
                onClick = {
                    viewModel.submitForm(
                        onSuccess = {
                            navController.navigate("LoginScreen")
                        },
                        onError = { errorMessage ->
                            android.widget.Toast.makeText(
                                context,
                                errorMessage,
                                android.widget.Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                },
                modifier = Modifier
                    .width(150.dp)
                    .padding(vertical = 16.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFD75813)),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text(text = "註冊", fontSize = 16.sp, color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUp() {
    SignUpScreen()
}
