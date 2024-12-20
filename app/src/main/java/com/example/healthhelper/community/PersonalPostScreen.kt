package com.example.healthhelper.community


import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.healthhelper.R
import com.example.healthhelper.community.components.CmtNavbarComponent
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@JvmOverloads
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PersonalPostScreen(
    navController: NavHostController,
    commentVM: CommentVM,
    postVM: PostVM,
    postId: String?,
) {
    var reply by remember { mutableStateOf("") }
    var isRefreshing by remember { mutableStateOf(false) }
    var comments by remember { mutableStateOf<List<Comment>>(emptyList()) }
    val scope = rememberCoroutineScope()
    val postIdInt = postId?.toIntOrNull() // 確保是 Int，避免 null
    val post = remember { postIdInt?.let { postVM.getPostById(it) } }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            scope.launch {
                postId?.toInt()?.let {
                    comments = commentVM.filterComment(it)
                }
                isRefreshing = false
            }
        }
    )


    LaunchedEffect(postId) {
        postId?.toInt()?.let {
            comments = commentVM.filterComment(it)
        }
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.backgroundcolor))
                .padding(bottom = 72.dp)
        ) {
            // Navbar 元件
            item {
                CmtNavbarComponent(navController = navController, postVM = postVM)
            }

            // Profile row
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 16.dp)
                ) {
                    post?.photoUrl?.let {
                        Image(
                            painter = rememberAsyncImagePainter(it),
                            contentDescription = "User profile picture",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .padding(0.dp)
                        )
                    } ?: Image(
                        painter = painterResource(R.drawable.profile),
                        contentDescription = "User profile picture",
                        modifier = Modifier
                            .size(48.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = post?.userName ?: stringResource(id = R.string.userName),
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
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Text(
                        text = post?.title ?: stringResource(id = R.string.post_title_text),
                        fontSize = 30.sp,
                        lineHeight = 40.sp,
                        fontWeight = FontWeight(800),
                        color = colorResource(R.color.black_200)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            post?.picture?.let { base64Image ->
                item {
                    // 將 Base64 編碼的圖片字串解碼為 ByteArray
                    val imageBytes = Base64.decode(base64Image, Base64.DEFAULT)
                    val imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Image(
                            bitmap = imageBitmap.asImageBitmap(),
                            contentDescription = "Post Image",
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
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
                        text = post?.content ?: stringResource(id = R.string.post_content_text),
                        fontSize = 18.sp,
                        lineHeight = 30.sp,
                        fontWeight = FontWeight(400),
                        color = colorResource(id = R.color.dark_blue_100)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Post date row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    val dateString = runCatching {
                        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                        val date = LocalDateTime.parse(post?.postDate, inputFormatter)
                        date.format(outputFormatter)
                    }.getOrElse { post?.postDate }
                    Text(
                        text = dateString ?: "2024-11-01 08:30",
                        fontSize = 16.sp,
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Text(
                        text = "共${comments.size}則留言",
                        fontSize = 25.sp,
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
            itemsIndexed(comments) { index, comment ->

                ListItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ListItemDefaults.colors(
                        containerColor = colorResource(R.color.backgroundcolor),

                        ),
                    overlineContent = {
                        Text(
                            text = comment.userName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(600),
                            color = colorResource(id = R.color.black_200)
                        )
                    },
                    headlineContent = {
                        Text(
                            text = comment.reply,
                            fontSize = 18.sp,
                            lineHeight = 22.4.sp,
                            fontWeight = FontWeight(400),
                            color = colorResource(id = R.color.black_200)
                        )
                    },
                    supportingContent = {
                        Text(
                            text = comment.commdate,
                            fontSize = 14.sp,
                            lineHeight = 22.4.sp,
                            color = colorResource(id = R.color.gray_600)
                        )
                    },
                    leadingContent = {
                        comment.photoUrl?.let {
                            Image(
                                painter = rememberAsyncImagePainter(it),
                                contentDescription = "User profile picture",
                                modifier = Modifier
                                    .padding(16.dp)
                                    .width(45.16.dp)
                                    .height(45.16.dp)
                                    .clip(CircleShape)
                            )
                        } ?: Image(
                            painter = painterResource(R.drawable.profile),
                            contentDescription = "User profile picture",
                            modifier = Modifier
                                .padding(16.dp)
                                .width(45.16.dp)
                                .height(45.16.dp)
                        )

                    },
                    trailingContent = {
                        Text(text = "B${index + 1}", fontSize = 16.sp)
                    }
                )

                HorizontalDivider(color = colorResource(id = R.color.white_100))
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            OutlinedTextField(
//                value = reply,
//                onValueChange = { reply = it },
//                placeholder = { Text(text = "留言…", color = colorResource(id = R.color.gray_500)) },
//                modifier = Modifier
//                    .weight(1f)
//                    .background(colorResource(id = R.color.white))
//                    .border(
//                        width = 1.dp,
//                        color = colorResource(id = R.color.gray_300),
//                        shape = RoundedCornerShape(size = 4.dp)
//                    )
//                    .background(
//                        color = colorResource(id = R.color.white),
//                        shape = RoundedCornerShape(size = 4.dp)
//                    ),
//
//                )

            androidx.compose.material.OutlinedTextField(
                value = reply,
                onValueChange = { reply = it },
                placeholder = {
                    Text(
                        text = "留言…",
                        color = colorResource(id = R.color.gray_500)
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .background(colorResource(id = R.color.white)),
                colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = colorResource(id = R.color.gray_300),
                    focusedBorderColor = colorResource(id = R.color.gray_300),
                    backgroundColor = colorResource(id = R.color.white),
                    textColor = colorResource(id = R.color.black_300),
                    cursorColor = colorResource(id = R.color.black_200)
                ),
                shape = RoundedCornerShape(size = 4.dp)
            )




            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = {
                if (postId != null) {
                    scope.launch {
                        val result = commentVM.insertComment(reply, postId.toInt())
                        if (result) {
                            reply = ""
                            postId.toIntOrNull()?.let {
                                comments = commentVM.filterComment(it)
                            }
                        }
                    }
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.send),
                    contentDescription = "Send Comment",
                    tint = colorResource(id = R.color.primarycolor)
                )
            }
        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
