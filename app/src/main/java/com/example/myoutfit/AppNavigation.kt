package com.example.myoutfit

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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

        // Rota para a tela de erro
        composable("error") {
            ErrorScreen(navController = navController)
        }

        composable(
            route = "category/{categoryName}",
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            val categoryType = try {
                TagTypeClothes.valueOf(categoryName.uppercase())
            } catch (e: IllegalArgumentException) {
                null
            }

            // Se categoryType for nulo, redireciona para a tela de erro
            if (categoryType == null) {
                navController.navigate("error")
            } else {
                CategoryScreen(
                    categoryType = categoryType,
                    viewModel = hiltViewModel(),
                    navController = navController
                )
            }
        }
    }
}
