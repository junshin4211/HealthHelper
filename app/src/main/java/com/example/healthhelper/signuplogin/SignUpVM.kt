package com.example.healthhelper.signuplogin

import android.content.Context
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.dietary.gson.gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

   // private val serverUrl = "http://10.0.2.2:8080/HealthyHelperServer" // 替換成你的伺服器網址

    // 表單更新方法
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

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    confirmPassword = confirmPassword,
                    passwordErrorMessage = if (confirmPassword != currentState.formState.password)
                        "密碼不一致" else ""
                )
            )
        }
    }

    fun updateUsername(username: String) {
        _uiState.update { currentState ->
            currentState.copy(formState = currentState.formState.copy(username = username))
        }
    }

    fun updateEmail(email: String) {
        _uiState.update { currentState ->
            currentState.copy(formState = currentState.formState.copy(email = email))
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

    fun updateBirthDate(birthDate: String) {
        _uiState.update { currentState ->
            currentState.copy(formState = currentState.formState.copy(birthDate = birthDate))
        }
    }

    fun updateIsNutritionist(isNutritionist: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(formState = currentState.formState.copy(isNutritionist = isNutritionist))
        }
    }

    // UI 狀態切換
    fun togglePasswordVisibility() {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    passwordVisible = !currentState.formState.passwordVisible
                )
            )
        }
    }

    fun toggleConfirmPasswordVisibility() {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    confirmPasswordVisible = !currentState.formState.confirmPasswordVisible
                )
            )
        }
    }

    fun toggleGenderDropdown() {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    expanded = !currentState.formState.expanded
                )
            )
        }
    }

    fun toggleDatePicker() {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    showDatePicker = !currentState.formState.showDatePicker
                )
            )
        }
    }

    // 檔案處理
    fun handleFileSelection(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                val base64String = convertFileToBase64(context, uri)
                if (base64String != null) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            formState = currentState.formState.copy(
                                certificate = "已選擇檔案",
                                certificateBase64 = base64String
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun convertFileToBase64(context: Context, uri: Uri): String? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val bytes = inputStream.readBytes()
                Base64.encodeToString(bytes, Base64.DEFAULT)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 表單提交
    // 在 SignUpViewModel 中修改 submitForm 方法

    fun submitForm(context: Context, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true

                // 資料驗證
                val validator = SignUpValidator()
                val accountExists = false // 假設帳號不存在，您可以在將來替換此值
                val validationErrors = validator.validateSignUp(_uiState.value.formState, accountExists)

                if (validationErrors.isNotEmpty()) {
                    withContext(Dispatchers.Main) {
                        onError(validationErrors.first().message)
                    }
                    return@launch
                }

                // 準備 JSON 數據
                val jsonObject = JsonObject().apply {
                    addProperty("account", _uiState.value.formState.account)
                    addProperty("password", _uiState.value.formState.password)
                    addProperty("username", _uiState.value.formState.username)
                    addProperty("userEmail", _uiState.value.formState.email)
                    addProperty("phoneno", _uiState.value.formState.phone)
                    val genderValue = when (_uiState.value.formState.gender) {
                        "男" -> 0
                        "女" -> 1
                        else -> 2
                    }
                    addProperty("gender", genderValue)
                    addProperty("birthday", _uiState.value.formState.birthDate)
                    addProperty("roleID", if (_uiState.value.formState.isNutritionist) 2 else 1)
                    if (_uiState.value.formState.isNutritionist) {
                        addProperty("certificate", _uiState.value.formState.certificateBase64)
                    }
                }

                 //使用 httpPost 發送註冊請求
                val response = httpPost(
                    url = "$serverUrl/user/register",
                    dataOut = jsonObject.toString()
                )

                // 處理伺服器回應
                withContext(Dispatchers.Main) {
                    val jsonResponse = gson.fromJson(response, JsonObject::class.java)
                    if (jsonResponse.get("success")?.asBoolean == true) {
                        onSuccess()
                    } else {
                        onError(jsonResponse.get("message")?.asString ?: "註冊失敗")
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("註冊失敗: ${e.message}")
                }
            } finally {
                _isLoading.value = false
            }
        }
    }




// UI 狀態數據類
data class SignUpUiState(
    val formState: SignUpProperty = SignUpProperty()
)
}
