package com.example.androidhealthmonitor.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidhealthmonitor.R
import com.example.androidhealthmonitor.util.SleepAlarmReceiver
import java.util.Calendar

class SleepTimeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep_time)

        val timePicker: TimePicker = findViewById(R.id.timePicker)
        val btnSetAlarm: Button = findViewById(R.id.btnSetAlarm)

        btnSetAlarm.setOnClickListener {
            val calendar = Calendar.getInstance()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
                calendar.set(Calendar.MINUTE, timePicker.minute)
            } else {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.currentHour)
                calendar.set(Calendar.MINUTE, timePicker.currentMinute)
            }
            calendar.set(Calendar.SECOND, 0)

            setAlarm(calendar.timeInMillis)
        }
    }

    private fun setAlarm(timeInMillis: Long) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, SleepAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
                Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission to schedule exact alarms not granted", Toast.LENGTH_SHORT).show()
            }
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
            Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show()
        }
    }
}
