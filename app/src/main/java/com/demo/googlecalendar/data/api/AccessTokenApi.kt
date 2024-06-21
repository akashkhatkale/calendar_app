package com.demo.googlecalendar.data.api

import com.demo.googlecalendar.data.models.AccessTokenDto
import com.google.gson.annotations.SerializedName
import okhttp3.internal.concurrent.Task
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccessTokenApi {

    @FormUrlEncoded
    @POST("https://oauth2.googleapis.com/token")
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String,
        @Field("client_secret") clientSecret: String,
    ): Response<AccessTokenDto?>
}

