package com.example.androidhealthmonitor.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.androidhealthmonitor.R
import com.example.androidhealthmonitor.network.Content
import com.example.androidhealthmonitor.network.GeminiRequest
import com.example.androidhealthmonitor.network.Part
import com.example.androidhealthmonitor.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnalysisActivity : AppCompatActivity() {

    private lateinit var inputContainer: LinearLayout
    private lateinit var btnAnalyze: Button
    private lateinit var tvAnalysisResult: TextView
    private lateinit var analysisType: String
    private val inputFields = mutableListOf<EditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analysis)

        inputContainer = findViewById(R.id.inputContainer)
        btnAnalyze = findViewById(R.id.btnAnalyze)
        tvAnalysisResult = findViewById(R.id.tvAnalysisResult)

        analysisType = intent.getStringExtra("ANALYSIS_TYPE") ?: ""
        setupInputFields()

        btnAnalyze.setOnClickListener {
            performAnalysis()
        }
    }

    private fun setupInputFields() {
        val fields = when (analysisType) {
            "BLOOD_SUGAR" -> listOf("Blood Sugar (mg/dL)")
            "WEIGHT_BMI" -> listOf("Weight (kg)", "Height (cm)")
            "WATER_REMINDER" -> listOf("Water Intake (ml)")
            "CHECK_SYMPTOMS" -> listOf("Symptoms (e.g., cold, fever)", "Heart Rate (bpm)")
            "HEALTH_DIET" -> listOf("Today's Meals (e.g., breakfast, lunch, dinner)")
            "SLEEP_TIME" -> listOf("Sleep Duration (hours)")
            else -> emptyList()
        }

        for (hint in fields) {
            val editText = EditText(this)
            editText.hint = hint
            inputContainer.addView(editText)
            inputFields.add(editText)
        }
    }

    private fun performAnalysis() {
        val inputText = inputFields.joinToString(separator = ", ") { "${it.hint}: ${it.text}" }
        val prompt = "Analyze the following health data: $inputText"

        // ✅ Updated lifecycleScope launch
        lifecycleScope.launch {
            tvAnalysisResult.text = "Analyzing..."
            tvAnalysisResult.visibility = View.VISIBLE

            try {
                // ✅ Pass context and run in IO dispatcher
                val resultText = withContext(Dispatchers.IO) {
                    val service = RetrofitClient.create(applicationContext)
                    val request = GeminiRequest(contents = listOf(Content(parts = listOf(Part(text = prompt)), role = "user")))

                    // ✅ Call suspend fun
                    val response = service.analyze(request)

                    if (response.isSuccessful) {
                        response.body()?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "No analysis available."
                    } else {
                        "Error: ${response.code()} ${response.message()}"
                    }
                }
                tvAnalysisResult.text = resultText

            } catch (e: Exception) {
                tvAnalysisResult.text = "Error: ${e.message}"
            }
        }
    }
}