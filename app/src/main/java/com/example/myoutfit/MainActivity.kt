package com.example.myoutfit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.myoutfit.ui.theme.MyOutfitTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    private val authViewModel: AuthViewModel by viewModels()
    private val firebaseAuth = FirebaseAuth.getInstance() // 🔹 Aqui pegamos o auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel.configureGoogleSignIn(this)

        googleSignInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val data = result.data
                authViewModel.handleGoogleSignInResult(data) { success, message ->
                    if (success) {
                        Toast.makeText(this, "Login com Google feito com sucesso", Toast.LENGTH_SHORT).show()
                        // Você pode navegar com base em um estado se quiser
                    } else {
                        Toast.makeText(this, "Erro no login com Google: $message", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        setContent {
            MyOutfitTheme {
                val navController = rememberNavController()
                AppNavigation(
                    navController = navController
                    // 🔹 Passando o auth
                )
            }
        }
    }
}

