package com.example.myoutfit.Activitys

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.myoutfit.Firebase.AuthViewModel
import com.example.myoutfit.Firebase.LoginState
import com.example.myoutfit.Screens.LoginScreen
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var googleSignInLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel.configureGoogleSignIn(this)
        googleSignInLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                authViewModel.handleGoogleSignInResult(data) { success, message ->
                    if (success) {
                        Log.d("LoginActivity", "Login com Google realizado com sucesso!")
                        // Navegue para a próxima tela, por exemplo, para a tela principal
                    } else {
                        Log.e("LoginActivity", "Erro no login com Google: $message")
                        // Mostrar mensagem de erro para o usuário
                    }
                }
            } else {
                Log.e("LoginActivity", "Cadastro com Google cancelado ou falhou")
            }
        }

        // Escutando os estados do LoginState
        lifecycleScope.launch {
            authViewModel.loginState.collectLatest { state ->
                when (state) {
                    is LoginState.Success -> {
                        Log.d("LoginActivity", "Login bem-sucedido")
                        // Navegar para a próxima tela
                        // Aqui você pode adicionar a navegação para a tela inicial ou qualquer outra lógica
                    }
                    is LoginState.Error -> {
                        Log.e("LoginActivity", "Erro: ${state.message}")
                        // Mostrar erro para o usuário
                    }
                    is LoginState.Loading -> {
                        Log.d("LoginActivity", "Processando...")
                        // Mostrar carregando se quiser
                    }
                    is LoginState.Idle -> Unit
                }
            }
        }

        // Configurando a navegação
        setContent {
            val auth = FirebaseAuth.getInstance()  // Inicialize o FirebaseAuth
            val navController = rememberNavController()  // Inicialize o NavController

            // Chama o LoginScreen passando os parâmetros necessários
            LoginScreen(
                auth = auth,
                authViewModel = authViewModel,
                launcher = googleSignInLauncher,
                navController = navController
            )
        }
    }

    private fun loginWithGoogle() {
        authViewModel.getGoogleSignInIntent { intent ->
            if (intent != null) {
                val intentSenderRequest = IntentSenderRequest.Builder(intent).build()
                googleSignInLauncher.launch(intentSenderRequest)
            } else {
                Log.e("LoginActivity", "Falha ao obter o intent de login do Google")
            }
        }
    }
}
