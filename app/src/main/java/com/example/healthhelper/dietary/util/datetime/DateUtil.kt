package com.example.healthhelper.dietary.util.datetime

import android.annotation.SuppressLint
import com.example.healthhelper.dietary.util.dateformatter.DateFormatterPattern
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * DateUtil
 *
 * util object for date
 */
object DateUtil {
    fun isValidDate(dateStr:String):Boolean{
        try {
            val formatter = SimpleDateFormat(DateFormatterPattern.pattern, Locale.getDefault())
            val date = formatter.parse(dateStr)
            return true
        }catch (ex:Exception){
            return false
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat(DateFormatterPattern.pattern)
        return formatter.format(Date(millis))
    }
}