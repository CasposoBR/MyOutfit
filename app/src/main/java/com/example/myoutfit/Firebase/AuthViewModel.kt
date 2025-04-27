package com.example.myoutfit.Firebase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    fun configureGoogleSignIn(context: Context) {
        oneTapClient = Identity.getSignInClient(context)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(context.getString(com.example.myoutfit.R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .setAutoSelectEnabled(true)
            .build()
    }

    fun getGoogleSignInIntent(callback: (Intent?) -> Unit) {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                callback(result.pendingIntent.intentSender.let { Intent(Intent.ACTION_VIEW).putExtra(Intent.EXTRA_INTENT, it) })
            }
            .addOnFailureListener { e ->
                Log.e("Auth", "Falha ao obter o intent de login do Google", e)
                callback(null)
            }
    }

    fun handleGoogleSignInResult(activity: Activity, data: Intent?, callback: (Boolean, String?) -> Unit) {
        if (data == null) {
            callback(false, "Intent de login do Google é nulo")
            return
        }
        try {
            val credential = Identity.getSignInClient(activity).getSignInCredentialFromIntent(data)
            val idToken = credential.googleIdToken
            if (!idToken.isNullOrEmpty()) {
                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                firebaseAuth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener { task ->
                        callback(task.isSuccessful, task.exception?.localizedMessage)
                    }
            } else {
                callback(false, "Token inválido")
            }
        } catch (e: ApiException) {
            Log.e("Auth", "Falha no login do Google", e)
            callback(false, e.localizedMessage)
        }
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        callback: (Boolean, String?) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("AuthViewModel", "Login com email e senha bem-sucedido.")
                    callback(true, null)
                } else {
                    Log.e("AuthViewModel", "Falha no login com email e senha", task.exception)
                    callback(false, task.exception?.localizedMessage)
                }
            }
    }
}