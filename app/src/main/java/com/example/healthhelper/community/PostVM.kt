package com.example.healthhelper.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.web.httpPost
import com.example.healthhelper.web.serverUrl
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostVM:ViewModel() {
    private val _postSelectedState = MutableStateFlow(Post())
    val postSelectedState: StateFlow<Post> = _postSelectedState.asStateFlow()
fun setSelectedPost(post: Post) {
    _postSelectedState.value = post
}
    private val _postsState = MutableStateFlow(emptyList<Post>())
    val postsState: StateFlow<List<Post>> = _postsState.asStateFlow()
    init{
        viewModelScope.launch {
            _postsState.update { fetchPosts() }
        }
    }
    private suspend fun fetchPosts(): List<Post> {
        val url = "${serverUrl}/post"
        var gson = Gson()
        var result = httpPost(url, "")
        val collectionType = object: TypeToken<List<Post>>() {}.type
        var posts = gson.fromJson<List<Post>>(result, collectionType)
        return posts
    }
}
