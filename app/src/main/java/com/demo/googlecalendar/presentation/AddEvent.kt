package com.demo.googlecalendar.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.demo.googlecalendar.R
import com.demo.googlecalendar.presentation.screens.AddEventScreen
import com.demo.googlecalendar.presentation.viewmodels.AddEventViewModel
import com.demo.googlecalendar.presentation.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEvent : ComponentActivity() {
    private val viewModel by viewModels<AddEventViewModel>()

    companion object {
        const val MONTH = "MONTH"
        const val DAY = "DAY"
        const val YEAR = "YEAR"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddEventScreen(
                viewModel,
                month = intent.getIntExtra(MONTH, 0),
                date = intent.getIntExtra(DAY, 0),
                year = 2024,
                activity = this
            )
        }
    }
}