package com.example.androidhealthmonitor.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.example.androidhealthmonitor.R
import com.example.androidhealthmonitor.data.db.AppDatabase
import com.example.androidhealthmonitor.data.db.Patient
import kotlinx.coroutines.launch


class AddPatientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)


        val etName = findViewById<EditText>(R.id.etName)
        val etAge = findViewById<EditText>(R.id.etAge)
        val etTemp = findViewById<EditText>(R.id.etTemp)
        val etHR = findViewById<EditText>(R.id.etHR)
        val etSys = findViewById<EditText>(R.id.etSys)
        val etDia = findViewById<EditText>(R.id.etDia)
        val etWeight = findViewById<EditText>(R.id.etWeight)
        val etHeight = findViewById<EditText>(R.id.etHeight)
        val etNotes = findViewById<EditText>(R.id.etNotes)
        val btnSave = findViewById<Button>(R.id.btnSavePatient)


        val db = AppDatabase.getDatabase(this)


        btnSave.setOnClickListener {
            val p = Patient(
                name = etName.text.toString(),
                age = etAge.text.toString().toIntOrNull() ?: 0,
                gender = "Not specified",
                notes = etNotes.text.toString(),
                temperature = etTemp.text.toString().toDoubleOrNull() ?: 0.0,
                heartRate = etHR.text.toString().toIntOrNull() ?: 0,
                systolic = etSys.text.toString().toIntOrNull() ?: 0,
                diastolic = etDia.text.toString().toIntOrNull() ?: 0,
                weightKg = etWeight.text.toString().toDoubleOrNull() ?: 0.0,
                heightCm = etHeight.text.toString().toDoubleOrNull() ?: 0.0
            )


            lifecycleScope.launch {
                val newPatientId = db.patientDao().insert(p)
                runOnUiThread {
                    Toast.makeText(this@AddPatientActivity, "Saved", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AddPatientActivity, PatientDetailActivity::class.java)
                    intent.putExtra("pid", newPatientId)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
