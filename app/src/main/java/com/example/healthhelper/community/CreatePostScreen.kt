package com.example.healthhelper.community

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthhelper.R
import com.example.healthhelper.ui.theme.HealthHelperTheme


@Composable
fun CreatePostScreen() {
    var uid by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Column(
       modifier = Modifier
           .fillMaxSize()
           .padding(top = 40.dp)
   ) {
       Row(
           horizontalArrangement = Arrangement.SpaceBetween,
           verticalAlignment = Alignment.CenterVertically,
           modifier = Modifier
               .fillMaxWidth()
               .padding(12.dp)
               .align(Alignment.CenterHorizontally)
       ) {
           Icon(
               painter = painterResource(id = R.drawable.arrow_back_ios_new),
               contentDescription = "arrowBack",
               tint = colorResource(id = R.color.primarycolor)
           )
           Text(
               text = "飲食社群",
               fontSize = 24.sp,
               color = colorResource(id = R.color.primarycolor),
               textAlign = TextAlign.Center,
               fontWeight = FontWeight(600),
           )
           Icon(
               painter = painterResource(id = R.drawable.tabler_receipt),
               contentDescription = "arrowBack",
               tint = colorResource(id = R.color.primarycolor)
           )
       }
       HorizontalDivider(
           thickness = 2.dp,
           modifier = Modifier
               .fillMaxWidth(),
           color = colorResource(id = R.color.primarycolor)
       )
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
                    painter = painterResource(id = R.drawable.community), // 替換為你使用者頭像的資源ID
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "John Doe", // 顯示使用者名稱
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Clear,  // 關閉按鈕
                    contentDescription = "Close",
                    modifier = Modifier.clickable {
                        // 點擊關閉處理邏輯
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title TextField
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Content TextField
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Write your post or question here") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp), // 控制輸入框的高度
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Add Image and Submit Button Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { /* 點擊加入圖片的邏輯 */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow), // 替換為加入圖片圖標的資源ID
                        contentDescription = "Add Image"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "加入圖片")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { /* 點擊送出的邏輯 */ },
                    modifier = Modifier.weight(1f)
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
        CreatePostScreen()
    }
}

