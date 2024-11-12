package com.example.healthhelper.community

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.community.components.CmtNavbarComponent
import com.example.healthhelper.ui.theme.HealthHelperTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun EditPostScreen(navController: NavHostController) {
    var title by remember { mutableStateOf("我的菜單分享") }
    var content by remember { mutableStateOf("今日菜單內容有.............................") }
    var showAlert by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.backgroundcolor))
    ) {
//        CmtNavbarComponent(navController = navController, postVM = postVM)
        Column(
            // 內容物水平置中
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // Profile row
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(48.dp),
                    colorResource(R.color.primarycolor)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = stringResource(id = R.string.userName),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.black_200)
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(id = R.drawable.trash_can),
                    contentDescription = "Delete",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            showAlert = true
                        },
                    colorResource(R.color.red_100)
                )
                if (showAlert) {
                    CustomDialog(
                        // 點擊對話視窗外部或 back 按鈕時呼叫
                        onDismissRequest = {
                            showAlert = false
                        },
                        // 設定確定時欲執行內容
                        onConfirm = {
                            //之後需寫刪除貼文這筆資料後並導到我的貼文頁
                            navController.navigate(CmtScreenEnum.MyPostsScreen.name)
                        },
                        // 設定取消時欲執行內容
                        onDismiss = {
                            showAlert = false
                        })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title TextField
            //Title字數限制尚未完成
            OutlinedTextField(value = title, onValueChange = { title = it }, placeholder = {
                Text(
                    text = title,
                    fontWeight = FontWeight(600),
                    color = colorResource(id = R.color.black_200)
                )
            }, modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.gray_300),
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .background(
                    color = colorResource(id = R.color.gray_200),
                    shape = RoundedCornerShape(size = 4.dp)
                )

            )

            Spacer(modifier = Modifier.height(16.dp))

            // Content TextField
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                placeholder = {
                    Text(
                        text = content,
                        color = colorResource(id = R.color.gray_300)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp) // 控制輸入框的高度
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.gray_300),
                        shape = RoundedCornerShape(size = 4.dp)
                    )
                    .background(
                        color = colorResource(id = R.color.gray_200),
                        shape = RoundedCornerShape(size = 4.dp)
                    ),
                maxLines = 5,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Add Image and Submit Button Row
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /* 點擊加入圖片的邏輯 */ },
                    shape = RoundedCornerShape(size = 9.22006.dp),
                    modifier = Modifier
                        .width(150.dp)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.gray_100))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_photo_24),
                        contentDescription = "Add Image",
                        modifier = Modifier,
                        colorResource(id = R.color.black_200)

                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "加入圖片",
                        fontSize = 14.75.sp,
                        fontWeight = FontWeight(600),
                        color = colorResource(id = R.color.black_300)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { navController.navigate(CmtScreenEnum.MyPostsScreen.name) },
                    modifier = Modifier
                        .width(161.dp)
                        .height(52.dp)
                        .background(
                            color = colorResource(id = R.color.primarycolor),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primarycolor))
                ) {
                    Text(text = "儲存")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}


@Composable
fun CustomDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnClickOutside = true) // 控制點擊外部時是否消失
    ) {
        // Dialog 內容
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                // Title
                Text(
                    text = stringResource(id = R.string.make_sure_delete_post),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp),
                    fontWeight = FontWeight(700),
                    color = colorResource(R.color.black_200)
                )

                // Confirm Button
                Button(
                    onClick = { onConfirm() },
                    modifier = Modifier
                        .width(280.dp)
                        .height(52.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(colorResource(id = R.color.red_100)),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.red_100))
                ) {
                    Text(
                        stringResource(id = R.string.confirm),
                        fontWeight = FontWeight(600)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Dismiss Button
                Button(
                    onClick = { onDismiss() },
                    modifier = Modifier
                        .width(280.dp)
                        .height(52.dp)
                        .background(colorResource(id = R.color.white))
                        .border(
                            BorderStroke(1.dp, color = colorResource(R.color.gray_400)),
                            shape = RoundedCornerShape(size = 8.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.white)),
                    shape = RoundedCornerShape(size = 8.dp)
                ) {
                    Text(
                        stringResource(id = R.string.cancel),
                        fontWeight = FontWeight(600),
                        color = colorResource(R.color.gray_400)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EditPostScreenPreview() {
    HealthHelperTheme {
        EditPostScreen(rememberNavController())
    }
}
