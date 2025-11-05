package com.example.androidhealthmonitor.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


// DTOs (keep minimal; adapt if you need streaming etc.)
data class ChatMessage(val role: String, val content: String)


data class ChatRequest(val model: String, val messages: List<ChatMessage>, val max_tokens: Int = 300)


data class ChatChoice(val index: Int, val message: ChatMessage)


data class ChatResponse(val id: String?, val choices: List<ChatChoice>?)


interface OpenAiApi {
    @POST("v1/chat/completions")
    suspend fun createChat(@Body body: ChatRequest): ChatResponse
}


object OpenAiClient {
    fun create(ctx: android.content.Context): OpenAiApi {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(ctx, "openai"))
            .addInterceptor(logging)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        return retrofit.create(OpenAiApi::class.java)
    }
}