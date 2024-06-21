package com.demo.googlecalendar.domain.usecase.impl

import com.demo.googlecalendar.domain.repository.GoogleCalendarApi
import com.demo.googlecalendar.domain.usecase.GetEventsUseCase
import com.google.api.services.calendar.model.Event
import javax.inject.Inject

class GetEventsUseCaseImpl @Inject constructor(
    private val googleCalendarApi: GoogleCalendarApi
): GetEventsUseCase {

    override suspend fun invoke(input: GetEventsUseCase.GetEventsUseCaseInput): Result<List<Event>> {
        return googleCalendarApi.getCalendars(
            input.date, input.month
        )
    }
}