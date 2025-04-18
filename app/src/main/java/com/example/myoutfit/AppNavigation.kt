package com.example.myoutfit

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()

    // Launcher para o login com Google
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // TODO: Tratar resultado do login com Google aqui, usando authViewModel.handleGoogleSignInResult(result)
    }

    val authViewModel: AuthViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                auth = auth,  // Passando o FirebaseAuth aqui
                authViewModel = authViewModel,
                launcher = googleSignInLauncher,
                navController = navController
            )
        }

        composable("register") {
            RegisterScreen(
                auth = auth,  // Passando o FirebaseAuth aqui também
                navController = navController
            )
        }

        composable("home") {
            MyOutfitHomeScreen(navController)
        }

        composable("category/{categoryName}") { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            val categoryViewModel: CategoryViewModel = hiltViewModel()
            val categoryType = try {
                CategoryType.valueOf(categoryName)
            } catch (e: IllegalArgumentException) {
                CategoryType.TREND
            }

            CategoryScreen(
                category = categoryName,
                categoryType = categoryType,
                viewModel = categoryViewModel,
                navController = navController
            )
        }
    }
}
