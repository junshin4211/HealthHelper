package com.example.healthhelper.plan.ui

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.healthhelper.R

class CustomDialog {
    @Composable
    fun DeleteDataDialog(
        @StringRes title: Int,
        onConfirm: () -> Unit,
        onDismiss: () -> Unit
    ) {
        // alert dialog屬於簡易、制式化的對話視窗
        AlertDialog(
            // 點擊對話視窗外部或back按鈕時呼叫，並非點擊dismissButton時呼叫
            onDismissRequest = onDismiss,
            title = { Text(text = stringResource(title)) },
            // 設定確定按鈕
            confirmButton = {
                CustomButton().CreateButton(
                    text = stringResource(R.string.cancel),
                    color = R.color.primarycolor,
                    onClick = { onDismiss () },
                    textcolor = R.color.white
                )
            },
            // 設定取消按鈕
            dismissButton = {
                CustomButton().CreateButton(
                    text = stringResource(R.string.delete),
                    color = R.color.primarycolor,
                    onClick = { onConfirm () },
                    textcolor = R.color.white
                )
            }
        )
    }
}