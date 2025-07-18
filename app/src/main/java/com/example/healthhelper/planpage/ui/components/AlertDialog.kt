package com.example.healthhelper.planpage.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthhelper.R
import com.example.healthhelper.ui.theme.HealthHelperTheme

/** 簡單彈出對話視窗 */
@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    title: @Composable () -> Unit,
    text: @Composable () -> Unit,
    onConfirm: () -> Unit,
){

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = title,
        text = text,
        // 設定確定按鈕
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(stringResource(R.string.confirm))
            }
        },
        // 設定取消按鈕
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}


@Preview(showBackground = true, name = "Standard Preview (Forced Styles in Lambda)")
@Composable
fun PreviewCustomAlertDialog() {
    HealthHelperTheme {
        CustomAlertDialog(
            onDismissRequest = {},
            title = {
                Text(
                    "預覽標題 (Lambda)",
                    color = Color.Magenta, // 非常規顏色，易於識別
                    fontSize = 22.sp
                )
            },
            text = {
                Text(
                    "預覽內容 (Lambda)",
                    color = Color.Green, // 非常規顏色
                    fontSize = 18.sp
                )
            },
            onConfirm = {}
        )
    }
}
