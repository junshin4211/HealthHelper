package com.example.healthhelper.signuplogin

import android.util.Log
import com.example.healthhelper.person.model.UserPhoto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object UserManager {
    private var _currentUser= MutableStateFlow(User())
    val currentUser: StateFlow<User> = _currentUser.asStateFlow()

    fun setUser(user: User) {
        _currentUser.value= user
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
            photoUrl:${user.photoUrl}
        """.trimIndent())
    }

    fun getUser(): User {
        return _currentUser.value
    }

    fun clearUser() {
        _currentUser.value.clear()
        Log.d("userclear", "${getUser()}")
    }
}