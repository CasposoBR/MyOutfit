package com.example.myoutfit

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(auth = auth, navController = navController)
        }
        composable("home") {
            HomeScreen()
        }
        composable("register") {
            RegisterScreen(auth = auth, navController = navController)
        }
    }
}