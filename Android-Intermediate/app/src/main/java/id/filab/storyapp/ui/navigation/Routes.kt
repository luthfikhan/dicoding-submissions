package id.filab.storyapp.ui.navigation

sealed class Routes(val route:String) {
    object SplashScreen: Routes("splash_screen")
    object SignupScreen: Routes("signup_screen")
    object LoginScreen: Routes("login_screen")
    object StoriesScreen: Routes("stories_screen")
    object StoryDetailScreen: Routes("story_detail_screen")
    object StoryPickerScreen: Routes("story_picker_screen")
    object StoriesMapScreen: Routes("stories_map_screen")

    fun withArgs(vararg args: String): String {
        return  buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}
