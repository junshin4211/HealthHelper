package com.example.healthhelper.signuplogin

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateInfoVM : ViewModel() {
    private val _uiState = MutableStateFlow(UpdateInfoUIState())
    val uiState: StateFlow<UpdateInfoUIState> = _uiState.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val gson = Gson()

    // 設置用戶資料
    fun setUserData(user: User) {
        Log.d("UpdateInfoVM", """
            Setting user data:
            Account: ${user.account}
            Username: ${user.username}
            Email: ${user.userEmail}
            Phone: ${user.phoneno}
            Gender: ${user.gender}
            Birthday: ${user.birthday}
        """.trimIndent())

        if (user.account.isNotEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(
                    formState = currentState.formState.copy(
                        account = user.account,
                        username = user.username,
                        email = user.userEmail,
                        phone = user.phoneno ?: "",
                        gender = when (user.gender) {
                            0 -> "男"
                            1 -> "女"
                            else -> "不提供"
                        },
                        birthDate = user.birthday ?: ""
                    ),
                    currentUser = user
                )
            }
            Log.d("UpdateInfoVM", "Successfully set user data with account: ${user.account}")
        } else {
            Log.e("UpdateInfoVM", "Received empty account!")
        }
    }

    // 表單提交
    fun submitForm(
        context: Context,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        Log.d("UpdateInfoVM", "Submitting form with account: ${_uiState.value.formState.account}")

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true

                // 構建 gender 值並準備 JSON 資料
                val genderValue = when (_uiState.value.formState.gender) {
                    "男" -> 0
                    "女" -> 1
                    else -> 2
                }

                val jsonObject = JsonObject().apply {
                    addProperty("account", _uiState.value.formState.account)
                    addProperty("username", _uiState.value.formState.username)
                    addProperty("userEmail", _uiState.value.formState.email)
                    addProperty("phoneno", _uiState.value.formState.phone)
                    addProperty("gender", genderValue)
                    addProperty("birthday", _uiState.value.formState.birthDate)
                }

                Log.d("UpdateInfoVM", "Prepared JSON Data (with account): ${jsonObject.toString()}")
                Log.d("UpdateInfoVM", "Sending update request to: $serverUrl/user/update")

                val response = httpPost(
                    url = "$serverUrl/user/update",
                    dataOut = jsonObject.toString()
                )

                Log.d("UpdateInfoVM", "Received response: $response")

                withContext(Dispatchers.Main) {
                    val jsonResponse = gson.fromJson(response, JsonObject::class.java)
                    if (jsonResponse.get("success")?.asBoolean == true) {
                        Log.d("UpdateInfoVM", "Update successful")
                        onSuccess()
                    } else {
                        val errorMessage = jsonResponse.get("message")?.asString ?: "更新失敗"
                        Log.e("UpdateInfoVM", "Update failed: $errorMessage")
                        onError(errorMessage)
                    }
                }
            } catch (e: Exception) {
                Log.e("UpdateInfoVM", "Error: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    onError("Error: ${e.message}")
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    // UI 更新函數
    fun updateUsername(username: String) {
        _uiState.update { currentState ->
            currentState.copy(formState = currentState.formState.copy(username = username))
        }
    }

    fun toggleGenderDropdown() {
        _uiState.update { currentState ->
            currentState.copy(formState = currentState.formState.copy(expanded = !currentState.formState.expanded))
        }
    }

    fun updateGender(gender: String) {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    gender = gender,
                    expanded = false
                )
            )
        }
    }

    fun updatePhone(phone: String) {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    phone = phone,
                    phoneErrorMessage = if (!phone.matches(Regex("^09\\d{8}$")) && phone.isNotEmpty())
                        "手機號碼格式不正確" else ""
                )
            )
        }
    }

    fun updateEmail(email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    email = email,
                    emailErrorMessage = if (!email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$")) && email.isNotEmpty())
                        "Email格式不正確" else ""
                )
            )
        }
    }

    fun toggleDatePicker() {
        _uiState.update { currentState ->
            currentState.copy(formState = currentState.formState.copy(showDatePicker = !currentState.formState.showDatePicker))
        }
    }

    fun updateBirthDate(birthDate: String) {
        _uiState.update { currentState ->
            currentState.copy(formState = currentState.formState.copy(birthDate = birthDate))
        }
    }
}

// Data class for UI state
data class UpdateInfoUIState(
    val formState: UpdateInfoProperty = UpdateInfoProperty(),
    val currentUser: User? = null
)
