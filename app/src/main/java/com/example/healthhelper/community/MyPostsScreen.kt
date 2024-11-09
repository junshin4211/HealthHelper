package com.example.healthhelper.community


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.community.components.CmtNavbarComponent
import com.example.healthhelper.community.components.MyPostsPreviewComponent
import com.example.healthhelper.ui.theme.HealthHelperTheme

@Composable
fun MyPostsScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.backgroundcolor))
    ) {
        CmtNavbarComponent(navController = navController)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "我的貼文",
                fontSize = 36.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight(600),
                color = colorResource(id = R.color.primarycolor),
                modifier = Modifier.padding(bottom = 30.dp)
            )
//            MyPostLists(
//                posts = emptyList(),
//                navController = navController
//            )
        }
    }
}

@Composable
fun MyPostLists(
    modifier: Modifier = Modifier,
//    posts: List<Post>,
    navController: NavHostController,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(colorResource(id = R.color.backgroundcolor))


    ) {
//        posts.forEach { post ->
//            MyPostsPreviewComponent(navController = navController, post = post)
//        }
    }
}


@Preview(showBackground = true)
@Composable
fun MyPostScreenPreview() {
    HealthHelperTheme {
        MyPostsScreen(rememberNavController())
    }
}