package com.example.healthhelper.healthyMap.source

import android.content.Context
import androidx.annotation.RawRes
import com.example.healthhelper.healthyMap.model.City
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class CityDistrictsLoader(@RawRes private val fileName: Int) {
    private val gson = Gson()

    fun getCityDistrictsData(context: Context): List<City> {
        val inputStream = context.resources.openRawResource(fileName)
        return gson.fromJson(InputStreamReader(inputStream, Charsets.UTF_8), object : TypeToken<List<City>>() {}.type)
    }
}