package com.example.healthhelper.dietary.components.picker.photopicker

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

object MySinglePhotoPicker {
    @Composable
    fun pickSinglePhoto(
        pickImageLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
        selectedImageUri:Uri?
    ): Uri? {

        return selectedImageUri
    }
}