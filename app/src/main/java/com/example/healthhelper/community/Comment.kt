package com.example.healthhelper.community

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("commentId") val commentId: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("userName") val userName: String,
    @SerializedName("photoUrl") val photoUrl: String? = null,
    @SerializedName("postId") val postId: Int,
    @SerializedName("reply") val reply: String,
    @SerializedName("commdate") val commdate: String,
    @SerializedName("likecomm") val likecomm: Int,
)
