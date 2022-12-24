package id.filab.storyapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.filab.storyapp.dto.ListStory
import id.filab.storyapp.ui.screen.*
import id.filab.storyapp.utils.viewModelFactory
import id.filab.storyapp.viewmodel.StoryViewModel
import id.filab.storyapp.viewmodel.UserViewModel
import kotlinx.serialization.json.Json

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()
    val storyViewModel: StoryViewModel = viewModel(
        factory = viewModelFactory {
            StoryViewModel(userViewModel)
        }
    )

    NavHost(navController = navController, startDestination = Routes.SplashScreen.route ) {

        composable(route = Routes.SplashScreen.route) {
            SplashScreen(navController, userViewModel)
        }
        composable(route = Routes.SignupScreen.route) {
            SignupScreen(
                userViewModel = userViewModel,
                navigateTo = {
                    when(it) {
                        Routes.StoriesScreen.route -> {
                            navController.navigate(it) {
                                popUpTo(Routes.SignupScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                        else -> navController.navigate(it)
                    }
                }
            )
        }
        composable(route = Routes.LoginScreen.route) {
            LoginScreen(
                userViewModel = userViewModel,
                navigateTo = {
                    navController.navigate(it) {
                        popUpTo(Routes.SignupScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Routes.StoriesScreen.route) {
            StoriesScreen(userViewModel, storyViewModel, navController)
        }
        composable(
            route = Routes.StoryDetailScreen.route + "/{storyItem}",
            arguments = listOf(
                navArgument("storyItem") {
                    type = NavType.StringType
                    nullable = false
                    defaultValue = ""
                }
            )
        ) {
            StoryDetailScreen(
                storyItem = Json.decodeFromString(ListStory.serializer(), it.arguments?.getString("storyItem") ?: "")
            )
        }
        composable(route = Routes.StoryPickerScreen.route) {
            StoryPickerScreen(
                storyViewModel = storyViewModel,
                goBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Routes.StoriesMapScreen.route) {
            StoriesMapScreen(
                storyViewModel
            )
        }
    }
}