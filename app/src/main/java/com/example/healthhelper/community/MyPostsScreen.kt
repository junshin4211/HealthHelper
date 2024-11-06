package com.example.healthhelper.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.healthhelper.community.components.CmtNavbarComponent
import com.example.healthhelper.ui.theme.HealthHelperTheme

@Composable
fun MyPostsScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.backgroundcolor))
    ) {
        CmtNavbarComponent(navController = navController)
        IconButton(
            onClick = {
                navController.navigate(CmtScreenEnum.EditPostScreen.name)
            },
            modifier = Modifier,

            ) {
            Icon(
                painter = painterResource(id = R.drawable.my_posts_edit),
                contentDescription = "Create Post",
                modifier = Modifier,
                colorResource(id = R.color.primarycolor)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.backgroundcolor))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { navController.navigate(CmtScreenEnum.PersonalPostScreen.name) }
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "高蛋白午餐分享", fontWeight = FontWeight.Bold)
                            Text(
                                text = "Saya Akan Membagikan Pengalaman \n" +
                                        "Saya Saat ...", fontWeight = FontWeight.Bold
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.postpic),
                            contentDescription = "貼文圖片",
                            modifier = Modifier
                                .width(189.dp)
                                .height(107.dp)
                                .background(color = colorResource(id = R.color.backgroundcolor))
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        Row {
                            Icon(Icons.Default.ThumbUp, contentDescription = "Like")
                            Text(text = " 100", modifier = Modifier.padding(start = 4.dp))
                        }
                        Row {
                            Icon(Icons.Default.Check, contentDescription = "Comment")
                            Text(text = " 50", modifier = Modifier.padding(start = 4.dp))
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "2小時前",
                                fontSize = 16.sp,
                                color = colorResource(id = R.color.primarycolor)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyPostScreenPreview() {
    HealthHelperTheme {
        MyPostsScreen(rememberNavController())
    }
}