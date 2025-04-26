package com.example.myoutfit.Activitys

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.myoutfit.Firebase.AuthViewModel
import com.example.myoutfit.ui.theme.MyOutfitTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

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
            authViewModel.handleGoogleSignInResult(data) { success, message ->
                if (success) {
                    Toast.makeText(this, "Login com Google feito com sucesso", Toast.LENGTH_SHORT).show()
                    // Aqui você pode navegar com base em algum estado ou ação
                } else {
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

