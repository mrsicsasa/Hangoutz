package com.example.hangoutz.data
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://zsjxwfjutstrybvltjov.supabase.co/"
    val retrofitInterceptor = RetrofitInterceptor()
    val httpClient =  OkHttpClient.Builder().addInterceptor(retrofitInterceptor).build()
    val api: UserAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(UserAPI::class.java)
    }
}