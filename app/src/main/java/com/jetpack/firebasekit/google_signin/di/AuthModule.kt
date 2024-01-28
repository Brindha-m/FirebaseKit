package com.jetpack.firebasekit.google_signin.di

import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.jetpack.firebasekit.BuildConfig
import com.jetpack.firebasekit.google_signin.di.wrapper.RemoteConfigWrapper
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
    @Singleton
    fun provideRemoteConfig() = FirebaseRemoteConfig.getInstance()


    @Provides
    @Singleton
    fun provideRemoteConfigWrapper(remoteConfig: FirebaseRemoteConfig): RemoteConfigWrapper =
        RemoteConfigWrapper(remoteConfig)
}





