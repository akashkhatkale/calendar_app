package com.demo.googlecalendar.domain.di

import com.demo.googlecalendar.domain.usecase.AddEventUseCase
import com.demo.googlecalendar.domain.usecase.GetEventsUseCase
import com.demo.googlecalendar.domain.usecase.impl.AddEventUseCaseImpl
import com.demo.googlecalendar.domain.usecase.impl.GetEventsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetGoogleCalendarUseCase(
        impl: GetEventsUseCaseImpl
    ): GetEventsUseCase

    @Binds
    abstract fun bindAddEventUseCase(
        impl: AddEventUseCaseImpl
    ): AddEventUseCase
}