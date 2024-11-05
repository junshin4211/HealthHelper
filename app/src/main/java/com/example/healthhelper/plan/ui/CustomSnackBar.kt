package com.example.healthhelper.plan.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CustomSnackBar {

    suspend fun CreateSnackBar(
        message: String,
        withDismissAction: Boolean = true,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short,
        snackbarHostState: SnackbarHostState
    ){
        snackbarHostState.showSnackbar(
            message = message,
            withDismissAction = withDismissAction,
            actionLabel = actionLabel,
            duration = duration,
        )
    }
}