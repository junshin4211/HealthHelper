package com.example.healthhelper.community

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.ui.theme.HealthHelperTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CmtMainScreen(
    navController: NavHostController,
) {
    var inputText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.backgroundcolor))
    ) {
        CmtNavbarComponent(navController = navController)
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(
                    4.dp,
                    colorResource(R.color.primarycolor),
                    shape = RoundedCornerShape(15.dp)
                ),
            value = inputText,
            onValueChange = { inputText = it },
            placeholder = { Text(text = "請輸入關鍵字") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = "search",
                    tint = colorResource(R.color.primarycolor)
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.clearSearch),
                    modifier = Modifier.clickable { inputText = "" },
                    tint = colorResource(R.color.primarycolor)
                )
            },
            shape = RoundedCornerShape(15.dp),
            singleLine = true,
        )
        IconButton(
            onClick = {
                navController.navigate(CmtScreenEnum.CreatePostScreen.name)
            },
            modifier = Modifier,

        ) {
            Icon(
                painter = painterResource(id = R.drawable.create_post),
                contentDescription = "Create Post",
                modifier = Modifier,
                colorResource(id = R.color.primarycolor)
            )
        }
    }
}


/**
 * 列表內容
 * @param books 欲顯示的書籍清單
 * @param innerPadding 元件要套用innerPadding，否則內容無法跟TopAppBar對齊
 * @param onItemClick 點擊列表項目時所需呼叫的函式
 */
@Composable
fun PostLists(
    posts: List<Post>,
    innerPadding: PaddingValues,
    onItemClick: (Post) -> Unit,
) {
    // 也可改用LazyColumn
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            // 需要加上垂直捲動功能
            .verticalScroll(rememberScrollState())
    ) {
        posts.forEach { post ->
            // 用來建立Lists內容物
            ListItem(
                modifier = Modifier.clickable {
                    onItemClick(post)
                },
                headlineContent = { Text(post.name) },
                supportingContent = { Text(post.price.toString()) },
            )
            HorizontalDivider()
        }
    }
}


/**
 * 載入測試需要資料
 * @return 多本書資訊
 */
fun fetchPosts(): List<Post> {
    return listOf(
        Post("0001", "Android App", 600.0),
        Post("0002", "iOS App", 650.0),
        Post("0003", "MySQL DB", 550.0),
        Post("0004", "Python", 500.0),
        Post("0005", "Kotlin", 520.0),
        Post("0006", "MongoDB", 630.0),
        Post("0007", "C/C++", 420.0),
        Post("0008", "C#", 480.0),
        Post("0009", "Swift", 540.0),
        Post("0010", "Java", 520.0),
        Post("0011", "SQL Server", 620.0),
        Post("0012", "Oracle DB", 680.0),
        Post("0013", "HTML", 400.0),
        Post("0014", "Java Script", 470.0)
    )
}


@Preview(showBackground = true)
@Composable
fun CmtMainScreenPreview() {
    HealthHelperTheme {
        CmtMainScreen(navController = rememberNavController())
    }
}
