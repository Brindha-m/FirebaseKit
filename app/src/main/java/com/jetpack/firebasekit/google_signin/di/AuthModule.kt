package com.jetpack.firebasekit.google_signin.di

import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.jetpack.firebasekit.BuildConfig
import com.jetpack.firebasekit.google_signin.domain.AuthRepository
import com.jetpack.firebasekit.google_signin.domain.AuthRepositoryImpl
import com.jetpack.firebasekit.google_signin.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl


    @Provides
    fun provideOneTapClient(
        @ApplicationContext
        context: Context
    ) = Identity.getSignInClient(context)


    @Provides
    @Named(Constants.SIGN_IN_REQUEST)
    fun provideSignInRequest() =
        BeginSignInRequest.Builder()
            /** Here we follow the Builder Pattern **/
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Shows only accounts previously used to Sign in.
                    .setFilterByAuthorizedAccounts(true)
                    // Your Server's client ID
                    .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()

    @Provides
    @Named(Constants.SIGN_UP_REQUEST)
    fun provideSignUpRequest() =
        BeginSignInRequest.Builder()
            /** Here we follow the Builder Pattern **/
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Shows all the available accounts to sign in.
                    .setFilterByAuthorizedAccounts(false)
                    // Your Server's client ID
                    .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()

}





