package com.demo.googlecalendar.presentation.screens

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.demo.googlecalendar.presentation.AddEvent
import com.demo.googlecalendar.presentation.AddEvent.Companion.DAY
import com.demo.googlecalendar.presentation.AddEvent.Companion.MONTH
import com.demo.googlecalendar.presentation.AddEvent.Companion.YEAR
import com.demo.googlecalendar.presentation.DetailsActivity
import com.demo.googlecalendar.presentation.DetailsActivity.Companion.EVENT
import com.demo.googlecalendar.presentation.LoginActivity
import com.demo.googlecalendar.presentation.auth.GoogleAuth
import com.demo.googlecalendar.presentation.common.Calendar
import com.demo.googlecalendar.presentation.common.ProfileIcon
import com.demo.googlecalendar.presentation.models.EventDetails
import com.demo.googlecalendar.presentation.sharedprefs.SharedPrefs
import com.demo.googlecalendar.presentation.utils.CalendarUtils.getTime
import com.demo.googlecalendar.presentation.utils.SharedPrefsConstants
import com.demo.googlecalendar.presentation.utils.UIState
import com.demo.googlecalendar.presentation.viewmodels.MainActivityViewModel
import com.google.api.services.calendar.model.Event
import com.google.gson.Gson
import kotlinx.coroutines.delay
import java.time.LocalDate

@Composable
fun HomeScreen(
    sharedPrefs: SharedPrefs,
    viewModel: MainActivityViewModel,
    modifier: Modifier = Modifier,
    activity: ComponentActivity,
    auth: GoogleAuth
) {
    val selectedEvents by viewModel.selectedEvents.collectAsState()
    val calendarInputList by viewModel.calendarInputList

    LaunchedEffect(key1 = Unit) {
        delay(1000)
        viewModel.getEvents(
            LocalDate.now().monthValue - 1,
            LocalDate.now().dayOfMonth
        )
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HeaderComponent(
                logoUrl = sharedPrefs.getString(SharedPrefsConstants.PROFILE_URL),
                onSignOut = {
                    auth.signOut()
                    sharedPrefs.clearPrefs()
                    activity.startActivity(
                        Intent(
                            activity,
                            LoginActivity::class.java
                        )
                    )
                    activity.finish()
                }
            )
            Calendar(
                calendarInput = calendarInputList,
                onDayClick = { month, day ->
                    viewModel.getEvents(month, day)
                },
                month = "June",
            )

            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "All Events",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .background(Color(0xFF028391), shape = RoundedCornerShape(100.dp))
                        .size(30.dp)
                        .clickable {
                            activity.startActivity(
                                Intent(
                                    activity,
                                    AddEvent::class.java
                                ).apply {
                                    putExtra(MONTH, viewModel.selectedMonth + 1)
                                    putExtra(DAY, viewModel.selectedDay)
                                    putExtra(YEAR, 2024)
                                }
                            )
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center),
                        tint = Color.White
                    )
                }
            }


            Spacer(modifier = Modifier.height(5.dp))

            when (selectedEvents) {
                is UIState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }

                is UIState.Success -> {
                    val items = viewModel.selectedEvents.value as? UIState.Success
                    if (items?.data.orEmpty().isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Text(
                                text = "No events found",
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(vertical = 20.dp)
                        ) {
                            items(items?.data?.size ?: 0) {
                                items?.data?.get(it)?.let {
                                    EventItem(
                                        it,
                                        onClick = { time ->
                                            activity.startActivity(
                                                Intent(activity, DetailsActivity::class.java).apply {
                                                    putExtra(EVENT, Gson().toJson(
                                                        EventDetails(
                                                            eventName = it.summary,
                                                            eventTime = time,
                                                            eventOrganiser = it.organizer.email,
                                                            attendees = it.attendees?.map {
                                                                it.email
                                                            }.orEmpty(),
                                                            description = it.description.orEmpty()
                                                        )
                                                    ))
                                                }
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                is UIState.Error -> {

                }
            }
        }
    }
}

@Composable
fun HeaderComponent(
    logoUrl: String,
    modifier: Modifier = Modifier,
    onSignOut: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = "Sign out",
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .clickable {
                    onSignOut()
                }
        )
        Spacer(modifier = Modifier.weight(1f))
        ProfileIcon(
            logoUrl = logoUrl,
        )
    }
}

@Composable
fun EventItem(
    event: Event,
    modifier: Modifier = Modifier,
    onClick: (time: String) -> Unit = {},
) {
    val time = getTime(event.start.dateTime ?: event.start.date, event.end.dateTime ?: event.end.date)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .background(color = Color(0xFF028391), shape = RoundedCornerShape(12.dp))
            .border(width = 1.dp, shape = RoundedCornerShape(12.dp), color = Color(0x10121212))
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .clickable {
                onClick(time)
            },
    ) {
        Column {
            Text(
                text = event.summary.orEmpty(),
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = time,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}