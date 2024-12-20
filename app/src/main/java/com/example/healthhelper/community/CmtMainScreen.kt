package com.example.healthhelper.community

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.community.components.CmtNavbarComponent
import com.example.healthhelper.community.components.PostsPreviewComponent
import com.example.healthhelper.signuplogin.SignUpProperty
import com.example.healthhelper.ui.theme.HealthHelperTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CmtMainScreen(navController: NavHostController, postVM: PostVM = viewModel()) {
    var inputText by remember { mutableStateOf("") }
//    val filteredPosts = fetchPosts().filter { it.title.contains(inputText, true) }
    // 使用 ViewModel 的 postsState 來取得貼文資料
    val posts by postVM.postsState.collectAsState()


    // 根據 inputText 篩選貼文
    val filteredPosts = posts.filter { it.title.contains(inputText, true) }

//    var selectedFilter by remember { mutableStateOf("熱門") } // 新增篩選狀態
//    val posts = remember(selectedFilter, filteredPosts) {
//        when (selectedFilter) {
//            "熱門" -> filteredPosts.sortedByDescending { it.likesAmount } // 按照按讚數排序
//            "最新" -> filteredPosts.sortedByDescending { it.postTime } // 按照貼文時間排序
//            else -> filteredPosts.sortedByDescending { it.postTime }
//        }
//    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.backgroundcolor))
    ) {
        Column {
            Scaffold(
                modifier = Modifier.background(colorResource(id = R.color.backgroundcolor)),
                topBar = { CmtNavbarComponent(navController = navController, postVM = postVM) }) { innerPadding ->
                Column(
                    modifier = Modifier
                        .background(colorResource(R.color.backgroundcolor))
                        .padding(innerPadding)
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .border(
                                4.dp,
                                colorResource(R.color.primarycolor),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .background(colorResource(id = R.color.backgroundcolor)),
                        value = inputText,
                        onValueChange = { inputText = it },
                        placeholder = { Text(text = "請輸入關鍵字") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "search",
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
                    //熱門及最新篩選
//                    Row(
//                        modifier = Modifier
//                            .background(colorResource(id = R.color.backgroundcolor))
//                            .fillMaxWidth()
//                            .padding(vertical = 8.dp),
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Button(
//                            onClick = { selectedFilter = "熱門" },
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = if (selectedFilter == "熱門") colorResource(R.color.primarycolor) else colorResource(
//                                    R.color.dark_gray
//                                )
//                            )
//                        ) {
//                            Text("熱門")
//                        }
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Button(
//                            onClick = { selectedFilter = "最新" },
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = if (selectedFilter == "最新") colorResource(R.color.primarycolor) else colorResource(
//                                    R.color.dark_gray
//                                )
//                            )
//                        ) {
//                            Text("最新")
//                        }
//                    }
                    PostLists(
                        posts = filteredPosts,
                        navController = navController
                    )

                }

            }
        }

        // 浮動按鈕
        IconButton(
            onClick = {
                navController.navigate(CmtScreenEnum.CreatePostScreen.name)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .offset(y = -16.dp)
                .background(
                    color = colorResource(id = R.color.white), shape = CircleShape
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.create_post),
                contentDescription = "Create Post",
                tint = colorResource(id = R.color.primarycolor)
            )
        }
    }
}


@Composable
fun PostLists(
    modifier: Modifier = Modifier,
    posts: List<Post>,
    navController: NavHostController,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(colorResource(id = R.color.backgroundcolor))


    ) {
        posts.forEach { post ->
            PostsPreviewComponent(navController = navController, post = post)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CmtMainScreenPreview() {
    HealthHelperTheme {
        CmtMainScreen(navController = rememberNavController())
    }
}
