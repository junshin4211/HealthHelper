package com.example.healthhelper.signuplogin

data class UpdateInfoProperty(
    // 表單數據
    val account: String = "",
    val password: String = "",                   // 密碼
    val confirmPassword: String = "",            // 確認密碼
    val username: String = "",                   // 姓名
    val email: String = "",                      // 電子郵件
    val phone: String = "",                      // 電話號碼
    val gender: String = "",                     // 性別
    val birthDate: String = "",                  // 生日

    // UI 狀態
    val passwordVisible: Boolean = false,         // 密碼是否顯示
    val confirmPasswordVisible: Boolean = false,  // 確認密碼是否顯示
    val expanded: Boolean = false,               // 性別下拉選單是否展開
    val showDatePicker: Boolean = false,         // 日期選擇器是否顯示

    // 錯誤訊息
    val passwordErrorMessage: String = "",        // 密碼錯誤訊息
    val phoneErrorMessage: String = "",          // 電話錯誤訊息
    val emailErrorMessage: String = ""           // 電子郵件錯誤訊息
)