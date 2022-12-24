package id.filab.wisataskh.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.filab.wisataskh.ui.screens.DetailWisataSkhScreen
import id.filab.wisataskh.ui.screens.ListWisataSkhScreen
import id.filab.wisataskh.ui.screens.ProfileScreen
import id.filab.wisataskh.ui.screens.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.SplashScreen.route ) {
        composable(
            route = Routes.SplashScreen.route
        ) {
            SplashScreen(navController)
        }
        composable(
            route = Routes.ListWisataSkhScreen.route
        ) {
            ListWisataSkhScreen(navController = navController)
        }
        composable(
            route = Routes.DetailWisataSkhScreen.route + "/{wisataId}",
            arguments = listOf(
                navArgument("wisataId") {
                    type = NavType.IntType
                    nullable = false
                    defaultValue = 1
                }
            )
        ) {
            DetailWisataSkhScreen(id = it.arguments?.getInt("wisataId") ?: 1)
        }
        composable(
            route = Routes.ProfileScreen.route
        ) {
            ProfileScreen()
        }
    }
}