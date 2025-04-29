package com.example.myoutfit.Firebase

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.myoutfit.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val auth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    // Pode usar o SignInClient do One Tap para outras necessidades

    @ApplicationContext private val context: Context
) : ViewModel() {

    private var signInRequest: BeginSignInRequest? = null

    // Configura a solicitação de login com Google
    fun configureGoogleSignIn() {
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(context.getString(R.string.default_web_client_id)) // Usa o context injetado
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }

    // Retorna o intent para o fluxo de login do Google
    fun getGoogleSignInIntentForLogin(): Intent {
        return googleSignInClient.signInIntent
    }

    // Lida com o resultado do Google Sign-In
    fun handleGoogleSignInResult(data: Intent?, onResult: (Boolean, String?) -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        if (task.isSuccessful) {
            val googleAccount = task.result
            val idToken = googleAccount?.idToken
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onResult(true, null) // Login bem-sucedido
                    } else {
                        onResult(false, task.exception?.localizedMessage) // Falha no login
                    }
                }
        } else {
            onResult(false, task.exception?.localizedMessage) // Erro no processo de login
        }
    }
}