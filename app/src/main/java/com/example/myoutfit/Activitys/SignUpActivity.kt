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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.myoutfit.Firebase.AuthViewModel
import com.example.myoutfit.Firebase.LoginState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : ComponentActivity() {
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

                // Passando um callback com dois parâmetros: Boolean e String
                authViewModel.handleGoogleSignInResult(data) { success, message ->
                    if (success) {
                        Log.d("SignUpActivity", "Login com Google bem-sucedido")
                        // Aqui você pode realizar alguma ação de navegação ou exibir uma mensagem
                    } else {
                        Log.e("SignUpActivity", "Erro no login com Google: $message")
                        // Exibir mensagem de erro
                    }
                }
            } else {
                Log.e("SignUpActivity", "Cadastro com Google cancelado ou falhou")
            }
        }

        // Escutando os estados do LoginState (não é mais AuthState)
        lifecycleScope.launch {
            authViewModel.loginState.collectLatest { state ->
                when (state) {
                    is LoginState.Success -> {
                        Log.d("SignUpActivity", "Cadastro/Login bem-sucedido")
                        // Ir para outra tela ou exibir sucesso
                    }
                    is LoginState.Error -> {
                        Log.e("SignUpActivity", "Erro: ${state.message}")
                        // Mostrar erro para o usuário
                    }
                    is LoginState.Loading -> {
                        Log.d("SignUpActivity", "Processando...")
                        // Mostrar carregando se quiser
                    }
                    is LoginState.Idle -> Unit
                }
            }
        }

        setContent {
            SignUpScreen(authViewModel, ::signUpWithGoogle)
        }
    }

    private fun signUpWithGoogle() {
        authViewModel.getGoogleSignInIntent { intent ->
            if (intent != null) {
                val intentSenderRequest = IntentSenderRequest.Builder(intent).build()
                googleSignInLauncher.launch(intentSenderRequest)
            } else {
                Log.e("SignUpActivity", "Falha ao obter o intent de login do Google")
            }
        }
    }
}

@Composable
fun SignUpScreen(
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
            onClick = {
                authViewModel.signInWithEmailAndPassword(email, password)
            },
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


