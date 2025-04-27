package com.example.myoutfit.Activitys

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.myoutfit.Firebase.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    @Inject
    lateinit var auth: FirebaseAuth // Injetando o FirebaseAuth

    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var googleSignInLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        googleSignInLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                // Remova o callback aqui e deixe o 'handleGoogleSignInResult' já utilizar seu próprio callback.
                authViewModel.handleGoogleSignInResult(this, data) { success, message ->
                    if (success) {
                        Toast.makeText(this, "Login com Google feito com sucesso", Toast.LENGTH_SHORT).show()
                        // Navegar para a próxima tela ou atualizar a UI
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Erro no login com Google: $message", Toast.LENGTH_SHORT).show()
                        // Mostrar mensagem de erro para o usuário
                    }
                }
            } else {
                Toast.makeText(this, "Erro no login com Google", Toast.LENGTH_SHORT).show()
                // Mostrar mensagem de erro para o usuário
            }
        }

    }
}
