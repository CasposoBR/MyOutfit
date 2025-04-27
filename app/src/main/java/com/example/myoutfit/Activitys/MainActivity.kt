package com.example.myoutfit.Activitys

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.example.myoutfit.Firebase.AuthViewModel
import com.example.myoutfit.R
import com.example.myoutfit.ui.theme.MyOutfitTheme
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var googleSignInLauncher: ActivityResultLauncher<IntentSenderRequest>
    private val authViewModel: AuthViewModel by viewModels()
    private val firebaseAuth = FirebaseAuth.getInstance() // 🔹 Aqui pegamos o auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuração do SignIn
        authViewModel.configureGoogleSignIn(this)

        // Registro do Launcher para o resultado do login com Google
        googleSignInLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            val data = result.data
            // Passando 'this' (Activity) como o primeiro parâmetro
            authViewModel.handleGoogleSignInResult(this, data) { success, message ->
                if (success) {
                    Toast.makeText(this, "Login com Google feito com sucesso", Toast.LENGTH_SHORT).show()
                    // Aqui você pode navegar com base em algum estado ou ação
                } else {
                    @HiltViewModel
                    class AuthViewModel @Inject constructor(
                        private val firebaseAuth: FirebaseAuth,
                        private val context: Context // Injeta o Context
                    ) : ViewModel() {

                        private val oneTapClient: SignInClient = Identity.getSignInClient(context)
                        private val signInRequest: BeginSignInRequest = BeginSignInRequest.builder()
                            .setGoogleIdTokenRequestOptions(
                                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                    .setSupported(true)
                                    .setServerClientId(context.getString(R.string.default_web_client_id)) // Usa o ID do strings.xml
                                    .setFilterByAuthorizedAccounts(false)
                                    .build()
                            )
                            .setAutoSelectEnabled(true) // Pode ser removido ou tornado configurável
                            .build()

                        init {
                            Log.d("AuthViewModel", "AuthViewModel criado e configurado.")
                        }

                        fun getGoogleSignInIntent(callback: (IntentSender?) -> Unit) {
                            oneTapClient.beginSignIn(signInRequest)
                                .addOnSuccessListener { result ->
                                    Log.d(
                                        "AuthViewModel",
                                        "Intent de login do Google obtido com sucesso."
                                    )
                                    callback(result.pendingIntent.intentSender)
                                }
                                .addOnFailureListener { e ->
                                    Log.e(
                                        "AuthViewModel",
                                        "Falha ao obter o intent de login do Google",
                                        e
                                    )
                                    callback(null)
                                }
                        }

                        fun handleGoogleSignInResult(
                            data: Intent?,
                            callback: (Boolean, String?) -> Unit
                        ) {
                            if (data == null) {
                                Log.e("AuthViewModel", "Intent de login do Google é nulo")
                                callback(false, "Intent de login do Google é nulo")
                                return
                            }
                            try {
                                val credential = oneTapClient.getSignInCredentialFromIntent(data)
                                val idToken = credential.googleIdToken
                                if (!idToken.isNullOrEmpty()) {
                                    val firebaseCredential =
                                        GoogleAuthProvider.getCredential(idToken, null)
                                    firebaseAuth.signInWithCredential(firebaseCredential)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Log.d(
                                                    "AuthViewModel",
                                                    "Login do Google bem-sucedido."
                                                )
                                                callback(true, null)
                                            } else {
                                                Log.e(
                                                    "AuthViewModel",
                                                    "Falha no login do Google",
                                                    task.exception
                                                )
                                                callback(false, task.exception?.localizedMessage)
                                            }
                                        }
                                } else {
                                    Log.e("AuthViewModel", "Token inválido")
                                    callback(false, "Token inválido")
                                }
                            } catch (e: ApiException) {
                                Log.e("AuthViewModel", "Falha no login do Google", e)
                                val errorMessage = when (e.statusCode) {
                                    CommonStatusCodes.NETWORK_ERROR -> "Erro de rede"
                                    CommonStatusCodes.API_NOT_CONNECTED -> "API não conectada"
                                    else -> e.localizedMessage ?: "Erro desconhecido"
                                }
                                callback(false, errorMessage)
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
                                        Log.d(
                                            "AuthViewModel",
                                            "Login com email e senha bem-sucedido."
                                        )
                                        callback(true, null)
                                    } else {
                                        Log.e(
                                            "AuthViewModel",
                                            "Falha no login com email e senha",
                                            task.exception
                                        )
                                        callback(false, task.exception?.localizedMessage)
                                    }
                                }
                        }
                    }
                    Toast.makeText(this, "Erro no login com Google: $message", Toast.LENGTH_SHORT).show()
                }
            }
        }

        setContent {
            MyOutfitTheme {
                val navController = rememberNavController()
                // Passando o navController e o authViewModel para o AppNavigation
                AppNavigation(
                    navController = navController,
                    auth = firebaseAuth, // Passando o FirebaseAuth
                    googleSignInLauncher = googleSignInLauncher
                )
            }
        }
    }
}

