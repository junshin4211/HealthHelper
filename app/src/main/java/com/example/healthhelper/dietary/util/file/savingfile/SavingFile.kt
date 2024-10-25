package com.example.healthhelper.dietary.util.file.savingfile

import android.app.Activity
import android.content.Context
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.io.File
import java.io.FileOutputStream

@Composable
fun SavingFile(
    context: Context,
    type:String,
    filename:String,
    data:String,
){
    val TAG = "tag_SavingFile"

    var canDownloadFile by remember { mutableStateOf(true) }

    val directoryFile = Environment.getExternalStoragePublicDirectory(type)

    val fullFile = File(directoryFile,filename)
    directoryFile?.let{
        if(!directoryFile.exists()){
            Log.d(TAG,"directory does NOT exists.")
            val status = directoryFile.mkdir()
            if(!status){
                Log.d(TAG,"directory created failed.")
                Toast.makeText(context,"directory created failed.",Toast.LENGTH_LONG).show()
                canDownloadFile = false
            }
        }
        if(!fullFile.exists()){
            Log.d(TAG,"The fullFile does NOT exists")
            val status = fullFile.createNewFile()
            if(!status){
                Log.d(TAG,"fullFile created failed.")
                Toast.makeText(context,"fullFile is NOT considered as a File.",Toast.LENGTH_LONG).show()
                canDownloadFile = false
            }
        }

        if(!fullFile.isFile){
            Log.d(TAG,"fullFile is NOT considered as a File.")
            Toast.makeText(context,"fullFile is NOT considered as a File.",Toast.LENGTH_LONG).show()
            canDownloadFile = false
        }

        if(canDownloadFile){
            val fileOutputStream = FileOutputStream(fullFile,false)
            fileOutputStream.write(data.toByteArray())
            fileOutputStream.close()
            Log.d(TAG,"Successfully download data.")
            Toast.makeText(context,"Successfully download data.",Toast.LENGTH_LONG).show()
        }
    }
}