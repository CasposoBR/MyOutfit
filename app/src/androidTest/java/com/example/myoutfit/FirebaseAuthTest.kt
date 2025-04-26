package com.example.myoutfit

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class FirebaseAuthTest {

    @Test
    fun createUserAndSignIn() = runBlocking {
        val auth = FirebaseAuth.getInstance()
        // Gerar um e-mail único para cada execução de teste
        val uniqueEmail = generateUniqueEmail()
        val password = "password123"

        try {
            // Criar o usuário
            val result = auth.createUserWithEmailAndPassword(uniqueEmail, password).await()

            // Verificar se o usuário foi criado
            assertNotNull(result.user)

            // Fazer login com o usuário recém-criado
            val signInResult = auth.signInWithEmailAndPassword(uniqueEmail, password).await()

            // Verificar se o login foi bem-sucedido
            assertNotNull(signInResult.user)

            // Se chegou aqui, o teste foi bem-sucedido
            println("Usuário criado e logado com sucesso: $uniqueEmail")

        } catch (e: Exception) {
            // Se houve uma exceção, o teste falhou
            fail("Erro ao criar usuário ou fazer login: ${e.message}")
        }
    }

    // Função para gerar um e-mail único
    private fun generateUniqueEmail(): String {
        return "testuser-${UUID.randomUUID()}@example.com"
    }
}