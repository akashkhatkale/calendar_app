package com.demo.googlecalendar.data.models

import com.google.gson.annotations.SerializedName

data class AccessTokenDto(
    @SerializedName("access_token") val access_token: String? = null,
    @SerializedName("expires_in") val expires_in: Int? = null,
    @SerializedName("scope") val scope: String? = null,
    @SerializedName("token_type") val token_type: String? = null,
)