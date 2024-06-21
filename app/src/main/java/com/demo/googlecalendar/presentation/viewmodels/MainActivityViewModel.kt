package com.demo.googlecalendar.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.googlecalendar.domain.usecase.GetEventsUseCase
import com.demo.googlecalendar.presentation.utils.CalendarUtils
import com.demo.googlecalendar.presentation.utils.CalendarUtils.createCalendarList
import com.demo.googlecalendar.presentation.utils.UIState
import com.google.android.play.integrity.internal.al
import com.google.api.services.calendar.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
): ViewModel() {
    private var events: List<Event> = mutableListOf()

    private val _selectedEvents: MutableStateFlow<UIState<List<Event>>> = MutableStateFlow(UIState.Loading())
    val selectedEvents: StateFlow<UIState<List<Event>>> = _selectedEvents.asStateFlow()

    var calendarInputList = mutableStateOf(createCalendarList())
    var selectedMonth = 0
    var selectedDay = 0

    fun getEvents(
        month: Int,
        day: Int,
    ) = viewModelScope.launch(Dispatchers.IO) {
        selectedMonth = month
        selectedDay = day
        _selectedEvents.emit(UIState.Loading())
        val oldList = createCalendarList(day, month)
        calendarInputList.value = oldList
        getEventsUseCase.invoke(
            GetEventsUseCase.GetEventsUseCaseInput(
                month = month + 1,
                date = day
            )
        ).onSuccess { data ->
            _selectedEvents.emit(UIState.Success(data))
        }.onFailure {
            _selectedEvents.emit(UIState.Error(it))
        }
    }
}