package com.example.androidhealthmonitor.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.androidhealthmonitor.R
import com.google.android.material.progressindicator.CircularProgressIndicator

class CalorieTrackerActivity : AppCompatActivity() {
    private var goal = 2000 // default
    private var consumed = 0

    private lateinit var progressBar: CircularProgressIndicator
    private lateinit var tvConsumed: TextView
    private lateinit var tvRemaining: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calorie_tracker)

        progressBar = findViewById(R.id.progressBar)
        tvConsumed = findViewById(R.id.tvConsumed)
        tvRemaining = findViewById(R.id.tvRemaining)
        val etGoal = findViewById<EditText>(R.id.etGoal)
        val etAdd = findViewById<EditText>(R.id.etAdd)
        val btnSet = findViewById<Button>(R.id.btnSetGoal)
        val btnAdd = findViewById<Button>(R.id.btnAddFood)

        btnSet.setOnClickListener {
            goal = etGoal.text.toString().toIntOrNull() ?: goal
            Toast.makeText(this, "Goal set: $goal kcal", Toast.LENGTH_SHORT).show()
            updateUI()
        }

        btnAdd.setOnClickListener {
            val add = etAdd.text.toString().toIntOrNull() ?: 0
            consumed += add
            updateUI()
            etAdd.text.clear()
        }

        updateUI()
    }

    private fun updateUI() {
        val remaining = goal - consumed
        val progress = if (goal > 0) (consumed * 100 / goal) else 0

        tvConsumed.text = "Consumed: $consumed kcal"
        tvRemaining.text = "Remaining: $remaining kcal"
        progressBar.progress = progress

        if (consumed >= goal) {
            tvConsumed.text = "Goal Achieved!"
            tvRemaining.text = "Congratulations!"
            progressBar.progress = 100
        }
    }
}
