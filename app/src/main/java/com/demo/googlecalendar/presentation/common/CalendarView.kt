package com.demo.googlecalendar.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.googlecalendar.presentation.utils.CalendarInput
import com.demo.googlecalendar.presentation.utils.MonthData
import com.demo.googlecalendar.presentation.utils.noInteractionClickable
import java.time.LocalDateTime
import java.util.Calendar

@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    calendarInput: List<MonthData>,
    onDayClick: (month: Int, day: Int)->Unit,
    strokeWidth: Float = 15f,
    month:String
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    var currentMonth by remember {
        mutableStateOf(LocalDateTime.now().monthValue - 1)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                       if (currentMonth > 0) {
                           currentMonth -= 1
                       }
                    }
            )
            Text(
                text = calendarInput[currentMonth].title,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp),
                textAlign = TextAlign.Center
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        if (currentMonth < 11) {
                            currentMonth += 1
                        }
                    }
            )
        }

        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            calendarInput[currentMonth].days.forEachIndexed { _, calendarInputs ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    calendarInputs.forEachIndexed { _, day ->
                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .width(screenWidth / 7)
                                .background(
                                    color = if (day.isSelected) Color(0xFF028391) else Color.Transparent,
                                    shape = RoundedCornerShape(100.dp)
                                )
                                .noInteractionClickable {
                                    onDayClick(currentMonth, day.day)
                                }
                        ) {
                            Text(
                                text = day.day.toString(),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(8.dp),
                                style = TextStyle(
                                    color = if (day.isSelected) Color.White else Color(0xFF121212)
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}