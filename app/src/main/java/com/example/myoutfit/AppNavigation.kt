package com.example.myoutfit

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    googleSignInLauncher: ActivityResultLauncher<Intent>,
    firebaseAuth: FirebaseAuth
) {
    val auth = FirebaseAuth.getInstance()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                auth = auth,
                authViewModel = authViewModel,
                launcher = googleSignInLauncher,
                navController = navController
            )
        }
        composable("register") {
            RegisterScreen(
                auth = auth,
                navController = navController
            )
        }
        composable("home") {
            MyOutfitHomeScreen(navController)
        }
        composable("category/{categoryName}") { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            val categoryType = try {
                CategoryType.valueOf(categoryName)
            } catch (e: IllegalArgumentException) {
                CategoryType.TREND // fallback
            }

            CategoryProductsScreen(categoryType = categoryType, navController = navController)
        }
    }
}