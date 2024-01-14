package com.jetpack.firebasekit.module

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.jetpack.firebasekit.R

fun buildSignInRequest(): BeginSignInRequest {
    return BeginSignInRequest.Builder()
        /** Here we follow the Builder Pattern **/
        .setGoogleIdTokenRequestOptions(
            GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(R.string.web_client_id.toString())
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()
}