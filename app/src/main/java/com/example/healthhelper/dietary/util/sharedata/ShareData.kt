package com.example.healthhelper.dietary.util.sharedata

import android.content.Context
import android.content.Intent

fun ShareData(
    context: Context,
    data:String = "",
){
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, data)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}