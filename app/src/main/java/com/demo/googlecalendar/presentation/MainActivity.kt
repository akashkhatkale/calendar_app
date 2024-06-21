package com.demo.googlecalendar.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.demo.googlecalendar.presentation.auth.GoogleAuthImpl
import com.demo.googlecalendar.presentation.screens.DetailsScreen
import com.demo.googlecalendar.presentation.screens.HomeScreen
import com.demo.googlecalendar.presentation.sharedprefs.SharedPrefs
import com.demo.googlecalendar.presentation.theme.GoogleCalendarTheme
import com.demo.googlecalendar.presentation.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    internal lateinit var sharedPrefs: SharedPrefs

    @Inject
    internal lateinit var auth: GoogleAuthImpl

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GoogleCalendarTheme {
                HomeScreen(
                    sharedPrefs = sharedPrefs,
                    viewModel = viewModel,
                    activity = this@MainActivity,
                    auth = auth
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getEvents(viewModel.selectedMonth, viewModel.selectedDay)
    }
}
