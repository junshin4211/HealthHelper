package com.example.healthhelper.signuplogin

data class SignUpProperty(
    var account: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var passwordVisible: Boolean = false,
    var confirmPasswordVisible: Boolean = false,
    var username: String = "",
    var gender: String = "",
    var expanded: Boolean = false, // 控制性別下拉選單展開狀態
    var phone: String = "",
    var email: String = "",
    var birthDate: String = "",
    var isNutritionist: Boolean = false,
    var certificate: String = "",
    var passwordErrorMessage: String = "",
    var phoneErrorMessage: String = "",
    var showDatePicker: Boolean = false
)