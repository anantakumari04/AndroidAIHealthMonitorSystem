package com.example.androidhealthmonitor.data.ai
import android.content.Context
import com.example.androidhealthmonitor.network.OpenAiClient
import com.example.androidhealthmonitor.network.HfClient


class AiRepository(private val ctx: Context) {
    private val openai = OpenAiClient.create(ctx)
    private val hf = HfClient.create(ctx)


    suspend fun analyzeWithOpenAi(prompt: String): String {
        val req = com.example.androidhealthmonitor.network.ChatRequest(
            model = "gpt-4o-mini", // pick model per your access
            messages = listOf(com.example.androidhealthmonitor.network.ChatMessage("user", prompt)),
            max_tokens = 400
        )
        val res = openai.createChat(req)
        return res.choices?.firstOrNull()?.message?.content ?: ""
    }


    suspend fun analyzeWithHf(model: String, prompt: String): String {
        try {
            val raw = hf.infer(model, com.example.androidhealthmonitor.network.HfRequest(prompt))
// Try to be flexible in reading response
            val generated = when {
                raw.containsKey("generated_text") -> raw["generated_text"].toString()
                raw.containsKey("0") -> raw["0"].toString()
                else -> raw.values.firstOrNull()?.toString() ?: ""
            }
            return generated
        } catch (e: Exception) {
            return "HF error: ${e.message}"
        }
    }
}