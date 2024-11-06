package com.example.healthhelper.signuplogin

import android.util.Log


object UserManager {
    private var _currentUser: User? = null

    fun setUser(user: User) {
        _currentUser = user
        // 加入測試用的 Log
        Log.d("TEST_DATA_DEMO", """
            UserManager - setUser:
            Account: ${user.account}
            Username: ${user.username}
            Email: ${user.userEmail}
            Phone: ${user.phoneno}
            Gender: ${user.gender}
            Birthday: ${user.birthday}
            RoleID: ${user.roleID}
        """.trimIndent())
    }

    fun getUser(): User? {
        return _currentUser
    }

    fun clearUser() {
        _currentUser = null
    }
}