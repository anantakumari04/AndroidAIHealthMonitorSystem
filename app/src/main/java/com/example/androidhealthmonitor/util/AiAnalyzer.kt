package com.example.androidhealthmonitor.util

import android.content.Context
import com.example.androidhealthmonitor.data.db.Patient
import com.example.androidhealthmonitor.network.Content
import com.example.androidhealthmonitor.network.GeminiRequest
import com.example.androidhealthmonitor.network.Part
import com.example.androidhealthmonitor.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

object AiAnalyzer {
    suspend fun analyze(context: Context, patient: Patient): String = withContext(Dispatchers.IO) {
        // ✅ Pass context to create()
        val service = RetrofitClient.create(context)

        val bmi = String.format("%.2f", patient.bmi())
        val summary = "Patient ${patient.name}, age ${patient.age}, BMI $bmi, " +
                "Temp ${patient.temperature}°C, HR ${patient.heartRate} bpm, BP ${patient.systolic}/${patient.diastolic} mmHg. " +
                "Notes/Symptoms: ${patient.notes}"

        val prompt = "You are an AI health assistant. Analyze the health of this patient based on the following data. " +
                "Provide a brief summary of potential issues, list clear precautions, and state whether a hospital visit is recommended (e.g., 'No immediate visit required', 'Consult a doctor soon', or 'Visit hospital immediately'). " +
                "Format the response clearly for a mobile app.\n\n" +
                "Patient Data:\n$summary"

        val request = GeminiRequest(
            contents = listOf(
                Content(
                    parts = listOf(Part(text = prompt)),
                    role = "user"
                )
            )
        )

        try {
            // ✅ Call the suspend fun directly, no .execute()
            val response = service.analyze(request)

            if (response.isSuccessful) {
                val responseBody = response.body()
                val text = responseBody?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                text ?: "No response from AI. Check API key and quota."
            } else {
                // Handle API error
                "API Error: ${response.code()} ${response.message()}\n${response.errorBody()?.string()}"
            }
        } catch (e: IOException) {
            "Network error: ${e.message}. Please check internet connection."
        } catch (e: Exception) {
            "An unexpected error occurred: ${e.message}"
        }
    }
}