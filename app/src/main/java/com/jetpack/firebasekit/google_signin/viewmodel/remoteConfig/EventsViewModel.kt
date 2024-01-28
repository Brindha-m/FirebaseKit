package com.jetpack.firebasekit.google_signin.viewmodel.remoteConfig

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.jetpack.firebasekit.google_signin.data.remoteconfig.EventModel
import com.jetpack.firebasekit.google_signin.di.wrapper.RemoteConfigWrapper
import com.jetpack.firebasekit.google_signin.viewmodel.remoteConfig.event.EventFetchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) : ViewModel() {

    private val _events = MutableStateFlow<EventFetchState>(EventFetchState.Loading)
    val events: StateFlow<EventFetchState> get() = _events

    init {
        fetchRemoteConfig()
    }

    private fun fetchRemoteConfig() {
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val jsonString = firebaseRemoteConfig.getString("febEvents")
                val eventsList = parseJson(jsonString)
                _events.value = EventFetchState.Success(eventsList)
            } else {
                Log.d(TAG, "Failed to fetch JSON object: ${task.exception?.message.orEmpty()}")
                _events.value = EventFetchState.Error("Failed to fetch JSON object")
            }
        }
    }

    private fun parseJson(jsonString: String): List<EventModel> {
        return try {
            val gson = Gson()
            val typeToken = object : TypeToken<List<EventModel>>() {}.type
            gson.fromJson(jsonString, typeToken)
        } catch (e: JsonSyntaxException) {
            emptyList()
        }
    }
}
