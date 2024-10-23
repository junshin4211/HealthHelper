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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
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
import java.time.temporal.ChronoUnit


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignUpScreen(navController: NavHostController = rememberNavController()) {
    val formState = remember { mutableStateOf(SignUpProperty()) }
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
                value = formState.value.account,
                onValueChange = { formState.value = formState.value.copy(account = it) },
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
                value = formState.value.password,
                onValueChange = { newPassword ->
                    formState.value = formState.value.copy(
                        password = newPassword,
                        passwordErrorMessage = if (formState.value.confirmPassword.isNotEmpty() && formState.value.confirmPassword != newPassword) {
                            "密碼錯誤"
                        } else {
                            ""
                        }
                    )
                },
                label = { Text("密碼") },
                visualTransformation = if (formState.value.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { formState.value = formState.value.copy(passwordVisible = !formState.value.passwordVisible) }) {
                        Icon(
                            painter = if (formState.value.passwordVisible) painterResource(id = R.drawable.iconbkshow) else painterResource(id = R.drawable.iconbknoshow),
                            contentDescription = if (formState.value.passwordVisible) "隱藏密碼" else "顯示密碼",
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
                value = formState.value.confirmPassword,
                onValueChange = { newConfirmPassword ->
                    formState.value = formState.value.copy(
                        confirmPassword = newConfirmPassword,
                        passwordErrorMessage = if (formState.value.confirmPassword != formState.value.password) {
                            "密碼錯誤"
                        } else {
                            ""
                        }
                    )
                },
                label = { Text("確認密碼") },
                visualTransformation = if (formState.value.confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { formState.value = formState.value.copy(confirmPasswordVisible = !formState.value.confirmPasswordVisible) }) {
                        Icon(
                            painter = if (formState.value.confirmPasswordVisible) painterResource(id = R.drawable.iconbkshow) else painterResource(id = R.drawable.iconbknoshow),
                            contentDescription = if (formState.value.confirmPasswordVisible) "隱藏密碼" else "顯示密碼",
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
                isError = formState.value.passwordErrorMessage.isNotEmpty()
            )

            // 密碼錯誤訊息
            if (formState.value.passwordErrorMessage.isNotEmpty()) {
                Text(
                    text = formState.value.passwordErrorMessage,
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
                    value = formState.value.username,
                    onValueChange = { formState.value = formState.value.copy(username = it) },
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
                        .clickable { formState.value = formState.value.copy(expanded = true) }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (formState.value.gender.isEmpty()) "性別" else formState.value.gender,
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
                        expanded = formState.value.expanded,
                        onDismissRequest = { formState.value = formState.value.copy(expanded = false) }
                    ) {
                        DropdownMenuItem(onClick = {
                            formState.value = formState.value.copy(gender = "男", expanded = false)
                        }) { Text("男") }
                        DropdownMenuItem(onClick = {
                            formState.value = formState.value.copy(gender = "女", expanded = false)
                        }) { Text("女") }
                        DropdownMenuItem(onClick = {
                            formState.value = formState.value.copy(gender = "不提供", expanded = false)
                        }) { Text("不提供") }
                    }
                }
            }

            // 電話
            TextField(
                value = formState.value.phone,
                onValueChange = { newPhone ->
                    formState.value = formState.value.copy(
                        phone = newPhone,
                        phoneErrorMessage = if (newPhone.startsWith("09") && newPhone.length == 10) {
                            ""
                        } else if (newPhone.isNotEmpty()) {
                            "電話號碼必須以09開頭且為10碼"
                        } else {
                            ""
                        }
                    )
                },
                label = { Text("電話") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(3.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                colors = textFieldColors,
                isError = formState.value.phoneErrorMessage.isNotEmpty()
            )

            if (formState.value.phoneErrorMessage.isNotEmpty()) {
                Text(
                    text = formState.value.phoneErrorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 1.dp)
                )
            }

            // 信箱
            TextField(
                value = formState.value.email,
                onValueChange = { formState.value = formState.value.copy(email = it) },
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
                value = formState.value.birthDate,
                onValueChange = { formState.value = formState.value.copy(birthDate = it) },
                label = { Text("生日") },
                trailingIcon = {
                    IconButton(onClick = { formState.value = formState.value.copy(showDatePicker = true) }) {
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

            if (formState.value.showDatePicker) {
                MyDatePickerDialog(
                    onConfirm = { selectedDateMillis ->
                        selectedDateMillis?.let {
                            val selectedDate = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                            formState.value = formState.value.copy(birthDate = selectedDate.toString(), showDatePicker = false)
                        }
                    },
                    onDismiss = { formState.value = formState.value.copy(showDatePicker = false) }
                )
            }

            // 註冊身份
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text("註冊身份", fontSize = 16.sp, modifier = Modifier.padding(end = 16.dp), color = Color(0xFF555555))
                Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.SpaceAround) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        RadioButton(
                            selected = !formState.value.isNutritionist,
                            onClick = { formState.value = formState.value.copy(isNutritionist = false) },
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
                            selected = formState.value.isNutritionist,
                            onClick = { formState.value = formState.value.copy(isNutritionist = true) },
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
            if (formState.value.isNutritionist) {
                var selectedFileUri by remember { mutableStateOf<Uri?>(null) }

                // 文件選擇器啟動器，接受任何檔案類型
                val pickFileLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent(),
                    onResult = { uri: Uri? ->
                        selectedFileUri = uri
                        formState.value = formState.value.copy(certificate = uri?.lastPathSegment ?: "未選擇檔案")
                    }
                )

                TextField(
                    value = formState.value.certificate,
                    onValueChange = { formState.value = formState.value.copy(certificate = it) },
                    label = { Text("營養師證書") },
                    trailingIcon = {
                        IconButton(onClick = {
                            pickFileLauncher.launch("*/*")
                        }) {
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

            Button(
                onClick = {

                    navController.navigate("Plan")
                },
                modifier = Modifier
                    .width(150.dp)
                    .padding(vertical = 16.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD75813))
            ) {
                Text(text = "註冊", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onConfirm: (message: Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val today = Instant.now().truncatedTo(ChronoUnit.DAYS).toEpochMilli()
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            // 如果所選日期小於或等於今天，啟用「確認」按鈕
            androidx.compose.material3.Button(
                onClick = {
                    onConfirm(datePickerState.selectedDateMillis)
                },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = if (datePickerState.selectedDateMillis != null && datePickerState.selectedDateMillis!! <= today) {
                        Color(0xFFD75813)
                    } else {
                        Color.Gray // 禁用按鈕
                    }
                ),
                enabled = datePickerState.selectedDateMillis != null && datePickerState.selectedDateMillis!! <= today // 禁用超過今天的日期
            ) {
                androidx.compose.material3.Text(
                    text = "確認",
                    color = Color.White
                )
            }
        },
        dismissButton = {
            androidx.compose.material3.Button(
                onClick = onDismiss,
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFFEED)
                )
            ) {
                androidx.compose.material3.Text(
                    text = "取消",
                    color = Color(0xFFD75813)
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEFE9F4))
        ) {
            DatePicker(
                state = datePickerState,
                modifier = Modifier.background(Color(0xFFEFE9F4)),
                colors = DatePickerDefaults.colors(
                    selectedDayContentColor = Color.White,
                    selectedDayContainerColor = Color(0xFFF19204),
                    todayContentColor = Color(0xFFFFA500),
                    todayDateBorderColor = Color(0xFFFFA500),

                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUp() {
    SignUpScreen()
}
