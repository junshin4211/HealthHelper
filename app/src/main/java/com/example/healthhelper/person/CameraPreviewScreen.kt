package com.example.healthhelper.person

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.screen.TabViewModel

@Composable
fun CameraPreviewScreen(
    onPictureTaken: (Uri?) -> Unit,
    navController: NavHostController,
    tabViewModel: TabViewModel,
) {
    val tag = "tag_CameraPreviewScreen"
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    tabViewModel.setTabVisibility(false)

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx).also {
                    it.scaleType = PreviewView.ScaleType.FILL_CENTER
                }
                imageCapture = startCamera(context, lifecycleOwner, previewView)
                previewView
            },
            modifier = Modifier.fillMaxSize()
        )

        Row(modifier = Modifier.align(Alignment.BottomCenter)) {
            Button(modifier = Modifier
                .height(70.dp)
                .weight(1f)
                .padding(8.dp),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.primarycolor)),
                onClick = {
                    val contentValues = ContentValues().apply {
                        put(
                            MediaStore.MediaColumns.DISPLAY_NAME,
                            "${System.currentTimeMillis()}.jpg"
                        )
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
                    }

                    val outputOptions = ImageCapture.OutputFileOptions.Builder(
                        context.contentResolver,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                    ).build()

                    imageCapture?.takePicture(
                        outputOptions,
                        ContextCompat.getMainExecutor(context),
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                val savedUri = outputFileResults.savedUri
                                if (savedUri != null) {
                                    imageUri = outputFileResults.savedUri
                                    Log.d(tag, "imageUri: $imageUri")
                                    onPictureTaken(imageUri)
                                }
                            }

                            override fun onError(exception: ImageCaptureException) {
                                val msg = "Picture capture failed: ${exception.message}"
                                Log.e(tag, msg, exception)
                            }
                        }
                    )
                }
            ) {
                Text(
                    stringResource(id = R.string.takePicture),
                    color = colorResource(R.color.backgroundcolor),
                    fontSize = 28.sp
                )
            }
            Button(
                modifier = Modifier
                    .height(70.dp)
                    .weight(1f)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.primarycolor)),
                onClick = {
                    imageUri = null
                    navController.popBackStack(PersonScreenEnum.personScreen.name, false)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    color = colorResource(R.color.backgroundcolor),
                    fontSize = 28.sp
                )
            }
        }
    }
}

private fun startCamera(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
): ImageCapture {
    // 單例模式取得物件，用來連結相機生命週期
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    val imageCapture = ImageCapture.Builder().build()
    // 註冊監聽器，當future結束，會使用指定executor執行監聽器內容
    cameraProviderFuture.addListener({
        val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
        // 建立預覽use case。CameraX將相機功能如預覽、拍照都視為是一種use case
        val preview = Preview.Builder()
            .build()
            .also {
                // 取得Preview.SurfaceProvider，並用以顯示預覽資料
                it.surfaceProvider = previewView.surfaceProvider
            }
        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

        try {
            // 先取消所有之前對鏡頭的連結使用
            cameraProvider.unbindAll()
            // 從後鏡頭擷取影像資料，並呈現預覽結果；也可用於拍照
            cameraProvider.bindToLifecycle(
                lifecycleOwner, // 生命週期物件，用來掌控相機使用過程，例如預覽、拍照
                cameraSelector, // 使用cameraSelector選擇的鏡頭擷取影像資料
                preview, // 設定要使用預覽use case
                imageCapture // 設定要使用拍照use case
            )
        } catch (exc: Exception) {
            Log.e("CameraPreview", "Use case binding failed", exc)
        }
    }, ContextCompat.getMainExecutor(context))

    return imageCapture
}

