package com.example.healthhelper.web

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

const val serverProtocol = "http"

// for running at android emulator
const val serverIPv4 = "10.0.2.2"

// for running at server (localhost)
//const val serverIPv4 = "localhost"

// for running at android device (client and server must connect same WIFI and it must be set to IPv4 of the WIFI)
//const val serverIPv4 = "192.168.13.109"

// default port of Tomcat server
const val serverPort = "8080"

// server name
const val serverName = "HealthyHelperServer"

// server url
const val serverUrl = "${serverProtocol}://${serverIPv4}:${serverPort}/${serverName}"


private const val myTag = "tag_Common"

suspend fun sendHttpRequest(
    url: String,
    dataOut: String,
    method: String = "POST",
): String {
    val TAG = "tag_sendHttpRequest"

    Log.e(TAG,"sendHttpRequest function was called.")

    var dataIn = ""
    withContext(Dispatchers.IO) {
        Log.e(TAG,"In withContext(Dispatchers.IO)")
        (URL(url).openConnection() as? HttpURLConnection)?.run {
            Log.e(TAG, "openConnection(),URL(url):${URL(url)}")
            doInput = true
            doOutput = true
            setChunkedStreamingMode(0)
            useCaches = false
            requestMethod = method
            setRequestProperty("content-type", "application/json")
            setRequestProperty("charset", "utf-8")
            Log.e(TAG, "In withContext(Dispatchers.IO),dataOut: ${dataOut},url:${url}")
            outputStream.bufferedWriter().use { it.write(dataOut) }

            Log.e(TAG,"In withContext(Dispatchers.IO),responseCode:${responseCode}")
            if (responseCode == 200) {
                inputStream.bufferedReader().useLines { lines ->
                    dataIn = lines.fold("") { text, line -> "$text$line" }
                    Log.e(TAG, "In withContext(Dispatchers.IO),dataIn: $dataIn")
                }
            }
        }
    }
    Log.e(TAG,"sendHttpRequest function was finished called.")
    return dataIn
}

suspend fun httpPost(
    url: String,
    dataOut: String
): String {
    return sendHttpRequest(url,dataOut,"POST")
}