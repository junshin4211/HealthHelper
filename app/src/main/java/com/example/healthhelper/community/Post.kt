package com.example.healthhelper.community

import java.sql.Timestamp

data class Post(
    var postId: Int = 0,
    var userId : Int = 0,
    var title: String = "",
    var content: String = "",
    var likepost: Int = 0,
    var picture: String? = null,
    var postDate: String = "",
    var userName: String = "",
    var photoUrl: String = ""
) {
    override fun equals(other: Any?): Boolean {
        return this.postId == (other as? Post)?.postId
    }

    override fun hashCode(): Int {
        return postId.hashCode()
    }
}
