package com.example.androidhealthmonitor.ui


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidhealthmonitor.R
import com.example.androidhealthmonitor.adapter.PatientAdapter
import com.example.androidhealthmonitor.data.db.AppDatabase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class PatientListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_list)


        val rv = findViewById<RecyclerView>(R.id.rvPatients)
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = PatientAdapter { patientId ->
            val i = Intent(this, PatientDetailActivity::class.java)
            i.putExtra("pid", patientId)
            startActivity(i)
        }
        rv.adapter = adapter


        val db = AppDatabase.getDatabase(this)
        lifecycleScope.launch {
            db.patientDao().getAll().collect { list ->
                adapter.submitList(list)
            }
        }
    }
}