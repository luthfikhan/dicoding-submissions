package id.filab.wisataskh.ui.navigation

sealed class Routes(val route:String) {
    object SplashScreen: Routes("splash_screen")
    object ListWisataSkhScreen: Routes("list_wisata_skh_screen")
    object DetailWisataSkhScreen: Routes("detail_wisata_skh_screen")
    object ProfileScreen: Routes("profile_screen")

    fun withArgs(vararg args: String): String {
        return  buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}
