package com.example.myoutfit.Activitys

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.myoutfit.Firebase.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        // Registrar o ActivityResultLauncher para o Google SignIn
        googleSignInLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                authViewModel.handleGoogleSignInResult(data) { success, message ->
                    if (success) {
                        navigateToHomeScreen()
                    } else {
                        showErrorMessage(message)
                    }
                }
            } else {
                showErrorMessage("Falha no login com o Google")
            }
        }

        // Iniciar o fluxo de login do Google
        startGoogleSignIn()
    }

    private fun startGoogleSignIn() {
        val signInIntent = authViewModel.getGoogleSignInIntentForLogin()
        googleSignInLauncher.launch(signInIntent)
    }

    // Função para navegação para a tela principal após login
    private fun navigateToHomeScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Evitar voltar pro login
    }

    // Função para mostrar mensagens de erro
    private fun showErrorMessage(message: String?) {
        message?.let {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }
}