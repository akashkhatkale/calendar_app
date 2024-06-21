package com.demo.googlecalendar.presentation.di

import com.demo.googlecalendar.presentation.auth.GoogleAuth
import com.demo.googlecalendar.presentation.auth.GoogleAuthImpl
import com.demo.googlecalendar.presentation.config.ClientConfig
import com.demo.googlecalendar.presentation.config.ClientConfigImpl
import com.demo.googlecalendar.presentation.sharedprefs.SharedPrefs
import com.demo.googlecalendar.presentation.sharedprefs.SharedPrefsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class InterfaceModule {

    @Binds
    abstract fun bindGoogleAuth(
        impl: GoogleAuthImpl
    ): GoogleAuth

    @Binds
    abstract fun bindSharedPrefs(
        impl: SharedPrefsImpl
    ): SharedPrefs

    @Binds
    abstract fun bindClientConfig(
        impl: ClientConfigImpl
    ): ClientConfig
}