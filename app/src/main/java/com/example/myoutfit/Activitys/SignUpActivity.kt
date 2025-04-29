package com.example.myoutfit.Activitys

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.myoutfit.Firebase.AuthViewModel
import com.example.myoutfit.Screens.RegisterScreen
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : ComponentActivity() {
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel.configureGoogleSignIn() // Configura a autenticação com o Google

        // Registro do launcher para resultado do Google Sign-In
        googleSignInLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                authViewModel.handleGoogleSignInResult(data) { success, message ->
                    if (success) {
                        navigateToHomeScreen()
                    } else {
                        showToast("Erro: $message")
                    }
                }
            } else {
                showToast("Erro ao realizar o login com o Google.")
            }
        }

        // Chama RegisterScreen com os parâmetros corretos
        setContent {
            RegisterScreen(
                auth = FirebaseAuth.getInstance(), // Passa a instância do FirebaseAuth
                navController = rememberNavController(), // Passa o navController
                googleSignInLauncher = googleSignInLauncher // Passa o launcher
            )
        }
    }

    // Função para iniciar o fluxo de login com o Google
    private fun signUpWithGoogle() {
        val signInIntent = authViewModel.getGoogleSignInIntentForLogin()
        googleSignInLauncher.launch(signInIntent)
    }

    // Navegação para a tela principal após login bem-sucedido
    private fun navigateToHomeScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()  // Finaliza a atividade de cadastro para evitar que o usuário volte para ela
    }

    // Função para exibir mensagens de erro com Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


