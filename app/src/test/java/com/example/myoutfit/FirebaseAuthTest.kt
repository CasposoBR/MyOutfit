package com.example.myoutfit

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirebaseAuthTest {

    @Test
    fun createUserAndSignIn() = runBlocking {
        val auth = FirebaseAuth.getInstance()
        val email = "testuser@myoutfit.com"
        val password = "12345678"

        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            assertNotNull(result.user)
        } catch (e: Exception) {
            fail("Erro ao criar usuário: ${e.message}")
        }
    }
}