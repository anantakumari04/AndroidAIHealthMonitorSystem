package com.example.androidhealthmonitor.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.androidhealthmonitor.R

class BmiCalculatorActivity : AppCompatActivity() {

    private lateinit var etWeight: EditText
    private lateinit var etHeight: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView
    private lateinit var tvCategory: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_calculator)

        etWeight = findViewById(R.id.etWeight)
        etHeight = findViewById(R.id.etHeight)
        btnCalculate = findViewById(R.id.btnCalculateBmi)
        tvResult = findViewById(R.id.tvBmiResult)
        tvCategory = findViewById(R.id.tvBmiCategory)

        btnCalculate.setOnClickListener {
            calculateBmi()
        }
    }

    private fun calculateBmi() {
        val weightStr = etWeight.text.toString()
        val heightStr = etHeight.text.toString()

        if (weightStr.isNotEmpty() && heightStr.isNotEmpty()) {
            val weight = weightStr.toFloat()
            val height = heightStr.toFloat() / 100 // Convert cm to meters

            if (height > 0) {
                val bmi = weight / (height * height)
                displayBmiResult(bmi)
            }
        }
    }

    private fun displayBmiResult(bmi: Float) {
        tvResult.text = String.format("Your BMI: %.2f", bmi)

        val category = when {
            bmi < 18.5 -> "Underweight"
            bmi < 24.9 -> "Normal weight"
            bmi < 29.9 -> "Overweight"
            else -> "Obese"
        }

        tvCategory.text = "Category: $category"
    }
}
