package com.demo.googlecalendar.domain.usecase

import com.demo.googlecalendar.utils.InputUseCase
import com.demo.googlecalendar.utils.NoInputUseCase
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event

interface AddEventUseCase: InputUseCase<AddEventUseCase.AddEventUseCaseInput, Result<Boolean>> {
    data class AddEventUseCaseInput(
        val summary: String,
        val description: String,
        val startTime: DateTime,
        val endTime: DateTime,
    )
}