package com.example.androidhealthmonitor.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidhealthmonitor.R
import com.example.androidhealthmonitor.util.WaterReminderReceiver

class WaterReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_reminder)

        val etMinutes: EditText = findViewById(R.id.etMinutes)
        val btnSetReminder: Button = findViewById(R.id.btnSetReminder)

        // ✅ Request runtime notification permission (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }

        btnSetReminder.setOnClickListener {
            val minutes = etMinutes.text.toString().toLongOrNull()
            if (minutes != null && minutes > 0) {
                setReminder(minutes)
                Toast.makeText(this, "Reminder set for $minutes minute(s)", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a valid number of minutes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setReminder(minutes: Long) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, WaterReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerAtMillis = System.currentTimeMillis() + (minutes * 60 * 1000)

        // ✅ Check exact alarm permission on Android 12+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                val settingsIntent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(settingsIntent)
                Toast.makeText(this, "Please allow exact alarms and try again", Toast.LENGTH_LONG).show()
                return
            }
        }

        // ✅ Schedule alarm
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }
}
