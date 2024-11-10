package com.example.healthhelper.person.screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.graphics.Path
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.healthhelper.R
import com.example.healthhelper.person.personVM.CloudPhotoUploadVM
import com.example.healthhelper.person.personVM.UserPhotoUploadVM
import com.example.healthhelper.person.widget.CustomTopBar
import com.example.healthhelper.person.widget.SaveButton
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickPhotoScreen(
    navController: NavHostController,
    cloudPhotoUploadVM: CloudPhotoUploadVM,
    userPhotoUploadVM: UserPhotoUploadVM,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            selectedImageUri = uri
            scale = 1f
            offset = Offset.Zero
        }
    )
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        pickImageLauncher.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopBar(
                title = "",
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                scrollBehavior = scrollBehavior,
            )
        },
        containerColor = colorResource(R.color.backgroundcolor)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                stringResource(R.string.drag_and_zoom),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            selectedImageUri?.let { uri ->
                var imageBounds by remember { mutableStateOf(Rect.Zero) }

                val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
                    scale = (scale * zoomChange).coerceIn(1f, 3f)
                    val newOffset = offset + panChange
                    val maxX = (imageBounds.width * (scale - 1)) / 2
                    val maxY = (imageBounds.height * (scale - 1)) / 2

                    offset = Offset(
                        newOffset.x.coerceIn(-maxX, maxX),
                        newOffset.y.coerceIn(-maxY, maxY)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(350.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .transformable(transformableState)
                        .onGloballyPositioned { coordinates ->
                            imageBounds = coordinates.boundsInParent()
                        }
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = null,
                        modifier = Modifier
                            .matchParentSize()
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale,
                                translationX = offset.x,
                                translationY = offset.y
                            ),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            val context = LocalContext.current
            val cloudinary = cloudPhotoUploadVM.cloudinary
            SaveButton(
                onClick = {
                    val croppedUri = selectedImageUri?.let { uri ->
                        generateCroppedBitmap(context, uri, scale, offset, 350)
                    }
                    croppedUri?.let { uri ->
                        scope.launch {
                            userPhotoUploadVM.uploadImageToCloudinary(cloudinary, uri)
                            navController.navigateUp()
                        }
                    }
                    navController.navigateUp()
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
fun generateCroppedBitmap(
    context: Context,
    uri: Uri,
    scale: Float,
    offset: Offset,
    size: Int,
): Uri? {
    val sourceBitmap = try {
        ImageDecoder.decodeBitmap(
            ImageDecoder.createSource(context.contentResolver, uri)
        ) { decoder, _, _ ->
            decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
            decoder.isMutableRequired = true
        }
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }

    val outputBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(outputBitmap)
    val centerX = size / 2f
    val centerY = size / 2f
    val scaleX = size.toFloat() / sourceBitmap.width
    val scaleY = size.toFloat() / sourceBitmap.height
    val baseScale = maxOf(scaleX, scaleY)
    val path = Path().apply {
        addCircle(centerX, centerY, size / 2f, Path.Direction.CCW)
    }
    canvas.clipPath(path)

    val matrix = Matrix().apply {
        postScale(baseScale, baseScale)
        postTranslate(
            (size - sourceBitmap.width * baseScale) / 2f,
            (size - sourceBitmap.height * baseScale) / 2f
        )
        postScale(scale, scale, centerX, centerY)
        postTranslate(offset.x, offset.y)
    }

    canvas.drawBitmap(sourceBitmap, matrix, null)
    sourceBitmap.recycle()

    return saveBitmapToFile(context, outputBitmap)
}

private fun saveBitmapToFile(context: Context, bitmap: Bitmap): Uri? {
    val filename = "cropped_image_${System.currentTimeMillis()}.png"
    val file = File(context.cacheDir, filename)

    return FileOutputStream(file).use { outputStream ->
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            Uri.fromFile(file)

        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PhotoPreview() {
//    PickPhotoScreen(rememberNavController())
//}



