package com.example.myoutfit.Firebase

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    // StateFlow para acompanhar o estado de login
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun configureGoogleSignIn(context: Context) {
        oneTapClient = Identity.getSignInClient(context)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(context.getString(com.example.myoutfit.R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    fun getGoogleSignInIntent(callback: (android.content.IntentSender?) -> Unit) {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                callback(result.pendingIntent.intentSender)
            }
            .addOnFailureListener { e ->
                Log.e("Auth", "Falha ao obter o intent de login do Google", e)
                callback(null)
            }
    }

    fun handleGoogleSignInResult(data: Intent?, callback: (Boolean, String) -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            // Sucesso ao obter a conta do Google
            val firebaseCredential = GoogleAuthProvider.getCredential(account.idToken, null)
            firebaseAuth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback(true, "Login bem-sucedido")
                    } else {
                        callback(false, "Erro no login: ${task.exception?.localizedMessage}")
                    }
                }
        } catch (e: ApiException) {
            callback(false, "Erro ao processar o resultado do Google Sign-In: ${e.localizedMessage}")
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("AuthViewModel", "Login com email e senha bem-sucedido.")
                        _loginState.value = LoginState.Success
                    } else {
                        Log.e("AuthViewModel", "Falha no login com email e senha", task.exception)
                        _loginState.value = LoginState.Error(task.exception?.localizedMessage ?: "Erro desconhecido")
                    }
                }
        }
    }

    fun resetLoginState() {
        _loginState.value = LoginState.Idle
    }
}

// Estados possíveis do login
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

