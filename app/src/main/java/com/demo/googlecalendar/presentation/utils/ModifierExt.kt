package com.demo.googlecalendar.presentation.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.noInteractionClickable(enabled: Boolean = true, onClick: () -> Unit): Modifier =
    composed {
        clickable(
            indication = null,
            enabled = enabled,
            interactionSource = remember { MutableInteractionSource() }
        ) { onClick() }
    }