package com.jetpack.firebasekit.google_signin.di.wrapper

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings


class RemoteConfigWrapper(private val firebaseRemoteConfig: FirebaseRemoteConfig) {

    fun initRemoteConfig() {
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(60)
            .build()

        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)

        firebaseRemoteConfig.setDefaultsAsync(
            mapOf(
                "todaysSpecialState" to "Default Value",
                "fetchSuccess" to true,
                "febEvents" to {}
            )
        )
    }


    // String from Remote config

    fun fetchTodaySpecialState(specialShowState: (String) -> Unit) {
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener {
            specialShowState(firebaseRemoteConfig.getString("todaysSpecialState"))
        }.addOnFailureListener {
            Log.d(TAG, it.message.orEmpty())
        }
    }

    // Json from Remote config


    fun fetchAllSpecialJson(jsonCallback: (String) -> Unit) {
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val jsonString = firebaseRemoteConfig.getString("febEvents")
                jsonCallback(jsonString)
            } else {
                Log.d(TAG, "Failed to fetch JSON object: ${task.exception?.message.orEmpty()}")
            }
        }
    }

    companion object {
        private const val TAG = "RemoteConfigWrapper"
    }

}