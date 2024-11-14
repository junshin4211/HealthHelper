package com.example.healthhelper.person.personVM

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

class AlarmViewModel : ViewModel() {
    private val _alarms = MutableStateFlow<List<Calendar>>(emptyList())
    val alarms: StateFlow<List<Calendar>> = _alarms.asStateFlow()

    private val _showPermissionDialog = MutableStateFlow(false)
    val showPermissionDialog: StateFlow<Boolean> = _showPermissionDialog.asStateFlow()

    fun addAlarm(context: Context, alarmTime: Calendar) {
        _alarms.value += alarmTime
        setAlarm(context, alarmTime)
    }

    fun removeAlarm(context: Context, alarm: Calendar) {
        _alarms.value = _alarms.value.filter { it != alarm }
        cancelAlarm(context, alarm)
    }

    fun dismissPermissionDialog() {
        _showPermissionDialog.value = false
    }

    private fun cancelAlarm(context: Context, alarmTime: Calendar) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmTime.timeInMillis.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "提醒取消", Toast.LENGTH_SHORT).show()
    }

    fun setAlarm(context: Context, alarmTime: Calendar) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                _showPermissionDialog.value = true
                return
            }
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("提醒時間", alarmTime.timeInMillis)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmTime.timeInMillis.toInt(),
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            if (alarmTime.timeInMillis <= System.currentTimeMillis()) {
                alarmTime.add(Calendar.DAY_OF_YEAR, 1)
            }

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmTime.timeInMillis,
                pendingIntent
            )

            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(alarmTime.timeInMillis, pendingIntent),
                pendingIntent
            )

        } catch (e: SecurityException) {
            _showPermissionDialog.value = true
        }
    }
}