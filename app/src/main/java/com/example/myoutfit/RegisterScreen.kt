package com.example.myoutfit

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun RegisterScreen(auth: FirebaseAuth, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var dataNascimento by remember { mutableStateOf("") }

    var cpfError by remember { mutableStateOf(false) }
    var dataError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    // 👉 Formata CPF (ex: 123.456.789-00)
    fun formatCPF(input: String): String {
        val digits = input.filter { it.isDigit() }.take(11)
        return buildString {
            for (i in digits.indices) {
                append(digits[i])
                if (i == 2 || i == 5) append('.')
                if (i == 8) append('-')
            }
        }
    }

    // 👉 Validação real de CPF
    fun isValidCPF(cpf: String): Boolean {
        val clean = cpf.filter { it.isDigit() }
        if (clean.length != 11 || clean.all { it == clean[0] }) return false
        val digits = clean.map { it.toString().toInt() }

        val v1 = (0..8).sumOf { (10 - it) * digits[it] } % 11
        val d1 = if (v1 < 2) 0 else 11 - v1
        if (digits[9] != d1) return false

        val v2 = (0..9).sumOf { (11 - it) * digits[it] } % 11
        val d2 = if (v2 < 2) 0 else 11 - v2
        return digits[10] == d2
    }

    // 👉 Formata Data (ex: 01012000 → 01/01/2000)
    fun formatDate(input: String): String {
        val digits = input.filter { it.isDigit() }.take(8)
        return buildString {
            for (i in digits.indices) {
                append(digits[i])
                if (i == 1 || i == 3) append('/')
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
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
                value = formatCPF(cpf),
                onValueChange = {
                    cpf = it.filter { c -> c.isDigit() }.take(11)
                    cpfError = cpf.length == 11 && !isValidCPF(cpf)
                },
                label = { Text("CPF") },
                isError = cpfError,
                modifier = Modifier.fillMaxWidth()
            )

            if (cpfError && cpf.length == 11) {
                Text("CPF inválido", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = formatDate(dataNascimento),
                onValueChange = {
                    dataNascimento = it.filter { c -> c.isDigit() }.take(8)
                    dataError = dataNascimento.length != 8
                },
                label = { Text("Data de Nascimento (DD/MM/AAAA)") },
                isError = dataError,
                modifier = Modifier.fillMaxWidth()
            )

            if (dataError && dataNascimento.isNotEmpty()) {
                Text("Data inválida", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            errorMessage = "Email inválido"
                            return@launch
                        }

                        if (password.length < 6) {
                            errorMessage = "Senha deve ter pelo menos 6 caracteres"
                            return@launch
                        }

                        if (!isValidCPF(cpf)) {
                            errorMessage = "CPF inválido"
                            return@launch
                        }

                        if (dataNascimento.length != 8) {
                            errorMessage = "Data de nascimento incompleta"
                            return@launch
                        }

                        try {
                            auth.createUserWithEmailAndPassword(email, password).await()
                            navController.navigate("home")
                        } catch (e: Exception) {
                            errorMessage = "Erro ao cadastrar: ${e.message}"
                        }
                    }
                },

                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cadastrar")
            }

            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
