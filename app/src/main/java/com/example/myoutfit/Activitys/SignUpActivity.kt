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
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.myoutfit.Firebase.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var googleSignInLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance() // Inicializa FirebaseAuth
        authViewModel.configureGoogleSignIn(this)

        googleSignInLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                authViewModel.handleGoogleSignInResult(this,data) { success, message ->
                    if (success) {
                        Log.d("SignUpActivity", "Cadastro com Google bem-sucedido!")
                        // Navegar para a próxima tela ou atualizar a UI
                    } else {
                        Log.e("SignUpActivity", "Erro no cadastro com Google: $message")
                        // Mostrar mensagem de erro para o usuário
                    }
                }
            } else {
                Log.e("SignUpActivity", "Cadastro com Google cancelado ou falhou")
                // Mostrar mensagem de erro para o usuário
            }
        }

        setContent {
            SignUpScreen(auth, authViewModel, ::signUpWithGoogle)
        }
    }

    private fun signUpWithGoogle() {
        authViewModel.getGoogleSignInIntent { intent ->
            if (intent != null) {
                val intentSenderRequest = IntentSenderRequest.Builder(intent.extras?.get("android.intent.extra.INTENT") as android.content.IntentSender).build()
                googleSignInLauncher.launch(intentSenderRequest)
            } else {
                Log.e("SignUpActivity", "Falha ao obter o intent de login do Google")
                // Mostrar mensagem de erro para o usuário
            }
        }
    }
}

@Composable
fun SignUpScreen(
    auth: FirebaseAuth,
    authViewModel: AuthViewModel,
    signUpWithGoogle: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = cpf,
            onValueChange = { cpf = it },
            label = { Text("CPF") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = birthDate,
            onValueChange = { birthDate = it },
            label = { Text("Data de Nascimento") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { registerWithEmail(auth, email, password, cpf, birthDate) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cadastrar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { signUpWithGoogle() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cadastrar com Google")
        }
    }
}

fun registerWithEmail(
    auth: FirebaseAuth,
    email: String,
    password: String,
    cpf: String,
    birthDate: String
) {
    if (email.isNotEmpty() && password.isNotEmpty() && cpf.isNotEmpty() && birthDate.isNotEmpty()) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Cadastro bem-sucedido!")
                } else {
                    println("Erro ao cadastrar, tente novamente.")
                }
            }
    } else {
        println("Preencha todos os campos.")
    }
}

