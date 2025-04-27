package com.example.myoutfit

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class FirebaseAuthTest {

    private val auth = FirebaseAuth.getInstance()

    @Test
    fun createUserAndSignIn() = runBlocking {
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

            println("Usuário criado e logado com sucesso: $uniqueEmail")
        } catch (e: Exception) {
            fail("Erro ao criar usuário ou fazer login: ${e.message}")
        }
    }

    @Test
    fun testGoogleSignIn() = runBlocking {
        // Mock da autenticação com o Google
        val googleSignInAccount = mock(GoogleSignInAccount::class.java)
        val firebaseUser = mock(FirebaseUser::class.java)
        val idToken = "mocked-id-token"

        // Simular o comportamento do GoogleSignInAccount
        whenever(googleSignInAccount.idToken).thenReturn(idToken)

        // Simular o processo de autenticação do Firebase com Google
        val authCredential = GoogleAuthProvider.getCredential(idToken, null)

        // Mock do retorno do signInWithCredential
        val authResult = mock(AuthResult::class.java)
        whenever(authResult.user).thenReturn(firebaseUser)

        // Simula a autenticação com o Firebase
        val signInResult = mock(SignInResult::class.java)
        whenever(auth.signInWithCredential(authCredential).await()).thenReturn(signInResult)

        // Verificar se o login foi bem-sucedido
        assertNotNull(signInResult.user)

        println("Login com Google bem-sucedido.")
    }

    // Função para gerar um e-mail único
    private fun generateUniqueEmail(): String {
        return "testuser-${UUID.randomUUID()}@example.com"
    }
}