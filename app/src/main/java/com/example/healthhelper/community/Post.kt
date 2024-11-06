package com.example.healthhelper.community

data class Post(
    var postId: String = "",
    var userId : String = "",
    var title: String = "",
    var content: String = "",
    var likesAmount: Int = 0,
    var img: Int = 0,
    var postTime: String = ""
) {
    override fun equals(other: Any?): Boolean {
        return this.postId == (other as? Post)?.postId
    }

    override fun hashCode(): Int {
        return postId.hashCode()
    }
}
