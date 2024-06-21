package com.demo.googlecalendar.domain.usecase

import com.demo.googlecalendar.utils.InputUseCase
import com.demo.googlecalendar.utils.NoInputUseCase
import com.google.api.services.calendar.model.Event

interface GetEventsUseCase: InputUseCase<GetEventsUseCase.GetEventsUseCaseInput, Result<List<Event>>> {
    data class GetEventsUseCaseInput(
        val month: Int,
        val date: Int,
    )
}