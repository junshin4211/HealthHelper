package com.example.healthhelper.signuplogin

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

private const val SERVER_URL = "http://10.0.2.2:8080/HealthyHelperServer"

class SignUpViewModel : ViewModel() {
    // UI 狀態
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    // Loading 狀態
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // 驗證錯誤
    private val _validationErrors = MutableStateFlow<List<SignUpValidator.ValidationError>>(emptyList())
    val validationErrors: StateFlow<List<SignUpValidator.ValidationError>> = _validationErrors.asStateFlow()

    private val validator = SignUpValidator()
    private val gson = Gson()

    // 更新帳號
    fun updateAccount(account: String) {
        _uiState.update { currentState ->
            currentState.copy(formState = currentState.formState.copy(account = account))
        }
        validateForm()
    }

    // 更新密碼
    fun updatePassword(password: String) {
        _uiState.update { currentState ->
            currentState.formState.copy(password = password).let { newFormState ->
                currentState.copy(
                    formState = newFormState.copy(
                        passwordErrorMessage = if (newFormState.confirmPassword.isNotEmpty()
                            && newFormState.confirmPassword != password) {
                            "密碼不相符"
                        } else {
                            ""
                        }
                    )
                )
            }
        }
        validateForm()
    }

    // 更新確認密碼
    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.update { currentState ->
            currentState.formState.copy(confirmPassword = confirmPassword).let { newFormState ->
                currentState.copy(
                    formState = newFormState.copy(
                        passwordErrorMessage = if (newFormState.password != confirmPassword) {
                            "密碼不相符"
                        } else {
                            ""
                        }
                    )
                )
            }
        }
        validateForm()
    }

    // 更新使用者名稱
    fun updateUsername(username: String) {
        _uiState.update { currentState ->
            currentState.copy(formState = currentState.formState.copy(username = username))
        }
        validateForm()
    }

    // 更新性別
    fun updateGender(gender: String) {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    gender = gender,
                    expanded = false
                )
            )
        }
        validateForm()
    }

    // 更新電話
    fun updatePhone(phone: String) {
        if (phone.length <= 10) {
            _uiState.update { currentState ->
                currentState.copy(
                    formState = currentState.formState.copy(
                        phone = phone,
                        phoneErrorMessage = if (phone.isNotEmpty() && (!phone.startsWith("09") || phone.length != 10)) {
                            "電話號碼必須以09開頭且為10碼"
                        } else {
                            ""
                        }
                    )
                )
            }
            validateForm()
        }
    }

    fun updateEmail(email: String) {
        _uiState.update { currentState ->
            currentState.copy(formState = currentState.formState.copy(email = email))
        }
        validateForm()
    }

    // 更新生日
    fun updateBirthDate(birthDate: String) {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    birthDate = birthDate,
                    showDatePicker = false
                )
            )
        }
        validateForm()
    }

    // 更新是否為營養師
    fun updateIsNutritionist(isNutritionist: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    isNutritionist = isNutritionist,
                    certificate = if (!isNutritionist) "" else currentState.formState.certificate
                )
            )
        }
        validateForm()
    }

    // 更新證書
    fun updateCertificate(certificateUri: Uri?) {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    certificate = certificateUri?.lastPathSegment ?: "未選擇檔案"
                )
            )
        }
        validateForm()
    }

    // 切換密碼可見性
    fun togglePasswordVisibility() {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    passwordVisible = !currentState.formState.passwordVisible
                )
            )
        }
    }

    // 切換確認密碼可見性
    fun toggleConfirmPasswordVisibility() {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    confirmPasswordVisible = !currentState.formState.confirmPasswordVisible
                )
            )
        }
    }

    // 切換性別下拉選單
    fun toggleGenderDropdown() {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    expanded = !currentState.formState.expanded
                )
            )
        }
    }

    // 切換日期選擇器
    fun toggleDatePicker() {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    showDatePicker = !currentState.formState.showDatePicker
                )
            )
        }
    }

    // 網路請求函數
    private suspend fun httpPost(url: String, dataOut: String): String {
        var dataIn = ""
        withContext(Dispatchers.IO) {
            (URL(url).openConnection() as? HttpURLConnection)?.run {
                doInput = true
                doOutput = true
                setChunkedStreamingMode(0)
                useCaches = false
                requestMethod = "POST"
                setRequestProperty("content-type", "application/json")
                setRequestProperty("charset", "utf-8")

                outputStream.bufferedWriter().use { it.write(dataOut) }
                if (responseCode == 200) {
                    inputStream.bufferedReader().useLines { lines ->
                        dataIn = lines.fold("") { text, line -> "$text$line" }
                    }
                }
            }
        }
        return dataIn
    }

    // 表單驗證
    private fun validateForm() {
        val errors = validator.validateSignUp(_uiState.value.formState)
        _validationErrors.value = errors
        _uiState.update { currentState ->
            currentState.copy(
                isSubmitEnabled = errors.isEmpty() &&
                        _uiState.value.formState.password == _uiState.value.formState.confirmPassword
            )
        }
    }

    // 註冊函數
    private suspend fun register(): Boolean {
        val url = "$SERVER_URL/user/register"
        val jsonObject = JsonObject().apply {
            addProperty("account", _uiState.value.formState.account)
            addProperty("password", _uiState.value.formState.password)
            addProperty("username", _uiState.value.formState.username)
            addProperty("userEmail", _uiState.value.formState.email)
            addProperty("phoneno", _uiState.value.formState.phone)
            addProperty("gender", _uiState.value.formState.gender)
            addProperty("birthday", _uiState.value.formState.birthDate)
            addProperty("isNutritionist", _uiState.value.formState.isNutritionist)
            addProperty("certificate", _uiState.value.formState.certificate)
        }

        val result = httpPost(url, jsonObject.toString())

        // 新增錯誤處理
        return try {
            if (result.isEmpty()) {
                return false
            }
            val response = gson.fromJson(result, JsonObject::class.java)
            response?.get("success")?.asBoolean ?: false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun submitForm(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                validateForm()

                if (_validationErrors.value.isEmpty() &&
                    _uiState.value.formState.password == _uiState.value.formState.confirmPassword
                ) {
                    val url = "$SERVER_URL/user/register"
                    val genderValue = when (_uiState.value.formState.gender) {
                        "男" -> 0
                        "女" -> 1
                        "不提供" -> 2
                        else -> 3
                    }

                    val jsonObject = JsonObject().apply {
                        addProperty("account", _uiState.value.formState.account)
                        addProperty("password", _uiState.value.formState.password)
                        addProperty("username", _uiState.value.formState.username)
                        addProperty("userEmail", _uiState.value.formState.email)
                        addProperty("phoneno", _uiState.value.formState.phone)
                        addProperty("gender", genderValue)
                        addProperty("birthday", _uiState.value.formState.birthDate)
                        addProperty("roleID", if (_uiState.value.formState.isNutritionist) 2 else 1)
                        // 如果需要證書
                        if (_uiState.value.formState.isNutritionist) {
                            addProperty("certificate", _uiState.value.formState.certificate)
                        }
                    }

                    val result = httpPost(url, jsonObject.toString())

                    if (result.isNotBlank()) {
                        try {
                            val response = gson.fromJson(result, JsonObject::class.java)
                            if (response.get("success")?.asBoolean == true) {
                                resetForm()
                                onSuccess()
                            } else {
                                onError(response.get("message")?.asString ?: "註冊失敗")
                            }
                        } catch (e: Exception) {
                            onError("伺服器回應格式錯誤: ${e.message}")
                        }
                    } else {
                        onError("伺服器無回應")
                    }
                } else {
                    onError(_validationErrors.value.firstOrNull()?.message ?: "表單驗證失敗")
                }
            } catch (e: Exception) {
                onError("註冊失敗: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }


    // 重置表單
    fun resetForm() {
        _uiState.value = SignUpUiState()
        _validationErrors.value = emptyList()
    }
}

// UI 狀態數據類
data class SignUpUiState(
    val formState: SignUpProperty = SignUpProperty(),
    val isSubmitEnabled: Boolean = false
)