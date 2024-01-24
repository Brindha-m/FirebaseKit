package com.jetpack.firebasekit.google_signin.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseUser
import com.jetpack.firebasekit.google_signin.data.Response.Success


enum class AuthState {
    Authenticated, SignedIn, SignedOut
}

object DataProvider {
    var oneTapSignInResponse by mutableStateOf<OneTapSignInResponse>(Success(null))

    var anonymousSignInResponse by mutableStateOf<FirebaseSignInResponse>(Success(null))

    var googleSignInResponse by mutableStateOf<FirebaseSignInResponse>(Success(null))

    var signOutResponse by mutableStateOf<SignOutResponse>(Success(false))

    var isAuthenticated by mutableStateOf(false)

    var isAnonymous by mutableStateOf(false)

    var authState by mutableStateOf(AuthState.SignedOut)
        private set

    var user by mutableStateOf<FirebaseUser?>(null)

    fun updateAuthState(user: FirebaseUser?) {
        this.user = user
        isAuthenticated = user != null
        isAnonymous = user?.isAnonymous ?: false

        authState = when {
            isAuthenticated && isAnonymous -> AuthState.Authenticated
            isAuthenticated -> AuthState.SignedIn
            else -> AuthState.SignedOut
        }
    }
}