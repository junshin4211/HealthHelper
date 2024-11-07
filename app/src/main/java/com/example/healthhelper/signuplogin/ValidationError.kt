package com.example.healthhelper.signuplogin

class SignUpValidator {
    open class ValidationError(val message: String)

    private object Errors {
        object EmptyAccount : ValidationError("帳號必須填寫")
        object AccountExists : ValidationError("帳號已經存在，無法註冊")
        object AccountTooLong : ValidationError("帳號長度不能超過30個字元")
        object EmptyPassword : ValidationError("密碼必須填寫")
        object PasswordTooLong : ValidationError("密碼長度不能超過30個字元")
        object EmptyUsername : ValidationError("姓名必須填寫")
        object UsernameTooLong : ValidationError("姓名長度不能超過30個字元")
        object EmailTooLong : ValidationError("Email長度不能超過50個字元")
        object InvalidEmail : ValidationError("Email格式不正確")
        object InvalidPhone : ValidationError("手機號碼格式不正確")
        object InvalidRoleID : ValidationError("使用者身分必須選擇")
        object EmptyCertificate : ValidationError("營養師必須上傳證書")
    }

    fun validateSignUp(formState: SignUpProperty, accountExists: Boolean): List<ValidationError> {
        val errors = mutableListOf<ValidationError>()

        if (formState.account.isEmpty()) {
            errors.add(Errors.EmptyAccount)
        } else if (formState.account.length > 30) {
            errors.add(Errors.AccountTooLong)
        } else if (accountExists) {
            errors.add(Errors.AccountExists) // 如果帳號已存在，加入此錯誤
        }

        // 驗證密碼
        if (formState.password.isEmpty()) {
            errors.add(Errors.EmptyPassword)
        } else if (formState.password.length > 30) {
            errors.add(Errors.PasswordTooLong)
        }

        // 驗證姓名
        if (formState.username.isEmpty()) {
            errors.add(Errors.EmptyUsername)
        } else if (formState.username.length > 30) {
            errors.add(Errors.UsernameTooLong)
        }
        //驗證信箱
        if (formState.email.isNotEmpty()) {
            if (formState.email.length > 50) {
                errors.add(Errors.EmailTooLong)
            }
            if (!formState.email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))) {
                errors.add(Errors.InvalidEmail)
            }
        }

        // 驗證電話
        if (formState.phone.isNotEmpty() && !formState.phone.matches(Regex("^09\\d{8}$"))) {
            errors.add(Errors.InvalidPhone)
        }

        // 驗證身分和證書
        if (formState.isNutritionist) {
            if (formState.certificateBase64.isEmpty()) {  // 改用 certificateBase64 來檢查
                errors.add(Errors.EmptyCertificate)
            }
        }



        return errors
    }

    companion object {
        fun customError(message: String): ValidationError {
            return object : ValidationError(message) {}
        }
    }
}

