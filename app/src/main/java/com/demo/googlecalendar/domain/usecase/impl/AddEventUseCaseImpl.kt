package com.demo.googlecalendar.domain.usecase.impl

import com.demo.googlecalendar.domain.repository.GoogleCalendarApi
import com.demo.googlecalendar.domain.usecase.AddEventUseCase
import com.demo.googlecalendar.domain.usecase.GetEventsUseCase
import com.google.api.services.calendar.model.Event
import javax.inject.Inject

class AddEventUseCaseImpl @Inject constructor(
    private val googleCalendarApi: GoogleCalendarApi
): AddEventUseCase {
    override suspend fun invoke(input: AddEventUseCase.AddEventUseCaseInput): Result<Boolean> {
        return googleCalendarApi.addEvent(
            summary = input.summary,
            description = input.description,
            startTime = input.startTime,
            endTime = input.endTime
        )
    }
}