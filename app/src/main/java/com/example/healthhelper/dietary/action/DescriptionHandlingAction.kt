package com.example.healthhelper.dietary.action

import androidx.compose.runtime.Composable

object DescriptionHandlingAction {
    val TAG = "tag_DescriptionHandlingAction"

    private var currentAction: (@Composable () -> Unit)? = null

    fun setAction(newAction: (@Composable () -> Unit)?) {
        currentAction = newAction
    }

    fun getAction(): (@Composable () -> Unit)? {
        return currentAction
    }

    @Composable
    fun callAction() {
        currentAction?.let {
            it()
        }
    }
}