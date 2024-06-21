package com.demo.googlecalendar.domain.repository

import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event

interface GoogleCalendarApi {
    suspend fun getCalendars(
        date: Int,
        month: Int
    ): Result<List<Event>>

    suspend fun addEvent(
        summary: String,
        description: String,
        startTime: DateTime,
        endTime: DateTime,
    ): Result<Boolean>
}