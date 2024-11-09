package com.example.healthhelper.signuplogin

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import java.time.Instant
import java.time.ZoneId


@Composable
fun UpdateInfoScreen(
//    user: User,
    navController: NavHostController = rememberNavController(),
    viewModel: UpdateInfoVM ,
    loginVM: LoginVM
) {
    val uiState by viewModel.uiState.collectAsState()
    val loginState by loginVM.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    // 當登入的用戶資料改變時，更新表單資料
    LaunchedEffect(loginState.loggedInUser) {
        Log.d("UpdateInfoScreen", """
           Login state changed:
           User: ${loginState.loggedInUser}
           Account: ${loginState.loggedInUser?.account}
       """.trimIndent())

        loginState.loggedInUser?.let { user ->
            Log.d("UpdateInfoScreen", "Setting user data to UpdateInfoVM")
            viewModel.setUserData(user)
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
            .offset(y = 100.dp)
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
                text = "個人資料修改",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD75813),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                TextField(
                    value = uiState.formState.username,
                    onValueChange = { viewModel.updateUsername(it) },
                    label={ Text("姓名") },
                   // placeholder = { Text("XXX") },
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
                isError = uiState.formState.phoneErrorMessage.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                colors = textFieldColors
            )

//            if (uiState.formState.phoneErrorMessage.isNotEmpty()) {
//                Text(
//                    text = uiState.formState.phoneErrorMessage,
//                    color = Color.Red,
//                    modifier = Modifier.padding(top = 1.dp)
//                )
//            }

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

            if (uiState.formState.emailErrorMessage.isNotEmpty()) {
                Text(
                    text = uiState.formState.emailErrorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 1.dp)
                )
            }

            // 生日
            TextField(
                value = uiState.formState.birthDate,
                onValueChange = { /* 由日期選擇器控制 */ },
                label = { Text("生日") },
                trailingIcon = {
                    IconButton(onClick = { viewModel.toggleDatePicker() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.calender),
                            contentDescription = "選擇日期",
                            modifier = Modifier.size(18.dp),
                            tint=Color.Gray
                        )
                    }
                },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color(0xFFF19204), RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                colors = textFieldColors,

            )

            // 日期選擇器
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()  // 讓 Row 佔滿寬度
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                // 取消按鈕
                Button(
                    onClick = {
                        navController.navigateUp() // 返回上一頁
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(48.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFAEAD1))
                ) {
                    Text(text = "取消", fontSize = 18.sp, color = Color(0xFFD75813))
                }

                Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    viewModel.submitForm(
                        context = context,
                        onSuccess = {
                            Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show()
                            navController.navigate("LoginScreen") {

                            }
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
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD75813)),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text(text = "修改", fontSize = 16.sp, color = Color.White)

                     }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUpdate() {
    //UpdateInfoScreen()
}