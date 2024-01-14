package com.jetpack.firebasekit.viewmodel.state

data class SignInState(
    val signInSuccess: Boolean = false,
    val signInError: String? = null
)