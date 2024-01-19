package com.jetpack.firebasekit.google_signin.domain

import com.google.android.gms.auth.api.identity.SignInCredential
import com.jetpack.firebasekit.google_signin.data.AuthStateResponse
import com.jetpack.firebasekit.google_signin.data.FirebaseSignInResponse
import com.jetpack.firebasekit.google_signin.data.OneTapSignInResponse
import com.jetpack.firebasekit.google_signin.data.SignOutResponse
import kotlinx.coroutines.CoroutineScope

interface AuthRepository {

    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse

    suspend fun signInAnonymously(): FirebaseSignInResponse

    suspend fun onTapSignIn(): OneTapSignInResponse

    suspend fun signInWithGoogle(credential: SignInCredential): FirebaseSignInResponse

    suspend fun signOut(): SignOutResponse
}