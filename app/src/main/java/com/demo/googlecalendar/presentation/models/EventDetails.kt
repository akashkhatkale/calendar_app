package com.demo.googlecalendar.presentation.models

data class EventDetails(
    val eventName: String,
    val eventTime: String,
    val eventOrganiser: String,
    val attendees: List<String>,
    val description: String,
)
