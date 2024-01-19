package com.jetpack.firebasekit.google_signin.data

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.StateFlow


/**
 *  type aliases - used for creating more descriptive names for certain types
 *  making the code more readable
 *
 */
typealias OneTapSignInResponse = Response<BeginSignInResult>
// Use to get the user info
typealias FirebaseSignInResponse = Response<AuthResult>
typealias SignOutResponse = Response<Boolean>
typealias AuthStateResponse = StateFlow<FirebaseUser?>


sealed class Response<out T> {
    object Loading: Response<Nothing>()
    data class Success<out T>(val data: T?): Response<T>()
    data class Failure(val e: Exception): Response<Nothing>()
}