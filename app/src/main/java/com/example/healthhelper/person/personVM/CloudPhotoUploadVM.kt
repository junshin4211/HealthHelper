package com.example.healthhelper.person.personVM
import androidx.lifecycle.ViewModel
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils

class CloudPhotoUploadVM:ViewModel() {
    val cloudinary: Cloudinary = Cloudinary(
        ObjectUtils.asMap(
            "cloud_name", "dneyggfga",
            "api_key", "198147441164313",
            "api_secret", "T50x9LZdmrPH7WGHuct_xztj7dQ"
        )
    )
}