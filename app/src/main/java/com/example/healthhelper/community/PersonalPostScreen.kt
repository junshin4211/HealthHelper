package com.example.healthhelper.community


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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

@Composable
fun PersonalPostScreen(navController: NavHostController) {
    val comments = fetchComments()
    var reply  by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.backgroundcolor))
    ) {
        // Navbar 元件
        item {
            CmtNavbarComponent(navController = navController)
        }

        // Profile row
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(48.dp),
                    tint = colorResource(R.color.primarycolor)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = stringResource(id = R.string.userName),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.black_200)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Post title row
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.post_title_text),
                    fontSize = 24.sp,
                    fontWeight = FontWeight(800),
                    color = colorResource(R.color.black_200)
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
        }

        // Post image
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.post_img_example),
                    contentDescription = "image description",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(365.90417.dp)
                        .height(192.89487.dp)
                        .padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Post content text
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.post_content_text),
                    color = colorResource(id = R.color.dark_blue_100)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        // Post date row
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
            ) {
                Text(
                    text = "2024-08-19 08:30",
                    fontSize = 14.sp,
                    lineHeight = 22.4.sp,
                    fontWeight = FontWeight(400),
                    color = colorResource(id = R.color.gray_600),
                )
            }
            Spacer(modifier = Modifier.height(55.dp))
        }

        // Comment section title
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = "共${comments.size}則留言",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = colorResource(id = R.color.gray_500)
                )
            }
            HorizontalDivider(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .width(387.dp)
                    .height(2.dp)
                    .background(color = colorResource(id = R.color.white_100))
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Comment list items
        items(comments) { comment ->
            Box(
                modifier = Modifier
                    .fillMaxWidth() // 確保留言項目填滿寬度
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(color = colorResource(id = R.color.primarycolor))
            ) {
                ListItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    overlineContent = {
                        Text(
                            text = comment.userName,
                            fontSize = 14.sp,
                            fontWeight = FontWeight(600),
                            color = colorResource(id = R.color.black_200)
                        )
                    },
                    headlineContent = {
                        Text(
                            text = comment.reply,
                            fontSize = 14.sp,
                            lineHeight = 22.4.sp,
                            fontWeight = FontWeight(400),
                            color = colorResource(id = R.color.black_200)
                        )
                    },
                    supportingContent = {
                        Text(
                            text = comment.commentTime,
                            fontSize = 14.sp,
                            lineHeight = 22.4.sp,
                            color = colorResource(id = R.color.gray_600)
                        )
                    },
                    leadingContent = {
                        Image(
                            painter = painterResource(id = comment.userIcon),
                            contentDescription = "User profile picture",
                            modifier = Modifier
                                .padding(16.dp)
                                .width(45.16.dp)
                                .height(45.16.dp)
                        )
                    },
                    trailingContent = { Text("B${comment.index}") }
                )
            }
            HorizontalDivider(color = colorResource(id = R.color.white_100))
        }
        // 留言輸入框
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value =  reply,
                    onValueChange = { reply = it },
                    placeholder = { Text("留言…") },
                    modifier = Modifier
                        .weight(1f)
                        .background(colorResource(id = R.color.white))
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.gray_300),
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .background(
                            color = colorResource(id = R.color.white),
                            shape = RoundedCornerShape(size = 4.dp)
                        ),

                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { /* 發送留言 */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.send),
                        contentDescription = "Send Comment",
                        tint = colorResource(id = R.color.primarycolor)
                    )
                }
            }
        }
    }
}


/**
 * 載入測試需要資料
 * @return 多留言資訊
 */
fun fetchComments(): List<Comment> {
    return listOf(
        Comment(
            R.drawable.profile,
            "Marry",
            "看起來美味又營養",
            1,
            "2024-06-07 04:30"
        ),
        Comment(
            R.drawable.profile,
            "Max",
            "看起來營養又好吃",
            2,
            "2024-06-07 13:34"
        ),
        Comment(
            R.drawable.profile,
            "Betty",
            "讚!繼續保持",
            3,
            "2024-06-07 23:30"
        ),
        Comment(
            R.drawable.profile,
            "Martin",
            "會瘦會瘦",
            4,
            "2024-06-07 23:44"
        )

    )
}


@Preview(showBackground = true)
@Composable
fun PersonalPostScreenPreview() {
    HealthHelperTheme {
        PersonalPostScreen(rememberNavController())
    }
}
