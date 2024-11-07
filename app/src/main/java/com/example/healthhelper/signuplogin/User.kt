package com.example.healthhelper.signuplogin




data class User(

    var userId: Int = 0,
    var account: String = "",
    var username: String = "",
    var roleID: Int? = null,  // 改為可為 null 的 Int 型別
    var userEmail: String = "",
    var phoneno: String? = null,
    var gender: Int? = null,
    var birthday: String? = null,
    var photoUrl: String?=null



) {
    override fun equals(other: Any?): Boolean {
        return this.userId == (other as? User)?.userId
    }

    override fun hashCode(): Int {
        return userId.hashCode()
    }
}
