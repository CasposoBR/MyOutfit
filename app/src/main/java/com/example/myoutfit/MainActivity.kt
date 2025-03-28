package com.example.myoutfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myoutfit.ui.theme.MyOutfitTheme


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