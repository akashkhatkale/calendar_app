// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
}

buildscript {
    repositories { google() }

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.49")
    }
}