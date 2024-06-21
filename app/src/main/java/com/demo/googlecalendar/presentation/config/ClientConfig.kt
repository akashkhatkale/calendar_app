package com.demo.googlecalendar.presentation.config

interface ClientConfig {
    fun getClientId(): String
    fun getClientSecret(): String
}