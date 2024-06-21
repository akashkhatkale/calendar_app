package com.demo.googlecalendar.presentation.screens

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastCbrt
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.demo.googlecalendar.R
import com.demo.googlecalendar.presentation.utils.UIState
import com.demo.googlecalendar.presentation.viewmodels.AddEventViewModel
import com.google.api.client.util.DateTime
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    viewModel: AddEventViewModel,
    month: Int,
    year: Int,
    date: Int,
    activity: ComponentActivity,
    modifier: Modifier = Modifier
) {
    var name by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var startTIme by remember {
        mutableStateOf("")
    }
    var endTime by remember {
        mutableStateOf("")
    }
    var showTimePicker2 by remember {
        mutableStateOf(false)
    }
    var showTimePicker by remember {
        mutableStateOf(false)
    }
    val timePickerState = rememberTimePickerState()
    val timePickerState2 = rememberTimePickerState()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(state.value) {
        if (state.value is UIState.Success) {
            activity.finish()
        } else if (state.value is UIState.Error) {
            Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }


    if (showTimePicker) {
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
            onDismissRequest = {

            }
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8)),
            ) {
                TimePicker(
                    state = timePickerState,
                )

                //row with ok, cancel button
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    Text(
                        text = "Cancel",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 10.dp)
                            .padding(top = 20.dp)
                            .clickable {
                                showTimePicker = false
                            }
                    )
                    Text(
                        text = "Ok",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 10.dp)
                            .padding(top = 20.dp)
                            .clickable {
                                startTIme = "${timePickerState.hour} : ${timePickerState.minute}"
                                showTimePicker = false
                            }
                    )
                }
            }
        }
    }

    if (showTimePicker2) {
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
            onDismissRequest = {

            }
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8)),
            ) {
                TimePicker(
                    state =timePickerState2 ,
                )

                //row with ok, cancel button
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    Text(
                        text = "Cancel",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 10.dp)
                            .padding(top = 20.dp)
                            .clickable {
                                showTimePicker2 = false
                            }
                    )
                    Text(
                        text = "Ok",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 10.dp)
                            .padding(top = 20.dp)
                            .clickable {
                                endTime = "${timePickerState2.hour} : ${timePickerState2.minute}"
                                showTimePicker2 = false
                            }
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    activity.finish()
                }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Add Event",
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            fontSize = 30.sp,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "Date: ${date} / ${if(month < 10) "0" else ""}${month} / 2024",
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(
                    text = "Add Event Name"
                )
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = description,
            onValueChange = {
                description = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(
                    text = "Add Event Description"
                )
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = startTIme,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showTimePicker = true
                },
            label = {
                Text(
                    text = "Add Start Time"
                )
            },
            enabled = false
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = endTime,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showTimePicker2 = true
                },
            label = {
                Text(
                    text = "Add End Time"
                )
            },
            enabled = false
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Done",
            style = TextStyle(
                color = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF028391), shape = RoundedCornerShape(10.dp))
                .padding(vertical = 10.dp)
                .clickable {
                    viewModel.addEvent(
                        summary = name,
                        description = description,
                        startTime = DateTime(
                            "${year}-${if (month < 10) "0" else ""}${month}-${if (date < 10) "0" else ""}${date}T${if (timePickerState.hour < 10) "0" else ""}${timePickerState.hour}:${if (timePickerState.minute < 10) "0" else ""}${timePickerState.minute}:00.695+05:30"
                        ),
                        endTime = DateTime(
                            "${year}-${if (month < 10) "0" else ""}${month}-${if (date < 10) "0" else ""}${date}T${if (timePickerState.hour < 10) "0" else ""}${timePickerState2.hour}:${if (timePickerState2.minute < 10) "0" else ""}${timePickerState2.minute}:00.695+05:30"
                        )
                    )
                },
            textAlign = TextAlign.Center
        )
    }
}