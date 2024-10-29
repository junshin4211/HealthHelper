package com.example.healthhelper.signuplogin

class SignUpValidator {
    open class ValidationError(val message: String)

    private object Errors {
        object EmptyAccount : ValidationError("請輸入帳號")
        object AccountTooLong : ValidationError("帳號長度不能超過30個字元")
        object EmptyPassword : ValidationError("請輸入密碼")
        object PasswordTooLong : ValidationError("密碼長度不能超過30個字元")
        object EmptyUsername : ValidationError("請輸入姓名")
        object UsernameTooLong : ValidationError("姓名長度不能超過30個字元")
        object EmptyEmail : ValidationError("請輸入Email")
        object EmailTooLong : ValidationError("Email長度不能超過50個字元")
        object InvalidEmail : ValidationError("Email格式不正確")
        object EmptyGender : ValidationError("請選擇性別")
        object InvalidGender : ValidationError("性別選項不正確")
        object EmptyBirthDate : ValidationError("請選擇生日")
        object InvalidPhone : ValidationError("電話號碼必須以09開頭且為10碼")
        object EmptyCertificate : ValidationError("請上傳營養師證書")
        object InvalidRoleID : ValidationError("使用者身分不正確")


    }

    fun validateSignUp(formState: SignUpProperty): List<ValidationError> {
        val errors = mutableListOf<ValidationError>()

        // 驗證帳號
        if (formState.account.isEmpty()) {
            errors.add(Errors.EmptyAccount)
        } else if (formState.account.length > 30) {
            errors.add(Errors.AccountTooLong)
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

        // 驗證 Email
        if (formState.email.isEmpty()) {
            errors.add(Errors.EmptyEmail)
        } else {
            if (formState.email.length > 50) {
                errors.add(Errors.EmailTooLong)
            }
            if (!formState.email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))) {
                errors.add(Errors.InvalidEmail)
            }
        }

        // 驗證性別
        if (formState.gender.isEmpty()) {
            errors.add(Errors.EmptyGender)
        } else {
            val genderValue = when (formState.gender) {
                "男" -> 0
                "女" -> 1
                "不提供" -> 2
                else -> -3
            }
            if (genderValue == -1) {
                errors.add(Errors.InvalidGender)
            }
        }

        // 驗證電話
        if (formState.phone.isNotEmpty() && !formState.phone.matches(Regex("^09\\d{8}$"))) {
            errors.add(Errors.InvalidPhone)
        }

        // 驗證生日
        if (formState.birthDate.isEmpty()) {
            errors.add(Errors.EmptyBirthDate)
        }

        // 驗證身分和證書
        if (formState.isNutritionist) {
            if (formState.certificate.isEmpty()) {
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