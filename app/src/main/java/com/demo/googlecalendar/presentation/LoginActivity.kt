package com.demo.googlecalendar.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.demo.googlecalendar.R
import com.demo.googlecalendar.presentation.auth.GoogleAuth
import com.demo.googlecalendar.presentation.auth.GoogleAuthImpl
import com.demo.googlecalendar.presentation.config.ClientConfig
import com.demo.googlecalendar.presentation.screens.LoginScreen
import com.demo.googlecalendar.presentation.sharedprefs.SharedPrefs
import com.demo.googlecalendar.presentation.utils.SharedPrefsConstants.ACCESS_TOKEN
import com.demo.googlecalendar.presentation.utils.SharedPrefsConstants.AUTH_CODE
import com.demo.googlecalendar.presentation.utils.SharedPrefsConstants.PROFILE_URL
import com.demo.googlecalendar.presentation.utils.SharedPrefsConstants.REFRESH_TOKEN
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.calendar.Calendar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    @Inject
    internal lateinit var googleAuth: GoogleAuth

    @Inject
    internal lateinit var sharedPrefs: SharedPrefs

    @Inject
    internal lateinit var clientConfig: ClientConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        googleAuth.init(this)
        enableEdgeToEdge()
        setContent {
            LoginScreen(
                onSignInClick = {
                    googleAuth.signIn(signInWithGoogleLauncher)
                }
            )
        }
    }

    private val signInWithGoogleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val intent = result.data

            if (intent == null) {
                return@registerForActivityResult
            }
            handleGoogleLogin(intent)
        }

    private fun handleGoogleLogin(intent: Intent) {
        val signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(intent)
        if (signInResult == null) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            return
        }
        GoogleSignIn
            .getSignedInAccountFromIntent(intent)
            .addOnSuccessListener {
                it.account?.let { account ->
                    lifecycleScope.launch(Dispatchers.IO) {
                        val HTTP_TRANSPORT = com.google.api.client.http.javanet.NetHttpTransport()
                        val tokenResponse = GoogleAuthorizationCodeTokenRequest(
                            HTTP_TRANSPORT, GsonFactory.getDefaultInstance(),
                            "https://www.googleapis.com/oauth2/v4/token",
                            clientConfig.getClientId(),
                            clientConfig.getClientSecret(),
                            it.serverAuthCode.orEmpty(),
                            ""
                        ).execute()
                        sharedPrefs.putString(AUTH_CODE, it.serverAuthCode.orEmpty())
                        sharedPrefs.putString(REFRESH_TOKEN, tokenResponse.refreshToken.orEmpty())
                        sharedPrefs.putString(PROFILE_URL, it.photoUrl.toString())
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                MainActivity::class.java
                            )
                        )
                        finish()
                    }
                }
            }
    }
}