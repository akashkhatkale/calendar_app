package com.demo.googlecalendar.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.demo.googlecalendar.R
import com.demo.googlecalendar.presentation.models.EventDetails
import com.demo.googlecalendar.presentation.screens.DetailsScreen
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : ComponentActivity() {
    companion object {
        const val EVENT = "EVENT"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val event = Gson().fromJson(intent.getStringExtra(EVENT), EventDetails::class.java)
        setContent {
            DetailsScreen(
                eventName = event.eventName,
                eventTime = event.eventTime,
                eventOrganiser = event.eventOrganiser,
                attendees = event.attendees,
                eventDescription = event.description,
                activity = this
            )
        }
    }
}