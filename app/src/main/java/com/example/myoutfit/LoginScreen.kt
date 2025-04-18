package com.example.myoutfit

    import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun LoginScreen(
    auth: FirebaseAuth,
    authViewModel: AuthViewModel,
    launcher: ActivityResultLauncher<Intent>,
    navController: NavHostController
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope() // Usando o coroutineScope do Composable

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Campo de email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo de senha
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Senha") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botão de login
                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                auth.signInWithEmailAndPassword(email, password).await()
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true } // Para garantir que o usuário não consiga voltar para a tela de login
                                }
                            } catch (e: FirebaseAuthInvalidCredentialsException) {
                                errorMessage = "Credenciais inválidas"
                            } catch (e: Exception) {
                                errorMessage = "Erro: ${e.message}"
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("INICIAR SESSÃO")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Botão de navegação para tela de registro
                Button(
                    onClick = { navController.navigate("register") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("REGISTRE-SE")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Botão de login com Google
                Button(
                    onClick = {
                        launcher.launch(authViewModel.getGoogleSignInIntent())
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login com Google")
                }

                // Exibe mensagem de erro se houver
                errorMessage?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}