package com.jetpack.firebasekit.google_signin.domain

import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import com.jetpack.firebasekit.google_signin.data.AuthStateResponse
import com.jetpack.firebasekit.google_signin.data.DataProvider
import com.jetpack.firebasekit.google_signin.data.FirebaseSignInResponse
import com.jetpack.firebasekit.google_signin.data.OneTapSignInResponse
import com.jetpack.firebasekit.google_signin.data.Response
import com.jetpack.firebasekit.google_signin.data.SignOutResponse
import com.jetpack.firebasekit.google_signin.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.cancellation.CancellationException

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    @Named(Constants.SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    @Named(Constants.SIGN_UP_REQUEST)
    private var signUpRequest: BeginSignInRequest

) : AuthRepository {
    override fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse {
        TODO("Not yet implemented")
    }

    override suspend fun signInAnonymously(): FirebaseSignInResponse {
        return try {
            val authResult = auth.signInAnonymously().await()
            authResult.user?.let { user ->
                Log.d(TAG, "FirebaseAuthSuccess: Anonymous UID: ${user.uid}")
            }
            Response.Success(authResult)
        } catch (e: Exception) {
            Log.e(TAG, "FirebaseAuthError: Failed to Sign in anonymously")
            Response.Failure(e)
        }
    }

    override suspend fun onTapSignIn(): OneTapSignInResponse {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            Response.Success(signInResult)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


    /**
     * For Google sign in we need 1. credential, 2. googleIdToken
     */
    override suspend fun signInWithGoogle(credential: SignInCredential): FirebaseSignInResponse {
        val googleCredential = GoogleAuthProvider.getCredential(
            credential.googleIdToken, null
        )
        return authenticateUser(googleCredential)
    }

    override suspend fun signOut(): SignOutResponse {
        return try {
            oneTapClient.signOut().await()
            auth.signOut()
            Response.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            Response.Failure(e)
        }
    }

    // Additional functions

    private suspend fun authenticateUser(credential: AuthCredential): FirebaseSignInResponse {
        return when {
            auth.currentUser != null -> authLink(credential)
            else -> authSignIn(credential)
        }
    }

    private suspend fun authLink(credential: AuthCredential): FirebaseSignInResponse {
        return try {
            val authResult = auth.currentUser?.linkWithCredential(credential)?.await()
            Log.i(TAG, "User: ${authResult?.user?.uid}")
            DataProvider.updateAuthState(authResult?.user)
            Response.Success(authResult)
        } catch (error: FirebaseAuthException) {
            when (error.errorCode) {
                Constants.AuthErrors.CREDENTIAL_ALREADY_IN_USE,
                Constants.AuthErrors.EMAIL_ALREADY_IN_USE -> {
                    Log.e(TAG, "FirebaseAuthError: authLink(credential:) failed, ${error.message}")
                    return authSignIn(credential)
                }
            }
            Response.Failure(error)
        } catch (error: Exception) {
            Response.Failure(error)
        }
    }

    private suspend fun authSignIn(credential: AuthCredential): FirebaseSignInResponse {
        return try {
            val authResult = auth.signInWithCredential(credential).await()
            Log.d(TAG, "User: ${authResult?.user?.uid}")
            DataProvider.updateAuthState(authResult?.user)
            Response.Success(authResult)
        } catch (error: Exception) {
            Response.Failure(error)
        }
    }


    companion object {
        private const val TAG = "Google AuthRepository"
    }
}