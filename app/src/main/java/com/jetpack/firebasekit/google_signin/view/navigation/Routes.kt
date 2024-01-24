package com.jetpack.firebasekit.google_signin.view.navigation

sealed class Screens(val route: String) {
    object LoginScreen: Screens("login_screen")

    object HomeScreen: Screens("home_screen")
    object ProfileScreen: Screens("profile_screen")
}
