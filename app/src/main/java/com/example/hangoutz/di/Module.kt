package com.example.hangoutz.di

import com.example.hangoutz.BuildConfig
import com.example.hangoutz.data.remote.EventAPI
import com.example.hangoutz.data.remote.InviteAPI
import com.example.hangoutz.data.remote.RetrofitInterceptor
import com.example.hangoutz.data.remote.UserAPI
import com.example.hangoutz.data.repository.EventRepositoryImpl
import com.example.hangoutz.data.repository.InviteRepositoryImpl
import com.example.hangoutz.data.repository.UserRepositoryImpl
import com.example.hangoutz.domain.repository.EventRepository
import com.example.hangoutz.domain.repository.InviteRepository
import com.example.hangoutz.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val retrofitInterceptor = RetrofitInterceptor()
        val httpClient = OkHttpClient.Builder().addInterceptor(retrofitInterceptor).build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Provides
    fun providesUserAPI(retrofit: Retrofit): UserAPI {
        return retrofit.create(UserAPI::class.java)
    }

    @Provides
    fun providesEventAPI(retrofit: Retrofit): EventAPI {
        return retrofit.create(EventAPI::class.java)
    }

    @Provides
    fun providesInviteApi(retrofit: Retrofit): InviteAPI{
        return retrofit.create(InviteAPI::class.java)
    }

    @Provides
    fun providesUserRepository(userAPI: UserAPI): UserRepository {
        return UserRepositoryImpl(userAPI)
    }
    @Provides
    fun provideEventRepository(eventAPI: EventAPI): EventRepository{
        return EventRepositoryImpl(eventAPI)
    }

    @Provides
    fun provideInviteRepository(invitesAPI: InviteAPI): InviteRepository {
        return InviteRepositoryImpl(invitesAPI)
    }
}