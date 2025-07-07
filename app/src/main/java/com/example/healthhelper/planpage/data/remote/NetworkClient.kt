package com.example.healthhelper.planpage.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object NetworkClient {

    // for running at android emulator
    //const val serverIPv4 = "10.0.2.2"

    // for running at server (localhost)
    //const val serverIPv4 = "localhost"

    // for running at android device (client and server must connect same WIFI and it must be set to IPv4 of the WIFI)
    const val serverIPv4 = "192.168.1.180"

    // server name
    const val serverName = "HealthyHelperServer"

    private const val BASE_URL = "http://$serverIPv4:8080/$serverName/"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val okHttpClient: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // 開發時建議
        }
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    // 提供 PlanApiService 的實例
    val planApiService: PlanApiService by lazy {
        retrofit.create(PlanApiService::class.java)
    }
}