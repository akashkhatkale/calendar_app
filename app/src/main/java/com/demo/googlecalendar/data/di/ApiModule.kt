package com.demo.googlecalendar.data.di

import com.demo.googlecalendar.data.api.AccessTokenApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS).build())
        .baseUrl("https://oauth2.googleapis.com")
        .build()

    @Provides
    @Singleton
    fun provideAccessTokenApi(retrofit: Retrofit): AccessTokenApi = retrofit.create(AccessTokenApi::class.java)
}