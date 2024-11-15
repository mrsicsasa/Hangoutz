package com.example.hangoutz.data.remote

import com.example.hangoutz.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RetrofitInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .header("apikey", BuildConfig.API_KEY)
            .header("Accept", "application/json")
            .method(original.method(), original.body())
            .build()
        return chain.proceed(request)
    }
}