package com.jetpack.firebasekit.google_signin.view.component

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.jetpack.firebasekit.google_signin.data.DataProvider
import com.jetpack.firebasekit.google_signin.data.Response

@Composable
fun AnonymousSignIn() {
    when (val anonymousResponse = DataProvider.anonymousSignInResponse) {
        is Response.Loading -> {
            Log.i("Login:AnonymousSignIn", "Loading")
            LoadingAnimation()
        }

        is Response.Success -> anonymousResponse.data?.let { authResult ->
            Log.i("Login:AnonymousSignIn", "Success: $authResult")
        }

        is Response.Failure -> LaunchedEffect(Unit) {
            Log.e("Login:AnonymousSignIn", "${anonymousResponse.e}")
        }
    }
}


@Composable
fun GoogleSignIn(
    launch: () -> Unit
) {
    when (val signInWithGoogleResponse = DataProvider.googleSignInResponse) {
        is Response.Loading -> {
            Log.i("Login:GoogleSignIn", "Loading")
            LoadingAnimation()
        }

        is Response.Success -> signInWithGoogleResponse.data?.let { authResult ->
            Log.i("Login:GoogleSignIn", "Success: $authResult")
            launch()
        }

        is Response.Failure -> LaunchedEffect(Unit) {
            Log.e("Login:GoogleSignIn", "${signInWithGoogleResponse.e}")
        }
    }

}


@Composable
fun OneTapSignIn(
    launch: (result: BeginSignInResult) -> Unit
) {
    when (val oneTapSignInResponse = DataProvider.oneTapSignInResponse) {
        is Response.Loading -> {
            Log.i("Login:OneTap", "Loading")
            LoadingAnimation()
        }

        is Response.Success -> oneTapSignInResponse.data?.let { signInResult ->
            LaunchedEffect(signInResult) {
                launch(signInResult)
            }
        }

        is Response.Failure -> LaunchedEffect(Unit) {
            Log.e("Login:OneTap", "${oneTapSignInResponse.e}")
        }
    }

}