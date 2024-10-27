package com.example.healthhelper.community

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.healthhelper.ui.theme.HealthHelperTheme


@Composable
fun CreatePostScreen(navController: NavHostController) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.backgroundcolor))
    ) {
        CmtNavbarComponent(navController = navController)
        Column(
            // 內容物水平置中
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // 所有padding皆為16
            // .padding(top = 64.dp)
            // .padding(horizontal = 16.dp, vertical = 32.dp)
            // .padding(start = 16.dp, top = 64.dp, end = 8.dp, bottom = 32.dp)
        ) {

            // Profile row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
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
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Close",
                    modifier = Modifier
                        .clickable {
                            // 點擊關閉處理邏輯
                        }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title TextField
            //Title字數限制尚未完成
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = {
                    Text(
                        text = stringResource(R.string.post_title),
                        color = colorResource(id = R.color.gray_300)
                    )
                },
                modifier = Modifier
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
                        text = stringResource(R.string.post_content),
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
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
                    onClick = { navController.navigate(CmtScreenEnum.MyPostsScreen.name)},
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
                    Text(text = "送出")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreatePostScreenPreview() {
    HealthHelperTheme {
        CreatePostScreen(rememberNavController())
    }
}

