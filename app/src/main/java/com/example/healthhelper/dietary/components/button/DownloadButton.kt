package com.example.healthhelper.dietary.components.button

import android.content.Context
import android.os.Environment
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.R
import com.example.healthhelper.attr.color.defaultcolor.DefaultColorViewModel
import com.example.healthhelper.dietary.gson.toJson
import com.example.healthhelper.dietary.util.file.savingfile.SavingFile
import com.example.healthhelper.dietary.viewmodel.DiaryViewModel

@Composable
fun DownloadButton(
    context:Context,
    diaryViewModel:DiaryViewModel = viewModel()
){
    var savingFileFlag by remember { mutableStateOf(false) }

    val diaries by diaryViewModel.data.collectAsState()

    IconButton(
        colors = DefaultColorViewModel.iconButtonColors,
        onClick = {
            savingFileFlag = true
        }) {
        Image(
            painter = painterResource(R.drawable.download),
            contentDescription = stringResource(R.string.download_icon),
        )
    }

    if (savingFileFlag) {
        val jsonString = diaries.toJson()

        SavingFile(
            context = context,
            filename = "download.txt",
            data = jsonString,
            type = Environment.DIRECTORY_DOWNLOADS,
        )
        savingFileFlag = false
    }
}