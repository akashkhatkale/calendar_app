package com.demo.googlecalendar.presentation.sharedprefs

interface SharedPrefs {
    fun getString(key: String, defaultValue: String = ""): String
    fun putString(key: String, value: String)
    fun clearPrefs()
}