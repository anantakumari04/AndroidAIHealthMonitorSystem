package com.example.androidhealthmonitor.network

import android.content.Context
import com.example.androidhealthmonitor.util.SecureStore
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit // ✅ Import TimeUnit

// --- Gemini API Request/Response ---
data class GeminiRequest(
    @SerializedName("contents") val contents: List<Content>
)

data class Content(
    @SerializedName("parts") val parts: List<Part>,
    @SerializedName("role") val role: String? = null
)

data class Part(
    @SerializedName("text") val text: String
)

data class GeminiResponse(
    @SerializedName("candidates") val candidates: List<Candidate>?
)

data class Candidate(
    @SerializedName("content") val content: Content?
)

// --- Retrofit Interface ---
interface AiService {

    @Headers("Content-Type: application/json")

    // Updated to Gemini 2.5 Flash

    @POST("v1/models/gemini-2.5-flash:generateContent")

    suspend fun analyze(@Body req: GeminiRequest): Response<GeminiResponse>

}

// --- Retrofit Builder ---
object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    fun create(context: Context): AiService {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        // ✅ Setup client with longer timeouts
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(60, TimeUnit.SECONDS) // 60-second connection timeout
            .readTimeout(60, TimeUnit.SECONDS)    // 60-second read timeout
            .writeTimeout(60, TimeUnit.SECONDS)   // 60-second write timeout
            .addInterceptor { chain ->
                val apiKey = SecureStore.getApiKey(context) ?: ""

                val original = chain.request()
                val originalHttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("key", apiKey)
                    .build()

                val requestBuilder = original.newBuilder().url(url)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // ✅ Use the new client with timeouts
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AiService::class.java)
    }
}