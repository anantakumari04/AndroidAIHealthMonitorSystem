package com.example.androidhealthmonitor.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import com.example.androidhealthmonitor.util.SecureStore


class AuthInterceptor(private val ctx: Context, private val serviceKeyName: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val token = SecureStore.getApiKey(ctx)
        val newReq = if (!token.isNullOrEmpty()) {
            req.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else req
        return chain.proceed(newReq)
    }
}