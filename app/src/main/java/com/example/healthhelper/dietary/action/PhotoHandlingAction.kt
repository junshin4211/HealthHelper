package com.example.healthhelper.dietary.action

import androidx.compose.runtime.Composable

object PhotoHandlingAction {
    private var currentAction: (@Composable () -> Unit)? = null

    fun setAction(newAction: (@Composable () -> Unit)? ){
        currentAction = newAction
    }

    fun getAction(): (@Composable () -> Unit)? {
        return currentAction
    }

    @Composable
    fun callAction(){
        currentAction?.let { it() }
    }
}