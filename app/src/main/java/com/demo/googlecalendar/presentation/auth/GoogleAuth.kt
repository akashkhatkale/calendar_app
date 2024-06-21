package com.demo.googlecalendar.presentation.auth

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

interface GoogleAuth {

    companion object {
        const val USER_CANCEL = 12501
        const val FAILED = 12500
    }

    fun init(context: Context)

    fun signIn(launcher: ActivityResultLauncher<Intent>)

    fun signOut()

    fun isLoggedIn(): Boolean

}