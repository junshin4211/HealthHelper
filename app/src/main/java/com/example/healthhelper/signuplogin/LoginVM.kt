package com.example.healthhelper.signuplogin

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginVM : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val gson = Gson()

    // 更新帳號輸入
    fun updateAccount(account: String) {
        _uiState.update { currentState ->
            currentState.copy(formState = currentState.formState.copy(account = account))
        }
    }

    // 更新密碼輸入
    fun updatePassword(password: String) {
        _uiState.update { currentState ->
            currentState.copy(formState = currentState.formState.copy(password = password))
        }
    }

    // 切換密碼顯示狀態
    fun togglePasswordVisibility() {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    passwordVisible = !currentState.formState.passwordVisible
                )
            )
        }
    }

    fun submitLogin(
        context: Context,
        onSuccess: (Int) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val url = "$serverUrl/user/login"
                val jsonObject = JsonObject().apply {
                    addProperty("account", _uiState.value.formState.account)
                    addProperty("password", _uiState.value.formState.password)
                }

                val result = httpPost(url, jsonObject.toString())
                println("Debug - Server response: $result")

                val response = gson.fromJson(result, JsonObject::class.java)
                if (response.get("success")?.asBoolean != true) {
                    throw Exception(response.get("message")?.asString ?: "登入失敗")
                }

                val user = gson.fromJson(result, User::class.java)

                // 只更新 StateFlow，不使用 SharedPreferences
                _uiState.update { currentState ->
                    currentState.copy(loggedInUser = user)
                }

                val tag = "UserInfo"

                Log.d(tag, """
                        LoginSuccess：
                        UserId: ${user.userId}
                        Account: ${user.account}
                        Username: ${user.username}
                        Email: ${user.userEmail}
                        RoleID: ${user.roleID}
                    """.trimIndent())

                onSuccess(user.userId)
            } catch (e: Exception) {
                println("Debug - Network error: ${e.message}")
                e.printStackTrace()
                onError("${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 提供獲取當前登入用戶的方法
//    fun getCurrentUser(): User? {
//        return _uiState.value.loggedInUser
//    }
}

data class LoginUiState(
    val formState: LoginProperty = LoginProperty(),
    val loggedInUser: User? = null
)