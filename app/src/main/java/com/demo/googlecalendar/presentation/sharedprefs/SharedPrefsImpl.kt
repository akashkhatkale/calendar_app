package com.demo.googlecalendar.presentation.sharedprefs

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPrefsImpl @Inject constructor(
    @ApplicationContext private val context: Context,
): SharedPrefs {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("calendar_app", Activity.MODE_PRIVATE)
    }
    override fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue).orEmpty()
    }

    override fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun clearPrefs() {
        sharedPreferences.edit().clear().apply()
    }
}