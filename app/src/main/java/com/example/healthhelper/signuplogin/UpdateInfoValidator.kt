package com.example.healthhelper.signuplogin

class UpdateInfoValidator {
    open class ValidationError(val message: String)

    private object Errors {
        object EmptyUsername : ValidationError("請輸入姓名")
        object UsernameTooLong : ValidationError("姓名長度不能超過30個字元")
        object EmptyEmail : ValidationError("請輸入Email")
        object EmailTooLong : ValidationError("Email長度不能超過50個字元")
        object InvalidEmail : ValidationError("Email格式不正確")
        object InvalidPhone : ValidationError("電話號碼必須以09開頭且為10碼")
        object InvalidGender : ValidationError("性別選項不正確")
        object PasswordMismatch : ValidationError("密碼與確認密碼不一致")
        object PasswordTooLong : ValidationError("密碼長度不能超過30個字元")
        object BirthdayRequired : ValidationError("請選擇生日")
        object InvalidBirthdayFormat : ValidationError("生日格式不正確")
    }

    fun validateUpdateInfo(formState: UpdateInfoProperty): List<ValidationError> {
        val errors = mutableListOf<ValidationError>()

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

        // 驗證電話（允許空值）
        if (formState.phone.isNotEmpty() && !formState.phone.matches(Regex("^09\\d{8}$"))) {
            errors.add(Errors.InvalidPhone)
        }

        // 驗證性別（允許空值）
        if (formState.gender.isNotEmpty() &&
            !listOf("男", "女", "不提供").contains(formState.gender)) {
            errors.add(Errors.InvalidGender)
        }

        // 驗證密碼（如果有輸入，則需要驗證）
        if (formState.password.isNotEmpty()) {
            if (formState.password.length > 30) {
                errors.add(Errors.PasswordTooLong)
            }
            if (formState.password != formState.confirmPassword) {
                errors.add(Errors.PasswordMismatch)
            }
        }

        // 驗證生日格式（如果有輸入）
        if (formState.birthDate.isNotEmpty()) {
            try {
                java.time.LocalDate.parse(formState.birthDate)
            } catch (e: Exception) {
                errors.add(Errors.InvalidBirthdayFormat)
            }
        }

        return errors
    }
}


