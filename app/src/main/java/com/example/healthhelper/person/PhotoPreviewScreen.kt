package com.example.healthhelper.person

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.healthhelper.R
import com.example.healthhelper.person.personVM.CloudPhotoUploadVM
import com.example.healthhelper.person.personVM.UserPhotoUploadVM
import com.example.healthhelper.screen.TabViewModel
import kotlinx.coroutines.launch

@Composable
fun PhotoPreviewScreen(
    navController:NavHostController,
    imageUri: Uri?,
    onRejectClick: () -> Unit,
    userPhotoUploadVM: UserPhotoUploadVM,
    cloudPhotoUploadVM: CloudPhotoUploadVM,
    ) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val cloudinary = cloudPhotoUploadVM.cloudinary
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageUri),
            contentDescription = null,
            modifier = Modifier
                .size(350.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(
                modifier = Modifier.width(150.dp).height(70.dp),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.primarycolor)),
                onClick = {
                    Log.e("imageUri", "$imageUri")
                    if(imageUri != null){
                        scope.launch {
                            userPhotoUploadVM.uploadImageUriToCloudinary(cloudinary, imageUri, context.contentResolver)
                            navController.popBackStack(
                                PersonScreenEnum.personScreen.name,
                                false
                            )
                        }
//                        tabViewModel.setTabVisibility(true)
                    }
                }
            ) {
                Text(stringResource(id = R.string.save), color = colorResource(R.color.backgroundcolor), fontSize = 28.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                modifier = Modifier.width(150.dp).height(70.dp),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.primarycolor)),
                onClick = {
                imageUri?.let { context.contentResolver.delete(it, null, null) }
                onRejectClick()
            }) {
                Text(stringResource(id = R.string.delete), color = colorResource(R.color.backgroundcolor), fontSize = 28.sp)
            }
        }
    }
}

