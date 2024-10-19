package com.example.healthhelper.dietary.util.file.savingfile

import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files

const val TAG = "savingFile"

fun saveEternal(file: File, jsonStr: String): Boolean {
    val parentDirectory = file.parentFile
    try {
        if (!Files.exists(parentDirectory?.toPath())) {
            Files.createFile(parentDirectory?.toPath())
        }
        if (!mediaMounted()) {
            return false
        }

        FileOutputStream(parentDirectory).bufferedWriter().use {
            it.write(jsonStr)
        }
    }catch (ex : Exception){
        Log.d(TAG,"There are an exception, exception:${ex.message},stacktrace:${ex.stackTrace.toList().joinToString { "," }.toString()}")
    }
    return true
}

fun mediaMounted(): Boolean {
    val state = Environment.getExternalStorageState()
    return state == Environment.MEDIA_MOUNTED
}