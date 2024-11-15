package com.example.hangoutz.data.remote

import com.example.hangoutz.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val retrofitInterceptor = RetrofitInterceptor()
    val httpClient = OkHttpClient.Builder().addInterceptor(retrofitInterceptor).build()
    val api: UserAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(UserAPI::class.java)
    }
}