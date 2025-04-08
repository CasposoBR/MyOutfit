package com.example.myoutfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.myoutfit.ui.theme.MyOutfitTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyOutfitTheme {
                AuthScreen() //tela de login e cadastro
            }
        }
    }
}

@Composable
fun AuthScreen() {
    val auth = remember { FirebaseAuth.getInstance() }
    LoginScreen(auth = auth)

    }
