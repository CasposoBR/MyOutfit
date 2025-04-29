package com.example.myoutfit.Activitys

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
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
fun AppNavigation(
    navController: NavHostController,
    onLoginSuccess: () -> Unit
) {
    val googleSignInLauncher: ActivityResultLauncher<Intent>? = null
    val authViewModel: AuthViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                authViewModel = authViewModel,
                navController = navController,
                onLoginSuccess = onLoginSuccess
            )
        }

        composable("register") {
            RegisterScreen(
                auth = FirebaseAuth.getInstance(),
                navController = navController,
                googleSignInLauncher = googleSignInLauncher
            )
        }

        composable("home") {
            MyOutfitHomeScreen(navController)
        }

        composable("error") {
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
                TagTypeClothes.valueOf(categoryName.uppercase())
            } catch (e: IllegalArgumentException) {
                null
            }

            if (categoryType == null) {
                navController.navigate("error")
            } else {
                CategoryScreen(
                    categoryName = categoryType,
                    categoryViewModel = hiltViewModel(),
                    navController = navController
                )
            }
        }
    }
}

