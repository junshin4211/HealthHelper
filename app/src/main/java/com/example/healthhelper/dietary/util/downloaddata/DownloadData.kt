package com.example.healthhelper.dietary.util.downloaddata

import android.content.Context
import android.os.Environment
import androidx.compose.runtime.Composable
import com.example.healthhelper.dietary.gson.toJson
import com.example.healthhelper.dietary.util.file.savingfile.SavingFile

@Composable
fun DownloadData(
    context:Context,
    vo: Any
){
    val jsonString = vo.toJson()
    SavingFile(
        context = context,
        filename = "download.txt",
        data = jsonString,
        type = Environment.DIRECTORY_DOWNLOADS,
    )
}