package com.demo.googlecalendar.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ProfileIcon(
    logoUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(50.dp)
    ) {
        AsyncImage(
            model = logoUrl,
            contentDescription = null
        )
    }
}