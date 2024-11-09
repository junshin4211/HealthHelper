package com.example.healthhelper.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.web.httpPost
import com.example.healthhelper.web.serverUrl
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Base64

class PostVM : ViewModel() {
    private val _postSelectedState = MutableStateFlow(Post())
    val postSelectedState: StateFlow<Post> = _postSelectedState.asStateFlow()


//    fun setSelectedPost(post: Post) {
//        _postSelectedState.value = post
//    }


    private val _postsState = MutableStateFlow(emptyList<Post>())
    val postsState: StateFlow<List<Post>> = _postsState.asStateFlow()

    init {
        viewModelScope.launch {
            _postsState.update { fetchPosts() }
        }
    }

    private suspend fun fetchPosts(): List<Post> {
        val url = "${serverUrl}/post"
        var gson = Gson()
        var result = httpPost(url, "")
        val collectionType = object : TypeToken<List<Post>>() {}.type
        var posts = gson.fromJson<List<Post>>(result, collectionType)
        return posts
    }

    // 新增貼文的函數
    fun insertPost(userId: Int, title: String, content: String, picture: ByteArray? = null) {
        viewModelScope.launch {
            val success = try {
                val url = "${serverUrl}/insertPost"
                val gson = Gson()

                // 建立 JSON 請求物件
                val jsonObject = JsonObject().apply {
                    addProperty("userId", userId)
                    addProperty("title", title)
                    addProperty("content", content)
                    picture?.let {
                        val base64Picture = Base64.getEncoder().encodeToString(it)
                        addProperty("picture", base64Picture)
                    } ?: addProperty("picture", "")
                }

                // 發送 HTTP POST 請求
                val result = httpPost(url, jsonObject.toString())
                val response = gson.fromJson(result, JsonObject::class.java)

                response.get("result").asBoolean
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }

            if (success) {
                // 若插入成功，重新取得最新貼文列表
                _postsState.update { fetchPosts() }
            }
        }
    }
}

