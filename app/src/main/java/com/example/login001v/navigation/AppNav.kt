package com.example.login001v.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.login001v.ui.login.LoginScreen
import com.example.login001v.ui.postscreen.PostScreen
import com.example.login001v.ui.registro.RegistroScreen
import com.example.login001v.view.faq.FaqScreen
import com.example.login001v.viewmodel.PostViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.login001v.ui.recetas.RecetasScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "drawerMenu/{user}"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { username ->
                    navController.navigate("drawerMenu/$username") {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToRegistro = {
                    navController.navigate("registro")
                }
            )
        }

        composable("registro") {
            RegistroScreen(
                onRegistroSuccess = {
                    // si el registro es exitoso, vuelve a login
                    navController.popBackStack()
                },
                onNavigateBack = {
                    //  flecha arriba
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "drawerMenu/{user}",
            arguments = listOf(navArgument("user") { type = NavType.StringType })
        ) { backStackEntry ->
            val user = backStackEntry.arguments?.getString("user") ?: "Usuario"
            DrawerMenu(username = user,
                navController = navController)
        }

        composable(
            route = "ProductoFormScreen/{username}/{precio}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
                navArgument("precio") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val precio = backStackEntry.arguments?.getString("precio") ?: ""
            ProductoFormScreen(navController = navController, nombre = username, precio = precio)
        }
        composable("faq") {
            FaqScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("info") {
            InfoScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("post"){
            val vm: PostViewModel = viewModel()
            PostScreen(viewModel = vm)
        }
        composable("recetas"){
            RecetasScreen()
        }
    }
}