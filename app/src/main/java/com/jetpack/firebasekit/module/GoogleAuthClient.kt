package com.jetpack.firebasekit.module

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jetpack.firebasekit.model.SignInResponse
import com.jetpack.firebasekit.model.UserData
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class GoogleAuthClient(
    private val context: Context,
    private val oneTapClient: SignInClient,
) {
    private val authClient = Firebase.auth

    suspend fun signIn() {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()?.pendingIntent?.intentSender
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            null
        }
    }

    suspend fun signInWithIntent(intent: Intent): SignInResponse {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = authClient.signInWithCredential(googleCredential).await().user
            SignInResponse(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePicture = photoUrl.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            SignInResponse(
                data = null,
                errorMessage = e.message
            )
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            authClient.signOut()
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }


    fun getSignedInUser(): UserData? = authClient.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePicture = photoUrl?.toString()
        )
    }
}