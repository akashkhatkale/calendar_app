package com.demo.googlecalendar.presentation.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailsScreen(
    eventName: String,
    eventDescription: String,
    eventTime: String,
    eventOrganiser: String,
    attendees: List<String>,
    activity: ComponentActivity,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
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
        Text(
            text = eventDescription,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            fontSize = 30.sp,
            modifier = Modifier
        )
        Text(
            text = eventName,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            fontSize = 20.sp,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = eventTime,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .background(Color(0xFF028391), shape = RoundedCornerShape(6.dp))
                .padding(horizontal = 10.dp, vertical = 2.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Organiser : ${eventOrganiser}",
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            fontSize = 14.sp,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(50.dp))
        if (attendees.isNotEmpty()) {
            Text(
                text = "Attendees",
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier
            )
            LazyColumn {
                items(attendees) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                    ) {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                            fontSize = 14.sp,
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Divider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.2f))
                    }
                }
            }
        }
    }
}