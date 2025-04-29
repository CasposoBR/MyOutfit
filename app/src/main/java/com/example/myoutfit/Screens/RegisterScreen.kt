package com.example.myoutfit.Screens

import android.content.Intent
import android.util.Patterns
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(auth: FirebaseAuth, navController: NavHostController, googleSignInLauncher: ActivityResultLauncher<Intent>?) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var dataNascimento by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var dataError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }  // Variável para controlar o progresso

    val scope = rememberCoroutineScope()

    fun formatDate(input: String): String {
        val digits = input.filter { it.isDigit() }.take(8)
        return buildString {
            for (i in digits.indices) {
                append(digits[i])
                if (i == 1 || i == 3) append('/')
            }
        }
    }

    fun isValidBirthDate(dateStr: String): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val birthDate = LocalDate.parse(dateStr, formatter)
            val today = LocalDate.now()
            val age = ChronoUnit.YEARS.between(birthDate, today)

            !birthDate.isAfter(today) && age in 13..120
        } catch (e: DateTimeParseException) {
            false
        }
    }

    // Função de validação de e-mail (permitindo apenas Gmail e Hotmail, exceto admin@teste.com)
    fun isValidEmail(email: String): Boolean {
        return when {
            email == "admin@teste.com" -> true // Permite o e-mail admin@teste.com
            Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                val domain = email.substringAfter("@")
                domain == "gmail.com" || domain == "hotmail.com"
            }
            else -> false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text("Cadastro") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {

            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = !isValidEmail(it)  // Verifica o email com a nova função
                    },
                    label = { Text("Email", color = MaterialTheme.colorScheme.onSurface) },
                    isError = emailError,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
                if (emailError) {
                    Text("Email inválido (apenas Gmail e Hotmail são permitidos)", color = MaterialTheme.colorScheme.error)
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = it.length < 6
                    },
                    label = { Text("Senha", color = MaterialTheme.colorScheme.onSurface) },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = passwordError,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
                if (passwordError && password.isNotEmpty()) {
                    Text("Senha deve ter pelo menos 6 caracteres", color = MaterialTheme.colorScheme.error)
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = dataNascimento,
                    onValueChange = {
                        val digits = it.filter { c -> c.isDigit() }.take(8)
                        val formatted = formatDate(digits)
                        dataNascimento = formatted
                        dataError = !isValidBirthDate(formatted)
                    },
                    label = { Text("Data de Nascimento (DD/MM/AAAA)", color = MaterialTheme.colorScheme.onSurface) },
                    isError = dataError,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
                if (dataError && dataNascimento.isNotEmpty()) {
                    Text("Data inválida", color = MaterialTheme.colorScheme.error)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (isLoading) return@Button // Impede múltiplos cliques enquanto carrega
                        isLoading = true
                        scope.launch {
                            if (emailError || passwordError) {
                                errorMessage = "Por favor, corrija os erros antes de continuar"
                                isLoading = false
                                return@launch
                            }

                            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            sdf.isLenient = false
                            try {
                                val birthDate = sdf.parse(dataNascimento)
                                val today = Calendar.getInstance()
                                val birthCal = Calendar.getInstance().apply { time = birthDate!! }

                                var age = today.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR)
                                if (today.get(Calendar.DAY_OF_YEAR) < birthCal.get(Calendar.DAY_OF_YEAR)) {
                                    age -= 1
                                }

                                if (age < 15 || age > 120) {
                                    dataError = true
                                    errorMessage = "Idade deve estar entre 15 e 120 anos"
                                    isLoading = false
                                    return@launch
                                }

                                dataError = false // validação passou

                            } catch (e: Exception) {
                                dataError = true
                                errorMessage = "Data de nascimento inválida"
                                isLoading = false
                                return@launch
                            }

                            try {
                                auth.createUserWithEmailAndPassword(email, password).await()
                                navController.navigate("home")
                            } catch (e: Exception) {
                                errorMessage = "Erro ao cadastrar: ${e.message}"
                            }
                            isLoading = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,  // Desabilita o botão enquanto carrega
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(4.dp),  // Aqui você pode ajustar a margem, caso necessário
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Cadastrar")
                    }
                }

                errorMessage?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}