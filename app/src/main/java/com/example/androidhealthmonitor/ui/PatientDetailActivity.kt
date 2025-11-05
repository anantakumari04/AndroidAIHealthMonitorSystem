package com.example.androidhealthmonitor.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.androidhealthmonitor.R
import com.example.androidhealthmonitor.data.db.AppDatabase
import com.example.androidhealthmonitor.util.AiAnalyzer
import kotlinx.coroutines.launch

class PatientDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_detail)

        val pid = intent.getLongExtra("pid", -1L)
        val tvDetails = findViewById<TextView>(R.id.tvDetails)
        val tvAnalysis = findViewById<TextView>(R.id.tvAnalysis)

        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val p = db.patientDao().findById(pid)
            p?.let {
                val details = buildString {
                    append("Name: ${it.name}\n")
                    append("Age: ${it.age}\n")
                    append("BMI: ${String.format("%.2f", it.bmi())}\n")
                    append("Notes: ${it.notes}\n")
                    append("Temp: ${it.temperature}°C\n")
                    append("Heart Rate: ${it.heartRate}\n")
                    append("BP: ${it.systolic}/${it.diastolic}\n")
                }

                runOnUiThread { tvDetails.text = details }

                val analysis = AiAnalyzer.analyze(this@PatientDetailActivity, it)
                runOnUiThread { tvAnalysis.text = analysis }
            }
        }
    }
}
