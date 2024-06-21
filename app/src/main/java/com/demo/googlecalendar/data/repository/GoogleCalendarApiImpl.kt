package com.demo.googlecalendar.data.repository

import android.util.Log
import com.demo.googlecalendar.data.api.AccessTokenApi
import com.demo.googlecalendar.domain.repository.GoogleCalendarApi
import com.demo.googlecalendar.presentation.config.ClientConfig
import com.demo.googlecalendar.presentation.sharedprefs.SharedPrefs
import com.demo.googlecalendar.presentation.utils.SharedPrefsConstants.ACCESS_TOKEN
import com.demo.googlecalendar.presentation.utils.SharedPrefsConstants.AUTH_CODE
import com.demo.googlecalendar.presentation.utils.SharedPrefsConstants.REFRESH_TOKEN
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.EventDateTime
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GoogleCalendarApiImpl @Inject constructor(
    private val sharedPrefs: SharedPrefs,
    private val clientConfig: ClientConfig,
    private val api: AccessTokenApi
): GoogleCalendarApi {
    private val HTTP_TRANSPORT = com.google.api.client.http.javanet.NetHttpTransport()
    private var service: Calendar? = null
    private val scope = CoroutineScope(Dispatchers.IO)


    init {
        scope.launch {
            service = Calendar.Builder(
                HTTP_TRANSPORT,
                GsonFactory.getDefaultInstance(),
                getCredentials(sharedPrefs.getString(REFRESH_TOKEN))
            )
                .setApplicationName("Google Calendar")
                .build()
        }
    }

    override suspend fun getCalendars(
        date: Int,
        month: Int
    ): Result<List<Event>> {
        try {
            val events = service?.events()?.list("primary")
                ?.setTimeMin(DateTime("2024-${if (month < 10) "0" else ""}${month}-${if (date < 10) "0" else ""}${date}T00:00:00.695+05:30"))
                ?.setTimeMax(DateTime("2024-${if (month < 10) "0" else ""}${month}-${if (date < 10) "0" else ""}${date+1}T00:00:00.695+05:30"))
                ?.setOrderBy("startTime")
                ?.setSingleEvents(true)
                ?.execute()
            return Result.success(events?.items.orEmpty())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun addEvent(
        summary: String,
        description: String,
        startTime: DateTime,
        endTime: DateTime,
    ): Result<Boolean> {
        try {
            val start = EventDateTime()
                .setDateTime(startTime)
            val end = EventDateTime()
                .setDateTime(endTime)
            val event = Event()
                .setSummary(summary)
                .setDescription(description)
                .setStart(start)
                .setEnd(end)

            service?.events()?.insert("primary", event)?.execute()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private suspend fun getCredentials(refreshToken: String): Credential {
        val a = api.getAccessToken(
            clientId = clientConfig.getClientId(),
            clientSecret = clientConfig.getClientSecret(),
            refreshToken = refreshToken,
            grantType = "refresh_token"
        )
        return GoogleCredential().setAccessToken(a.body()?.access_token?.orEmpty())
//        val tokenResponse = GoogleAuthorizationCodeTokenRequest(
//            HTTP_TRANSPORT, GsonFactory.getDefaultInstance(),
//            "https://www.googleapis.com/oauth2/v4/token",
//            clientConfig.getClientId(),
//            clientConfig.getClientSecret(),
//            authCode,
//            ""
//        ).execute()
//        Log.d("AKASH_LOG", "getCredentials: ${authCode}")
//        if (tokenResponse.refreshToken == null) {
//            return GoogleCredential().setAccessToken(tokenResponse.accessToken)
//        } else {
//            sharedPrefs.putString(REFRESH_TOKEN, tokenResponse.refreshToken)
//            return GoogleCredential().setAccessToken(tokenResponse.accessToken)
//        }
    }
}