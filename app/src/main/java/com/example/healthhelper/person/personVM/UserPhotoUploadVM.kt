package com.example.healthhelper.person.personVM

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.healthhelper.person.model.ErrorMsg
import com.example.healthhelper.person.model.UserPhoto
import com.example.healthhelper.web.httpPost
import com.example.healthhelper.web.serverUrl
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class UserPhotoUploadVM: ViewModel() {
    private val _userPhotoUrlState = MutableStateFlow(UserPhoto())
    val userPhotoUrlState: StateFlow<UserPhoto> = _userPhotoUrlState.asStateFlow()

    val uploadedUrl :MutableStateFlow<String?> = MutableStateFlow(null)

    init {
        viewModelScope.launch {
            _userPhotoUrlState.value = fetchUserPhoto(2)
        }

        viewModelScope.launch {
            uploadedUrl.collect {
                if (it?.isNotEmpty()==true) {
                    insertUserPhotoUrl(2, it)
                }
            }
        }
    }
    fun refreshUserPhoto(){
        viewModelScope.launch {
            _userPhotoUrlState.value = fetchUserPhoto(2)
        }
    }
    suspend fun fetchUserPhoto(userId: Int): UserPhoto {
        val url = "$serverUrl/selectUserPhoto"
        val gson = Gson()
        val jsonObject = JsonObject()
        jsonObject.addProperty("userId", userId) // TODO: 需要修改 userId

        return try {
            val result = httpPost(url, jsonObject.toString())
            Log.e("Refresh","result: $result")
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
            addProperty("userId", 2) // TODO: 需要修改 userId
            addProperty("photoUrl", photoUrl)
        }
        Log.d("dataout02", jsonObject.toString())

        val result = httpPost(url, jsonObject.toString())
        val response = gson.fromJson(result, JsonObject::class.java)
        Log.d("dataout", response.get("errMsg").toString())
        if (response.get("result").asBoolean) {
            refreshUserPhoto()
        }
        ErrorMsg.errMsg = response.get("errMsg").toString()
        return response.get("result").asBoolean
    }

    suspend fun uploadImageToCloudinary(
        cloudinary: Cloudinary,
        fileUri: Uri,
    ) {
        val file = File(fileUri.path ?: return)
        withContext(Dispatchers.IO) {
            try {
                val response = cloudinary.uploader().upload(file, ObjectUtils.emptyMap())
                val url = response["url"] as String
                uploadedUrl.value = url
                Log.d("Upload", "Image uploaded to: $url")
            } catch (e: Exception) {
                Log.e("cloudinary:Failed", " ${e.message}")
                e.printStackTrace()
            }
        }
    }
}