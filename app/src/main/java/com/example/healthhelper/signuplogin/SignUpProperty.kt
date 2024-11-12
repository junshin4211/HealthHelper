package com.example.healthhelper.signuplogin

data class SignUpProperty(
    val account: String = "",                    // 對應後端的 account
    val password: String = "",                   // 對應後端的 password
    val confirmPassword: String = "",            // 用於前端確認密碼
    //val passwordErrorMessage: String = "",
    val confirmPasswordErrorMessage: String = "",
    val username: String = "",                   // 對應後端的 username
    val email: String = "",                      // 會被映射為後端的 userEmail
    val phone: String = "",                      // 會被映射為後端的 phoneno
    val gender: String = "",                     // 會被轉換為後端的 gender (0,1,2)
    val birthDate: String = "",                  // 會被映射為後端的 birthday
    val isNutritionist: Boolean = false,         // 用於決定 roleID (1 或 2)
    val isNormalUser: Boolean = false,
    val certificate: String = "",                // 用於前端顯示檔案名稱
    val certificateBase64: String = "",          // 用於傳送給後端的證書資料

    // UI 狀態相關屬性
    val passwordVisible: Boolean = false,        // 密碼顯示/隱藏
    val confirmPasswordVisible: Boolean = false, // 確認密碼顯示/隱藏
    val expanded: Boolean = false,               // 性別下拉選單狀態
    val showDatePicker: Boolean = false,         // 日期選擇器顯示狀態

    // 錯誤訊息相關
    val passwordErrorMessage: String = "",       // 密碼錯誤訊息
    val phoneErrorMessage: String = ""           // 電話錯誤訊息
)