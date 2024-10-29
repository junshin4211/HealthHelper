package com.example.healthhelper.signuplogin

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

    fun updateAccount(account: String) {
        _uiState.update { currentState ->
            currentState.copy(formState = currentState.formState.copy(account = account))
        }
    }

    fun updatePassword(password: String) {
        _uiState.update { currentState ->
            currentState.copy(formState = currentState.formState.copy(password = password))
        }
    }

    fun togglePasswordVisibility() {
        _uiState.update { currentState ->
            currentState.copy(formState = currentState.formState.copy(
                passwordVisible = !currentState.formState.passwordVisible
            ))
        }
    }

    fun submitLogin(onSuccess: (Int) -> Unit, onError: (String) -> Unit) {  // 修改這裡，加入 Int 參數
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val url = "$serverUrl/user/login"
                val jsonObject = JsonObject().apply {
                    addProperty("account", _uiState.value.formState.account)
                    addProperty("password", _uiState.value.formState.password)
                }

                val result = httpPost(url, jsonObject.toString())

                if (result.isNotBlank()) {
                    val response = gson.fromJson(result, JsonObject::class.java)
                    if (response.get("success")?.asBoolean == true) {
                        val userId = response.get("userId").asInt
                        onSuccess(userId)
                    } else {
                        onError(response.get("message")?.asString ?: "登入失敗")
                    }
                } else {
                    onError("伺服器無回應")
                }
            } catch (e: Exception) {
                onError(e.message ?: "登入失敗")
            } finally {
                _isLoading.value = false
            }
        }
    }
}

data class LoginUiState(
    val formState: LoginProperty = LoginProperty(),
    val isSubmitEnabled: Boolean = false
)