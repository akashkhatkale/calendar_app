package com.demo.googlecalendar.presentation.utils

import com.google.api.client.util.DateTime
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

object CalendarUtils {

    fun createCalendarList(d: Int? = null, m: Int? = null): List<MonthData> {
        val months = listOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        val monthsName = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
        var currentDate = LocalDate.now()
        val day = d ?: currentDate.dayOfMonth;
        val month = m ?: (currentDate.monthValue - 1);

        val finalMonths = mutableListOf<MonthData>()

        for (y in 0 .. 11) {
            val days = months[y]
            var currentDay = 0
            val calendarInputs = mutableListOf<MutableList<CalendarInput>>()
            for (i in 0..days / 7) {
                val currentWeek = mutableListOf<CalendarInput>()
                for (j in 1..7) {
                    currentDay += 1
                    if (currentDay <= days) {
                        currentWeek.add(
                            CalendarInput(
                                currentDay,
                                toDos = listOf(),
                                isSelected = (currentDay == day) && (y == month)
                            )
                        )
                    }
                }
                calendarInputs.add(currentWeek)
            }
            finalMonths.add(
                MonthData(
                    days = calendarInputs,
                    title = monthsName[y]
                )
            )
        }

        return finalMonths.toList()
    }

    fun getTime(startTime: DateTime?, endTime: DateTime?): String {
        val start = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(startTime?.value ?: 0),
            ZoneId.systemDefault()
        )
        val end = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(endTime?.value ?: 0),
            ZoneId.systemDefault()
        )
        return "${start.hour}:${getMinute(start.minute)}  -  ${end.hour}:${getMinute(end.minute)}"
    }



    private fun getMinute(minute: Int): String {
        return if (minute < 10) {
            "0${minute}"
        } else "${minute}"
    }
}

data class CalendarInput(
    val day: Int,
    var isSelected: Boolean,
    val toDos:List<String> = emptyList()
)

data class MonthData(
    val days: MutableList<MutableList<CalendarInput>>,
    val title: String,
)