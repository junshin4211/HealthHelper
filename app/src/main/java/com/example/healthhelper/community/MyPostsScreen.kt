package com.example.healthhelper.community


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.community.components.CmtNavbarComponent
import com.example.healthhelper.community.components.MyPostsPreviewComponent

@Composable
fun MyPostsScreen(navController: NavHostController, postVM: PostVM) {
    val posts by postVM.postsState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.backgroundcolor))
    ) {
        CmtNavbarComponent(navController = navController, postVM = postVM)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "我的貼文",
                fontSize = 36.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight(600),
                color = colorResource(id = R.color.primarycolor),
                modifier = Modifier.padding(start = 8.dp, bottom = 30.dp)
            )
            MyPostLists(posts = posts, navController = navController)
        }
    }
}

@Composable
fun MyPostLists(
    posts: List<Post>,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.backgroundcolor)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (posts.isEmpty()) {
            Text(
                text = "尚無貼文",
                fontSize = 20.sp,
                color = colorResource(id = R.color.gray_600)
            )
        } else {
            posts.forEach { post ->
                MyPostsPreviewComponent(navController = navController, post = post)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}