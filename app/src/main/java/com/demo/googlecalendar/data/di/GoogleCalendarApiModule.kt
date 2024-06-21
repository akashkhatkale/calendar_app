package com.demo.googlecalendar.data.di

import com.demo.googlecalendar.data.repository.GoogleCalendarApiImpl
import com.demo.googlecalendar.domain.repository.GoogleCalendarApi
import com.demo.googlecalendar.presentation.auth.GoogleAuth
import com.demo.googlecalendar.presentation.auth.GoogleAuthImpl
import com.demo.googlecalendar.presentation.sharedprefs.SharedPrefs
import com.demo.googlecalendar.presentation.sharedprefs.SharedPrefsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class GoogleCalendarApiModule {

    @Binds
    abstract fun bindGoogleCalendarAuth(
        impl: GoogleCalendarApiImpl
    ): GoogleCalendarApi
}