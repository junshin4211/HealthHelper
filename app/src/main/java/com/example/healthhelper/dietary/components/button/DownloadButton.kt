package com.example.healthhelper.dietary.components.button

import android.app.Activity
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.R
import com.example.healthhelper.dietary.gson.toJson
import com.example.healthhelper.dietary.util.file.savingfile.saveEternal
import com.example.healthhelper.dietary.viewmodel.DiaryViewModel
import java.io.File
import kotlin.io.path.Path

@Composable
fun DownloadButton(
    diaryViewModel:DiaryViewModel = viewModel()
){
    var savingFileFlag by remember { mutableStateOf(false) }

    val diaries by diaryViewModel.data.collectAsState()

    IconButton(
        colors = IconButtonColors(
            containerColor = Color.Blue,
            contentColor = Color.Blue,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Gray,
        ), onClick = {
            savingFileFlag = true
        }) {
        Image(
            painter = painterResource(R.drawable.download),
            contentDescription = stringResource(R.string.download_icon),
        )
    }

    if (savingFileFlag) {
        val fileName = "download.txt"
        val jsonString = diaries.toJson()
        val folder = File(
            LocalContext.current.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            fileName,
        )
        val fullFilePath = Path(folder.toPath().toString(), fileName)
        val fullFile = fullFilePath.toFile()

        val saveSuccessfully = saveEternal(fullFile, jsonString)

        val currentActivity = LocalContext.current as Activity
        val toastMessage =
            if (saveSuccessfully) "Data saved successfully." else "Data saved failed."

        Toast.makeText(currentActivity, toastMessage, Toast.LENGTH_LONG).show()

        savingFileFlag = false
    }
}