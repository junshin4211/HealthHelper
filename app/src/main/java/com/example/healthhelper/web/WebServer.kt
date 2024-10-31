package com.example.healthhelper.web

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

const val serverUrl = "http://10.0.2.2:8080/HealthyHelperServer"


private const val myTag = "tag_Common"

suspend fun httpPost(
    url: String,
    dataOut: String
): String {
    var dataIn = ""
    withContext(Dispatchers.IO) {
        (URL(url).openConnection() as? HttpURLConnection)?.run {
            Log.d("", "openConnection()")
            doInput = true
            doOutput = true
            setChunkedStreamingMode(0)
            useCaches = false
            requestMethod = "POST"
            setRequestProperty("content-type", "application/json")
            setRequestProperty("charset", "utf-8")
            Log.d(myTag, "dataOut: $dataOut")
            Log.d(myTag, "dataOut: ${url}")
            outputStream.bufferedWriter().use { it.write(dataOut) }
//            Log.d(myTag, "dataOut: $responseCode")

            if (responseCode == 200) {
                inputStream.bufferedReader().useLines { lines ->
                    dataIn = lines.fold("") { text, line -> "$text$line" }
                    Log.d(myTag, "dataIn: $dataIn")
                }
            }
        }
    }
    return dataIn
}