package com.example.healthhelper.person.personVM

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object LoginState {
    var isLogin:MutableState<Boolean?> = mutableStateOf(null)
}