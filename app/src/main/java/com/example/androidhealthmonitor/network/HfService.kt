package com.example.androidhealthmonitor.network



import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


// Hugging Face accepts a raw JSON body; for text-generation many models expect {"inputs":"..."}
data class HfRequest(val inputs: String)
data class HfResponse(val generated_text: String?) // model-dependent; adapt per model


interface HfApi {
    @POST("/models/{model}")
    suspend fun infer(@Path("model") model: String, @Body body: HfRequest): Map<String, Any>
}


object HfClient {
    fun create(ctx: android.content.Context): HfApi {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(ctx, "huggingface"))
            .addInterceptor(logging)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-inference.huggingface.co/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        return retrofit.create(HfApi::class.java)
    }
}