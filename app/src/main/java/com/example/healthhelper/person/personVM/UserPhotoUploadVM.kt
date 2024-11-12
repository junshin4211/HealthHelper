package com.example.healthhelper.person.personVM

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.healthhelper.person.model.UserPhoto
import com.example.healthhelper.signuplogin.User
import com.example.healthhelper.signuplogin.UserManager
import com.example.healthhelper.web.httpPost
import com.example.healthhelper.web.serverUrl
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.protobuf.Internal.BooleanList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File

class UserPhotoUploadVM : ViewModel() {
//    val user :StateFlow<User>  = UserManager.currentUser
    private val _userPhotoUrlState = MutableStateFlow(UserPhoto())
    val userPhotoUrlState: StateFlow<UserPhoto> = _userPhotoUrlState.asStateFlow()
    private val _isloading = MutableStateFlow(false)
    val isloading : StateFlow<Boolean> = _isloading.asStateFlow()

    private val uploadedUrl: MutableStateFlow<String?> = MutableStateFlow(null)

    val userId = UserManager.getUser().userId

    init {
        viewModelScope.launch {
            _userPhotoUrlState.value = fetchUserPhoto(userId)
        }

        viewModelScope.launch {
            uploadedUrl.collect {
                if (it?.isNotEmpty() == true) {
                    insertUserPhotoUrl(userId, it)
                }
            }
        }
    }

    private fun refreshUserPhoto() {
        viewModelScope.launch {
            _userPhotoUrlState.value = fetchUserPhoto(userId)
            _isloading.value =false
        }
    }

    suspend fun fetchUserPhoto(userId: Int): UserPhoto {
        val url = "$serverUrl/selectUserPhoto"
        val gson = Gson()
        val jsonObject = JsonObject()
        jsonObject.addProperty("userId", userId)

        return try {
            val result = httpPost(url, jsonObject.toString())
            Log.e("Refresh", "result: $result")
            gson.fromJson(result, UserPhoto::class.java) ?: UserPhoto()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching user photo from $url: ${e.message}", e)
            UserPhoto()
        }
    }

    suspend fun insertUserPhotoUrl(userId: Int, photoUrl: String): Boolean {
        val url = "$serverUrl/insertUserPhoto"
        val gson = Gson()
        val jsonObject = JsonObject().apply {
            addProperty("userId", userId)
            addProperty("photoUrl", photoUrl)
        }
        Log.d("dataout02", jsonObject.toString())

        val result = httpPost(url, jsonObject.toString())
        val response = gson.fromJson(result, JsonObject::class.java)
        Log.d("dataout", response.get("errMsg").toString())

        if (response.get("result").asBoolean) {
            refreshUserPhoto()
        }

        return response.get("result").asBoolean
    }

    suspend fun uploadImageToCloudinary(
        cloudinary: Cloudinary,
        fileUri: Uri,
    ): Boolean {
        val file = File(fileUri.path ?: return false)
        return withContext(Dispatchers.IO) {
            try {
                _isloading.value = true
                val response = cloudinary.uploader().upload(file, ObjectUtils.emptyMap())
                val url = response["url"] as String
                uploadedUrl.value = url
                url.isNotEmpty()
            } catch (e: Exception) {
                Log.e("cloudinary:Failed", " ${e.message}")
                false
            }
        }
    }

    suspend fun uploadImageUriToCloudinary(
        cloudinary: Cloudinary,
        contentUri: Uri,
        contentResolver: ContentResolver,
    ) {
        withContext(Dispatchers.IO) {
            try {
                _isloading.value = true
                val inputStream = contentResolver.openInputStream(contentUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()
                val response = cloudinary.uploader().upload(byteArray, ObjectUtils.emptyMap())
                val url = response["url"] as String
                uploadedUrl.value = url
                Log.d("Upload", "Image uploaded to: $url")
            } catch (e: Exception) {
                Log.e("cloudinary:Failed", " ${e.message}")
            }
        }
    }

}