package com.example.healthhelper.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
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
    }
}

@Preview(showBackground = true)
@Composable
fun MyPostScreenPreview() {
    HealthHelperTheme {
        MyPostsScreen(rememberNavController())
    }
}