package com.example.healthhelper.person.personVM
import androidx.lifecycle.ViewModel
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils

class CloudPhotoUploadVM:ViewModel() {
    val cloudinary: Cloudinary = Cloudinary(
        ObjectUtils.asMap(
            "cloud_name", "cloud_name",
            "cloud_name", "cloud_name",
            "api_secret", "api_secret"
        )
    )
}