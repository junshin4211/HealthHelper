package com.example.healthhelper.community.components

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.community.CmtScreenEnum
import com.example.healthhelper.community.Post
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun PostsPreviewComponent(navController: NavHostController, post: Post) {
    Column(modifier = Modifier
        .padding(vertical = 8.dp)
        .shadow(
            elevation = 4.dp,
            spotColor = colorResource(id = R.color.black),
            ambientColor = colorResource(id = R.color.black),
            shape = RoundedCornerShape(15.dp)
        )
        .background(
            color = colorResource(id = R.color.backgroundcolor),
            shape = RoundedCornerShape(15.dp)
        )
        .clickable { navController.navigate("${CmtScreenEnum.PersonalPostScreen.name}/${post.postId}") }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            //usericon資料未引入
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = "Profile Picture",
                            modifier = Modifier.width(25.dp).height(25.dp),
                            tint = colorResource(R.color.primarycolor)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text =post.userName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.black_200)
                        )
                    }
                    Column {
                        Text(
                            text = post.title,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.black_200)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = post.content,
                            fontSize = 10.sp,
                            color = colorResource(R.color.dark_blue_100),
                            fontWeight = FontWeight.Bold,
                            lineHeight = 10.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    val imagePainter = post.picture?.let {
                        runCatching {
                            val decodedImage = Base64.decode(it, Base64.DEFAULT)
                            val bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.size)
                            BitmapPainter(bitmap.asImageBitmap())
                        }.getOrElse {
                            painterResource(id = R.drawable.postpic)
                        }
                    } ?: painterResource(id = R.drawable.postpic)

                    Image(
                        painter = imagePainter,
                        contentDescription = "貼文圖片",
                        modifier = Modifier
                            .width(189.dp)
                            .height(107.dp)
                            .background(colorResource(id = R.color.backgroundcolor))
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
//                Row {
//                    Icon(
//                        painter = painterResource(id = R.drawable.like_not_fill),
//                        contentDescription = "Like",
//                        tint = colorResource(R.color.primarycolor)
//                    )
//                    Text(
//                        text = " ${post.likesAmount}",
//                        fontWeight = FontWeight(600),
//                        color = colorResource(R.color.primarycolor),
//                        modifier = Modifier.padding(start = 4.dp)
//                    )
//                }
                Spacer(modifier = Modifier.width(32.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.comment),
//                        contentDescription = "Comment",
//                        tint = colorResource(R.color.primarycolor)
//                    )
//                    Text(
//                        text = " ${comment.commentAmount}",
//                        fontWeight = FontWeight(600),
//                        color = colorResource(R.color.primarycolor),
//                        modifier = Modifier.padding(start = 4.dp)
//                    )
                    Spacer(modifier = Modifier.weight(1f))
                    // 解析和格式化日期
                    val dateString = runCatching {
                        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                        val date = LocalDateTime.parse(post.postDate, inputFormatter)
                        date.format(outputFormatter)
                    }.getOrElse { post.postDate } // 若解析失敗，使用原始字串

                    Text(
                        text = dateString,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        color = colorResource(R.color.primarycolor)
                    )
                }
            }
        }
    }
}
