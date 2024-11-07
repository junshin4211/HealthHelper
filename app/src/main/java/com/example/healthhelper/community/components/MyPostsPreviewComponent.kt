package com.example.healthhelper.community.components

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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.community.CmtScreenEnum

@Composable
fun MyPostsPreviewComponent(navController: NavHostController) {
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
        .clickable { navController.navigate(CmtScreenEnum.PersonalPostScreen.name) }) {

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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .width(25.dp)
                                .height(25.dp),
                            colorResource(R.color.primarycolor)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = stringResource(id = R.string.userName),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.black_200)
                        )
                        IconButton(
                            onClick = {
                                navController.navigate(CmtScreenEnum.EditPostScreen.name)
                            },
                            modifier = Modifier,

                            ) {
                            Icon(
                                painter = painterResource(id = R.drawable.my_posts_edit),
                                contentDescription = "Create Post",
                                modifier = Modifier
                                    .padding(1.dp)
                                    .width(16.22415.dp)
                                    .height(16.22415.dp),
                                colorResource(id = R.color.primarycolor)
                            )
                        }
                    }
                    Column(
                    ) {
                        Text(
                            text = "高蛋白午餐分享!熱量低又美味!",
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.black_200)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "烤雞腿：誰說健康餐一定要吃雞胸肉！先將雞腿汆燙，接著調味後(\n" + "橄欖油、迷迭香 ...",
                            fontSize = 10.sp,
                            color = colorResource(id = R.color.dark_blue_100),
                            fontWeight = FontWeight.Bold,
                            lineHeight = 10.sp,
                        )
                    }
                }
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.postpic),
                        contentDescription = "貼文圖片",
                        modifier = Modifier
                            .width(189.dp)
                            .height(107.dp)
                            .background(color = colorResource(id = R.color.backgroundcolor))
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.like_not_fill),
                        contentDescription = "Like",
                        tint = colorResource(R.color.primarycolor)
                    )
                    Text(
                        text = " 2",
                        fontWeight = FontWeight(600),
                        color = colorResource(id = R.color.primarycolor),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.width(32.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.comment),
                        contentDescription = "Comment",
                        tint = colorResource(R.color.primarycolor)
                    )
                    Text(
                        text = " 3",
                        fontWeight = FontWeight(600),
                        color = colorResource(id = R.color.primarycolor),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "2小時前",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        color = colorResource(id = R.color.primarycolor)
                    )
                }
            }
        }
    }
}
