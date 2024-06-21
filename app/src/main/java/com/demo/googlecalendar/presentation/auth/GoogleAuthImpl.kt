package com.demo.googlecalendar.presentation.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.demo.googlecalendar.presentation.sharedprefs.SharedPrefs
import com.demo.googlecalendar.presentation.utils.SharedPrefsConstants.ACCESS_TOKEN
import com.demo.googlecalendar.presentation.utils.SharedPrefsConstants.AUTH_CODE
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.services.calendar.CalendarScopes
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import javax.inject.Inject

class GoogleAuthImpl @Inject constructor(
    private val sharedPrefs: SharedPrefs
): GoogleAuth {

    private var googleSignInClient: GoogleSignInClient? = null
    private var auth: FirebaseAuth? = null

    init {
        auth = Firebase.auth
    }

    override fun init(context: Context) {
        val gmailScope = Scope("https://www.googleapis.com/auth/calendar")
        Log.d("AKASH_LOG", "init: scope uri: ${gmailScope.scopeUri}")
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("359428286752-n7m91gbvm274qgg4neu17ae9rlj386k2.apps.googleusercontent.com")
            .requestEmail()
            .requestScopes(gmailScope)
            .requestServerAuthCode("359428286752-n7m91gbvm274qgg4neu17ae9rlj386k2.apps.googleusercontent.com")
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    override fun signIn(launcher: ActivityResultLauncher<Intent>) {
        googleSignInClient?.signInIntent?.let {
            launcher.launch(it)
        }
    }

    override fun signOut() {
        googleSignInClient?.signOut()
    }

    override fun isLoggedIn(): Boolean {
        return sharedPrefs.getString(AUTH_CODE).isNotEmpty()
    }
}