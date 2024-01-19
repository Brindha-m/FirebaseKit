package com.jetpack.firebasekit

sealed class Screens(val route: String) {
    object LoginScreen: Screens("login_screen")
    object RegisterScreen: Screens("register_screen")
    object ForgetPswdScreen: Screens("forget_screen")
}
