package com.example.healthhelper.signuplogin

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import java.time.Instant
import java.time.ZoneId
import android.widget.Toast
import androidx.compose.ui.tooling.preview.Preview


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignUpScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: SignUpViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    // 檔案選擇器
    val pickFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.handleFileSelection(context, uri)
        }
    }

    val textFieldColors = TextFieldDefaults.colors(
        errorContainerColor = Color(0xFFFFCDD2),
        focusedIndicatorColor = Color(0xFFD75813),
        unfocusedIndicatorColor = Color(0xFFD75813),
        unfocusedContainerColor = Color.White,
        focusedContainerColor = Color.White,
        focusedLabelColor = Color.Gray, // 標籤在聚焦時的顏色
       // unfocusedLabelColor = Color.Gray // 標籤在未聚焦時的顏色
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFEED))
            .offset(y = 20.dp)
            .padding(bottom = 50.dp)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "會員註冊",
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
                    .border(2.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
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
                    .border(2.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
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
                    .border(2.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                colors = textFieldColors,
                isError = uiState.formState.passwordErrorMessage.isNotEmpty()
            )

            if (uiState.formState.passwordErrorMessage.isNotEmpty()) {
                Text(
                    text = uiState.formState.passwordErrorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 1.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 姓名
                TextField(
                    value = uiState.formState.username,
                    onValueChange = { viewModel.updateUsername(it) },
                    label = { Text("姓名") },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .border(2.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White),
                    colors = textFieldColors
                )

                Spacer(modifier = Modifier.width(16.dp))

                // 性別下拉選單
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(2.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
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
                            color = Color(0xFF40434B),
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
                        DropdownMenuItem(
                            text = { Text("男") },
                            onClick = { viewModel.updateGender("男") }
                        )
                        DropdownMenuItem(
                            text = { Text("女") },
                            onClick = { viewModel.updateGender("女") }
                        )
                        DropdownMenuItem(
                            text = { Text("不提供") },
                            onClick = { viewModel.updateGender("不提供") }
                        )
                    }
                }
            }

            // 電話
            TextField(
                value = uiState.formState.phone,
                onValueChange = { viewModel.updatePhone(it) },
                label = { Text("電話") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                colors = textFieldColors,
                isError = uiState.formState.phoneErrorMessage.isNotEmpty()
            )

            if (uiState.formState.phoneErrorMessage.isNotEmpty()) {
                Text(
                    text = uiState.formState.phoneErrorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 1.dp)
                )
            }

            // Email
            TextField(
                value = uiState.formState.email,
                onValueChange = { viewModel.updateEmail(it) },
                label = { Text("信箱") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                colors = textFieldColors
            )

            // 生日
            TextField(
                value = uiState.formState.birthDate,
                onValueChange = { /* Do nothing since this field is readonly */ },
                label = { Text("生日") },
                trailingIcon = {
                    IconButton(onClick = { viewModel.toggleDatePicker() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.calender),
                            contentDescription = "生日",
                            modifier = Modifier.size(18.dp),
                            tint = Color.Gray
                        )
                    }
                },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                colors = textFieldColors
            )

// Show the DatePickerDialog when needed
            if (uiState.formState.showDatePicker) {
                MyDatePickerDialog(
                    onConfirm = { selectedDateMillis ->
                        selectedDateMillis?.let {
                            val selectedDate = Instant.ofEpochMilli(it)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            viewModel.updateBirthDate(selectedDate.toString())
                        }
                    },
                    onDismiss = { viewModel.toggleDatePicker() }
                )
            }

            // 使用者身分選擇
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "註冊身份",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(end = 16.dp),
                    color = Color(0xFF555555)
                )
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        RadioButton(
                            selected = !uiState.formState.isNutritionist,
                            onClick = { viewModel.updateIsNutritionist(false) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF555555),
                                unselectedColor = Color(0xFF555555)
                            )
                        )
                        Text("一般用戶", fontSize = 16.sp, color = Color(0xFF555555))
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        RadioButton(
                            selected = uiState.formState.isNutritionist,
                            onClick = { viewModel.updateIsNutritionist(true) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF555555),
                                unselectedColor = Color(0xFF555555)
                            )
                        )
                        Text("營養師", fontSize = 16.sp, color = Color(0xFF555555))
                    }
                }
            }

            // 營養師證書上傳
            if (uiState.formState.isNutritionist) {
                TextField(
                    value = uiState.formState.certificate,
                    onValueChange = { /* 唯讀 */ },
                    label = { Text("營養師證書") },
                    trailingIcon = {
                        IconButton(onClick = { pickFileLauncher.launch("image/*") }) {
                            Icon(
                                painter = painterResource(id = R.drawable.upload),
                                contentDescription = "上傳證書",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Gray
                            )
                        }
                    },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White),
                    colors = textFieldColors
                )
            }

            // 並排的按鈕
            Row(
                modifier = Modifier
                    .fillMaxWidth()  // 讓 Row 佔滿寬度
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,  // 置中對齊
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 取消按鈕
                Button(
                    onClick = {
                        navController.navigate("LoginScreen") {
                         //   popUpTo("LoginScreen") { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(48.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFAEAD1))
                ) {
                    Text(
                        text = "取消",
                        fontSize = 16.sp,
                        color = Color(0xFFD75813)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))  // 按鈕之間的間距

                // 註冊按鈕
                Button(
                    onClick = {
                        viewModel.submitForm(
                            context = context,
                            onSuccess = {
                                Toast.makeText(context, "註冊成功", Toast.LENGTH_SHORT).show()
                                navController.navigate("LoginScreen")
                            },
                            onError = { errorMessage ->
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(48.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD75813)),
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
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUp() {
    SignUpScreen()
}
