package com.example.healthhelper.signuplogin

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.healthhelper.attr.color.defaultcolor.DefaultColorViewModel
import java.time.Instant
import java.time.ZoneId

@Composable
fun SignUpScreen(navController: NavHostController = rememberNavController()) {
    val MALE = stringResource(R.string.male)
    val FEMALE = stringResource(R.string.female)
    val NOT_PROVIDED = stringResource(R.string.not_provided)

    var passwordErrorMessage by remember { mutableStateOf("") }
    var account by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) } // 控制性別下拉選單展開狀態
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var isNutritionist by remember { mutableStateOf(false) }
    var certificate by remember { mutableStateOf("") }
    LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                modifier = Modifier.padding(vertical = 16.dp)
            )

            TextField(
                value = account,
                onValueChange = { account = it },
                label = { Text("帳號") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(3.dp, Color(0xFF0174DB), RoundedCornerShape(16.dp)),
                colors = DefaultColorViewModel.outlinedTextFieldDefaultColors,
                /*colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color(0xFF0174DB),
                    focusedBorderColor = Color.Transparent, // 聚焦時底線顏色
                    unfocusedBorderColor = Color.Transparent, // 未聚焦時底線顏色
                    backgroundColor = Color.White
                )
                 */
            )

            TextField(
                value = password,
                onValueChange = { newPassword ->
                    password = newPassword
                    // 檢查確認密碼是否與密碼相同
                    passwordErrorMessage =
                        if (confirmPassword.isNotEmpty() && confirmPassword != password) {
                            "密碼錯誤"
                        } else {
                            ""
                        }
                },
                label = { Text("密碼") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = if (passwordVisible) painterResource(id = R.drawable.iconbkshow) else painterResource(
                                id = R.drawable.iconbknoshow
                            ),
                            contentDescription = if (passwordVisible) stringResource(R.string.hide_password) else stringResource(
                                R.string.show_password
                            ),
                            modifier = Modifier.size(20.dp),
                            tint = Color.Gray
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(3.dp, Color(0xFF0174DB), RoundedCornerShape(16.dp)),
                colors = DefaultColorViewModel.outlinedTextFieldDefaultColors,
                /*
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color(0xFF0174DB),
                    backgroundColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    )

                 */
            )

            // 確認密碼輸入框
            TextField(
                value = confirmPassword,
                onValueChange = { newConfirmPassword ->
                    confirmPassword = newConfirmPassword
                    passwordErrorMessage = if (confirmPassword != password) {
                        "密碼錯誤"
                    } else {
                        ""
                    }
                },
                label = {
                    Text(
                        "確認密碼",
                    )
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            painter = if (confirmPasswordVisible) painterResource(id = R.drawable.iconbkshow) else painterResource(
                                id = R.drawable.iconbknoshow
                            ),
                            contentDescription = if (confirmPasswordVisible) "隱藏密碼" else "顯示密碼",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Gray // 保持圖標顏色不變
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(3.dp, Color(0xFF0174DB), RoundedCornerShape(16.dp)),
                colors = DefaultColorViewModel.outlinedTextFieldDefaultColors,
                /*
                colors = TextFieldDefaults.outlinedTextFieldColors(

                    focusedLabelColor = Color(0xFF0174DB),
                    backgroundColor = Color.White,
                    unfocusedBorderColor = Color.Transparent, // 設置未聚焦時底線顏色為透明
                    focusedBorderColor = Color.Transparent,   // 設置聚焦時底線顏色為透明
                    errorBorderColor = Color.Transparent,
                    errorLabelColor = Color.Red
                   ),
                 */
                isError = passwordErrorMessage.isNotEmpty() // 使用 `isError` 屬性來控制錯誤訊息
            )

            // 密碼錯誤訊息
            if (passwordErrorMessage.isNotEmpty()) {
                Text(
                    text = passwordErrorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 1.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("姓名") },
                    modifier = Modifier
                        .weight(1f)
                        .border(3.dp, Color(0xFF0174DB), RoundedCornerShape(16.dp))
                        .padding(end = 8.dp),
                    colors = DefaultColorViewModel.outlinedTextFieldDefaultColors,
                    /*
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = Color(0xFF0174DB),
                        focusedBorderColor = Color.Transparent, // 聚焦時底線顏色
                        unfocusedBorderColor = Color.Transparent, // 未聚焦時底線顏色
                        backgroundColor = Color.White
                    )
                     */
                )
                Spacer(modifier = Modifier.width(16.dp))
                // 性別選擇下拉式選單
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(3.dp, Color(0xFF0174DB), RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .clickable { expanded = true }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = gender.ifEmpty { "性別" },
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                gender = MALE
                                expanded = false
                            },
                            text = { Text(MALE) },
                        )
                        DropdownMenuItem(
                            onClick = {
                                gender = FEMALE
                                expanded = false
                            },
                            text = { Text(FEMALE) },
                        )
                        DropdownMenuItem(
                            onClick = {
                                gender = NOT_PROVIDED
                                expanded = false
                            },
                            text = { Text(NOT_PROVIDED) }
                        )
                    }
                }
            }

            var errorMessage by remember { mutableStateOf("") }

            TextField(
                value = phone,
                onValueChange = { newPhone ->
                    // 更新 phone 狀態，並進行驗證
                    phone = newPhone
                    // 驗證電話格式：必須以 "09" 開頭，且長度為 10 碼
                    errorMessage = if (newPhone.startsWith("09") && newPhone.length == 10) {
                        ""  // 清除錯誤訊息
                    } else if (newPhone.isNotEmpty()) {
                        "電話號碼必須以09開頭且為10碼"
                    } else {
                        ""  // 當輸入框為空時清除錯誤訊息
                    }
                },
                label = { Text("電話") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(3.dp, Color(0xFF0174DB), RoundedCornerShape(16.dp)),
                colors = DefaultColorViewModel.outlinedTextFieldDefaultColors,
                /*
                colors = TextFieldDefaults.outlinedTextFieldColors(

                    focusedLabelColor = Color(0xFF0174DB),   // 聚焦時標籤顏色
                    backgroundColor = Color.White,           // 背景顏色
                    cursorColor = Color.Black,               // 游標顏色
                    focusedBorderColor = Color.Transparent,  // 聚焦時底線顏色（設為透明）
                    unfocusedBorderColor = Color.Transparent, // 未聚焦時底線顏色（設為透明）
                    errorBorderColor = Color.Transparent,    // 錯誤狀態下底線顏色（設為透明）
                    errorLabelColor = Color.Red              // 錯誤狀態下標籤顏色
                ),
                 */
                isError = errorMessage.isNotEmpty()  // 根據錯誤訊息是否存在來設定輸入框的錯誤狀態
            )

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,  // 顯示錯誤訊息為紅色
                    modifier = Modifier.padding(top = 1.dp)  // 設置錯誤訊息與輸入框之間的間距
                )
            }
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("信箱") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(3.dp, Color(0xFF0174DB), RoundedCornerShape(16.dp)),
                colors = DefaultColorViewModel.outlinedTextFieldDefaultColors,
                /*
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color(0xFF0174DB),
                    focusedBorderColor = Color.Transparent, // 聚焦時底線顏色
                    unfocusedBorderColor = Color.Transparent, // 未聚焦時底線顏色
                    backgroundColor = Color.White
                )
                 */
            )

            TextField(
                value = birthDate,
                onValueChange = { birthDate = it },
                label = { Text("生日") },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {  // 將日曆的彈出行為綁定在按鈕上
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
                    .border(3.dp, Color(0xFF0174DB), RoundedCornerShape(16.dp)),
                colors = DefaultColorViewModel.outlinedTextFieldDefaultColors,
                /*
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color(0xFF0174DB),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    backgroundColor = Color.White
                )
                 */
            )

            if (showDatePicker) {
                MyDatePickerDialog(
                    onConfirm = { selectedDateMillis ->
                        selectedDateMillis?.let {
                            val selectedDate =
                                Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            birthDate = selectedDate.toString()
                        }
                        showDatePicker = false
                    },
                    onDismiss = { showDatePicker = false }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp) // 調整左右內縮距離
            ) {
                Text(
                    "註冊身份",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(end = 16.dp),
                    color = Color.Gray
                ) // 增加右側內縮距離
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        RadioButton(
                            selected = !isNutritionist,
                            onClick = { isNutritionist = false },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Gray, // 設置選中時的顏色
                                unselectedColor = Color.Gray // 設置未選中時的顏色
                            )
                        )
                        Text("一般用戶", fontSize = 16.sp, color = Color.Gray) // 調整文字大小
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        RadioButton(
                            selected = isNutritionist,
                            onClick = { isNutritionist = true },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Gray, // 設置選中時的顏色
                                unselectedColor = Color.Gray // 設置未選中時的顏色
                            )
                        )
                        Text("營養師", fontSize = 16.sp, color = Color.Gray) // 調整文字大小
                    }
                }
            }
            if (isNutritionist) {
                TextField(
                    value = certificate,
                    onValueChange = { certificate = it },
                    label = { Text("營養師證書") },
                    trailingIcon = {
                        IconButton(onClick = {
                            // 處理點擊上傳事件
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.upload), // 替換成實際的圖標資源
                                contentDescription = "上傳證書",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(3.dp, Color(0xFF0174DB), RoundedCornerShape(16.dp)),
                    colors = DefaultColorViewModel.outlinedTextFieldDefaultColors,
                    /*
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = Color(0xFF0174DB),
                        focusedBorderColor = Color.Transparent, // 聚焦時底線顏色
                        unfocusedBorderColor = Color.Transparent, // 未聚焦時底線顏色
                        backgroundColor = Color.White
                    )
                     */
                )
            }

            Button(
                {
                    navController.navigate("LoginScreen")
                },
                modifier = Modifier
                    .width(150.dp)
                    .padding(vertical = 16.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF0174DB))
            ) {
                Text(
                    text = stringResource(R.string.signup_button),
                    fontSize = 16.sp,
                    color = Color.White
                )
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
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(datePickerState.selectedDateMillis)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0174DB) // 更改背景顏色
                )
            ) {
                Text(
                    text = stringResource(R.string.confirm_option_text),
                    color = Color.White // 更改文字顏色
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red // 更改取消按鈕背景顏色
                )
            ) {
                Text(
                    text = stringResource(R.string.cancel_option_text),
                    color = Color.White // 更改文字顏色
                )
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSignUp() {

    SignUpScreen()
}
