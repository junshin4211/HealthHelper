package com.example.healthhelper.community

import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.healthhelper.R
import com.example.healthhelper.community.components.CmtNavbarComponent
import com.example.healthhelper.ui.theme.HealthHelperTheme

@Composable
fun CreatePostScreen(
    navController: NavHostController,
    postVM: PostVM = viewModel(),
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf(false) }
    var contentError by remember { mutableStateOf(false) }
    //加入圖片功能
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    /* 呼叫rememberLauncherForActivityResult並搭配PickVisualMedia()
        以建立可以啟用photo picker的launcher物件。
        照片挑選完畢會執行onResult並傳來該照片的URI供後續處理 */
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            selectedImageUri = uri
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.backgroundcolor))
    ) {
        CmtNavbarComponent(navController = navController, postVM = postVM)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                if (titleError) {
                    Text(
                        text = "標題不可為空且需小於 30 字",
                        color = colorResource(id = R.color.red_100),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "*",
                        color = colorResource(id = R.color.red_100),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            // Title TextField
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    titleError = title.isBlank() || title.length > 30
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.input_post_title),
                        color = colorResource(id = R.color.gray_300)
                    )
                },
                isError = titleError,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.gray_300),
                        shape = RoundedCornerShape(size = 4.dp)
                    )
                    .background(
                        color = colorResource(id = R.color.gray_200),
                        shape = RoundedCornerShape(size = 4.dp)
                    )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (contentError) {
                    Text(
                        text = "內容不可為空",
                        color = colorResource(id = R.color.red_100),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "*",
                        color = colorResource(id = R.color.red_100),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
                Spacer(modifier = Modifier.height(4.dp))
                // Content TextField
                OutlinedTextField(
                    value = content,
                    onValueChange = {
                        content = it
                        contentError = content.isBlank()
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.input_post_content),
                            color = colorResource(id = R.color.gray_300)
                        )
                    },
                    isError = contentError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.gray_300),
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .background(
                            color = colorResource(id = R.color.gray_200),
                            shape = RoundedCornerShape(size = 4.dp)
                        ),
                    maxLines = 5,
                )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //點選跳出相簿處
                Button(
                    onClick = {
                        pickImageLauncher.launch(
                            PickVisualMediaRequest(
                                // 設定只能挑選圖片
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                    shape = RoundedCornerShape(size = 9.dp),
                    modifier = Modifier
                        .width(150.dp)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.gray_100))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_photo_24),
                        contentDescription = "Add Image",
                        modifier = Modifier,
                        colorResource(id = R.color.black_200)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "加入圖片",
                        fontSize = 14.75.sp,
                        fontWeight = FontWeight(600),
                        color = colorResource(id = R.color.black_300)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        titleError = title.isBlank() || title.length > 30
                        contentError = content.isBlank()
                        if (!titleError && !contentError) {
                            val pictureBytes = selectedImageUri?.let { uri ->
                                val inputStream =
                                    navController.context.contentResolver.openInputStream(uri)
                                val bytes = inputStream?.readBytes()
                                inputStream?.close()
                                bytes  // 直接返回 ByteArray
                            }

                            // 將 pictureBytes 傳入 insertPost
                            postVM.insertPost(title, content, pictureBytes)
                            navController.navigate(CmtScreenEnum.MyPostsScreen.name)
                        }
                    },
                    modifier = Modifier
                        .width(161.dp)
                        .height(52.dp)
                        .background(
                            color = colorResource(id = R.color.primarycolor),
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primarycolor))
                ) {
                    Text(text = "送出")
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
            // 當照片被選取則URI不為null，就將該照片顯示
            selectedImageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(350.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreatePostScreenPreview() {
    HealthHelperTheme {
        CreatePostScreen(rememberNavController())
    }
}

