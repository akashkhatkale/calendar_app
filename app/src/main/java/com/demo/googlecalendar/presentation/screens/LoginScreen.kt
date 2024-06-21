package com.demo.googlecalendar.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.googlecalendar.presentation.utils.noInteractionClickable

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Google Calendar App",
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF121212)
            ),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(10.dp)
        )

        Text(
            text = "Sign In",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(30.dp)
                .background(color = Color(0xFF028391), shape = RoundedCornerShape(12.dp))
                .padding(10.dp)
                .noInteractionClickable{
                    onSignInClick()
                },
            textAlign = TextAlign.Center
        )
    }
}