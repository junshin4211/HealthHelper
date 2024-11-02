package com.example.healthhelper.community


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.AbsoluteAlignment
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
import androidx.core.app.Person
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.community.components.CmtNavbarComponent
import com.example.healthhelper.ui.theme.HealthHelperTheme

@Composable
fun PersonalPostScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
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
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.post_title_text),
                    fontSize = 24.sp,
                    fontWeight = FontWeight(800),
                    color = colorResource(R.color.black_200)
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Image(
                painter = painterResource(id = R.drawable.post_img_example),
                contentDescription = "image description",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(365.90417.dp)
                    .height(192.89487.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.post_content_text),
                color = colorResource(id = R.color.dark_blue_100)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
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
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "共2則留言",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = colorResource(id = R.color.gray_500)
                )
            }
            HorizontalDivider(
                modifier = Modifier
                    .padding(0.dp)
                    .width(387.dp)
                    .height(2.dp)
                    .background(color = colorResource(id = R.color.white_100))
            )
        }
    }
}


@Composable
fun CommentLists(
    comments: List<Comment>,
    innerPadding: PaddingValues,
    onItemClick: (Comment) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(comments) { comment ->
            // 用來建立Lists內容物
            ListItem(
                modifier = Modifier.clickable {
                    onItemClick(comment)
                },
                overlineContent = { Text(text = comment.userName) },
                headlineContent = { Text(comment.reply) },
                supportingContent = { Text(comment.commentTime) },
                leadingContent = {
                    Image(
                        painter = painterResource(id = comment.userIcon),
                        contentDescription = "book",
                        modifier = Modifier.padding(16.dp)
                    )
                },
            )
            HorizontalDivider()
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
            0,
            "2024-06-07 04:30"
        ),
        Comment(
            R.drawable.profile,
            "Max",
            "看起來營養又好吃",
            1,
            "2024-06-07 13:34"
        ),
        Comment(
            R.drawable.profile,
            "Betty",
            "讚!繼續保持",
            2,
            "2024-06-07 23:30"
        ),
        Comment(
            R.drawable.profile,
            "Martin",
            "會瘦會瘦",
            3,
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
