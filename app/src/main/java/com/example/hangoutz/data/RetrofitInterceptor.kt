package com.example.hangoutz.data

import okhttp3.Interceptor
import okhttp3.Response

class RetrofitInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .header("apikey","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inpzanh3Zmp1dHN0cnlidmx0am92Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzA4MDgxNDEsImV4cCI6MjA0NjM4NDE0MX0.rKBWepKRAM7IbK-jzE7JZ46V1CYZenowgF-jSZEQ29w")
            .header("Accept", "application/json")
            .method(original.method(), original.body())
            .build()
        return chain.proceed(request)
    }
}