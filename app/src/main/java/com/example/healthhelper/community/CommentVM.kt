package com.example.healthhelper.community

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.person.model.ErrorMsg
import com.example.healthhelper.signuplogin.UserManager
import com.example.healthhelper.web.httpPost
import com.example.healthhelper.web.serverUrl
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CommentVM : ViewModel() {
    val userId = UserManager.getUser().userId
    private val _commentState = MutableStateFlow<List<Comment>>(emptyList())
    val commentState: StateFlow<List<Comment>> = _commentState.asStateFlow()

    init {
        viewModelScope.launch {
            _commentState.value = fetchComment()
        }
    }

    fun filterComment(postId: Int): List<Comment>{
        return commentState.value.filter { it.postId == postId }
    }
    fun refreshComment(){
        viewModelScope.launch {
            _commentState.value = fetchComment()
        }
    }

    suspend fun fetchComment(): List<Comment> {
        val url = "$serverUrl/selectComment"
        val gson = Gson()

        return try {
            val result = httpPost(url, "")
            val rootJsonObject = gson.fromJson(result, JsonObject::class.java)
            val dataJsonArray = rootJsonObject.getAsJsonArray("data")
            val collectionType = object : TypeToken<List<Comment>>() {}.type
            gson.fromJson(dataJsonArray, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching Comment from $url: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun insertComment(reply: String, postId: Int, userId: Int): Boolean {
        val url = "$serverUrl/insertComment"
        val gson = Gson()
        val jsonObject = JsonObject().apply {
            addProperty("userId", userId)
            addProperty("postId", postId)
            addProperty("reply", reply)
        }

        val result = httpPost(url, jsonObject.toString())
        val response = gson.fromJson(result, JsonObject::class.java)
        Log.d("dataout", response.get("errMsg").toString())
        if (response.get("result").asBoolean) {
            refreshComment()
        }
        ErrorMsg.errMsg = response.get("errMsg").toString()
        return response.get("result").asBoolean
    }
}