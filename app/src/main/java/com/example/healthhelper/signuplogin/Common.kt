package com.example.healthhelper.signuplogin

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
        try {
            Log.d("NetworkDebug", "Attempting connection to: $url")
            Log.d("NetworkDebug", "Sending data: $dataOut")

            (URL(url).openConnection() as? HttpURLConnection)?.run {
                Log.d("NetworkDebug", "Connection opened")
                doInput = true
                doOutput = true
                setChunkedStreamingMode(0)
                useCaches = false
                requestMethod = "POST"
                setRequestProperty("content-type", "application/json")
                setRequestProperty("charset", "utf-8")

                outputStream.bufferedWriter().use { it.write(dataOut) }
                Log.d("NetworkDebug", "Data written")

                if (responseCode == 200) {
                    inputStream.bufferedReader().useLines { lines ->
                        dataIn = lines.fold("") { text, line -> "$text$line" }
                        Log.d("NetworkDebug", "Response received: $dataIn")
                    }
                } else {
                    Log.e("NetworkDebug", "Error response code: $responseCode")
                }
            } ?: run {
                Log.e("NetworkDebug", "Failed to create connection")
            }
        } catch (e: Exception) {
            Log.e("NetworkDebug", "Exception in httpPost: ${e.message}")
            e.printStackTrace()
        }
    }
    return dataIn
}