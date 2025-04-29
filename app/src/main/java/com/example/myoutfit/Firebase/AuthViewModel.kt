package com.example.myoutfit.Firebase

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val oneTapClient: SignInClient,  // Deve ser SignInClient, não OneTapSignInClient

    @ApplicationContext private val context: Context
) : ViewModel() {

    private var signInRequest: BeginSignInRequest? = null

    fun configureGoogleSignIn() {
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId("253613913863-i76s4mrirgclb8or61cre7q7mh45ksu5.apps.googleusercontent.com") // seu client ID
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }

    fun getGoogleSignInIntent(callback: (pendingIntent: android.app.PendingIntent?) -> Unit) {
        oneTapClient.beginSignIn(signInRequest!!) // Use SignInClient aqui
            .addOnSuccessListener { result ->
                callback(result.pendingIntent)
            }
            .addOnFailureListener { e ->
                Log.e("AuthViewModel", "Erro ao iniciar o SignIn: ${e.localizedMessage}")
                callback(null)
            }
    }

    fun getGoogleSignInIntentForLogin(): Intent {
        return googleSignInClient.signInIntent
    }

    fun handleGoogleSignInResult(
        data: Intent?,
        onResult: (Boolean, String?) -> Unit
    ) {
        val credential = oneTapClient.getSignInCredentialFromIntent(data) // Novamente, use SignInClient aqui
        val idToken = credential.googleIdToken
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.localizedMessage)
                }
            }
    }
}