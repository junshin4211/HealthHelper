package com.example.healthhelper.signuplogin

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import java.time.Instant
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignUpScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: SignUpViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        backgroundColor = Color.White,
        focusedBorderColor = Color(0xFFD75813),
        unfocusedBorderColor = Color(0xFFD75813),
        focusedLabelColor = Color.Gray,
        unfocusedLabelColor = Color.Gray
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

            // 密碼錯誤訊息
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
                        .border(3.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
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
                        DropdownMenuItem(onClick = {
                            viewModel.updateGender("男")
                        }) { Text("男") }
                        DropdownMenuItem(onClick = {
                            viewModel.updateGender("女")
                        }) { Text("女") }
                        DropdownMenuItem(onClick = {
                            viewModel.updateGender("不提供")
                        }) { Text("不提供") }
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
                    .border(3.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
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

            // 信箱
            TextField(
                value = uiState.formState.email,
                onValueChange = { viewModel.updateEmail(it) },
                label = { Text("信箱") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(3.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                colors = textFieldColors
            )

            // 生日
            TextField(
                value = uiState.formState.birthDate,
                onValueChange = { viewModel.updateBirthDate(it) },
                label = { Text("生日") },
                trailingIcon = {
                    IconButton(onClick = { viewModel.toggleDatePicker() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.calender),
                            contentDescription = "生日",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(3.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                colors = textFieldColors
            )

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

            // 註冊身份
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text(
                    "註冊身份",
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

            // 營養師證書
            if (uiState.formState.isNutritionist) {
                var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
                val pickFileLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    selectedFileUri = uri
                    viewModel.updateCertificate(uri)
                }

                TextField(
                    value = uiState.formState.certificate,
                    onValueChange = { viewModel.updateCertificate(null) },
                    label = { Text("營養師證書") },
                    trailingIcon = {
                        IconButton(onClick = { pickFileLauncher.launch("*/*") }) {
                            Icon(
                                painter = painterResource(id = R.drawable.upload),
                                contentDescription = "上傳證書",
                                modifier = Modifier.size(20.dp)
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
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD75813)),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    androidx.compose.material3.CircularProgressIndicator(
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
