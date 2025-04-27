package com.example.myoutfit.Activitys

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myoutfit.Firebase.AuthViewModel
import com.example.myoutfit.Screens.CategoryScreen
import com.example.myoutfit.Screens.ErrorScreen
import com.example.myoutfit.Screens.FavoritesScreen
import com.example.myoutfit.Screens.LoginScreen
import com.example.myoutfit.Screens.MyOutfitHomeScreen
import com.example.myoutfit.Screens.RegisterScreen
import com.example.myoutfit.Database.TagTypeClothes
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(navController: NavHostController, auth: FirebaseAuth, googleSignInLauncher: ActivityResultLauncher<IntentSenderRequest>) {
    val auth = FirebaseAuth.getInstance()  // Inicialize o FirebaseAuth
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

        composable("error") {
            // Passando navController.popBackStack() como onBack
            ErrorScreen(onBack = { navController.popBackStack() })
        }
        composable("favorites") {
            FavoritesScreen(navController = navController)
        }

        composable(
            route = "category/{categoryName}",
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            val categoryType = try {
                TagTypeClothes.valueOf(categoryName.uppercase()) // Certifique-se de que o nome da categoria corresponde ao valor do Enum
            } catch (e: IllegalArgumentException) {
                null
            }

            // Se categoryType for nulo, redireciona para a tela de erro
            if (categoryType == null) {
                navController.navigate("error")
            } else {
                // Passando corretamente o categoryType, viewModel e navController para CategoryScreen
                CategoryScreen(
                    categoryName = categoryType,  // Passando a categoria para a tela de categoria
                    categoryViewModel = hiltViewModel(), // Passando o ViewModel
                    navController = navController // Passando o navController para navegar
                )
            }
        }
    }
}

