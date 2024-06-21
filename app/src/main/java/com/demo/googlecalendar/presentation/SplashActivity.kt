package com.demo.googlecalendar.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.demo.googlecalendar.R
import com.demo.googlecalendar.presentation.auth.GoogleAuth
import com.demo.googlecalendar.presentation.auth.GoogleAuthImpl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    @Inject
    internal lateinit var googleAuth: GoogleAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(
                modifier = Modifier
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
            }
        }
        checkIfLoggedIn()
    }

    private fun checkIfLoggedIn() {
        if (googleAuth.isLoggedIn()) {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
            finish()
        } else {
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )
            finish()
        }
    }
}