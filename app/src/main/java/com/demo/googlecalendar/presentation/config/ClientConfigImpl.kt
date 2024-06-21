package com.demo.googlecalendar.presentation.config

import com.demo.googlecalendar.BuildConfig
import javax.inject.Inject

class ClientConfigImpl @Inject constructor(): ClientConfig {
    override fun getClientId(): String {
        return BuildConfig.CLIENT_ID
    }

    override fun getClientSecret(): String {
        return BuildConfig.CLIENT_SECRET
    }
}