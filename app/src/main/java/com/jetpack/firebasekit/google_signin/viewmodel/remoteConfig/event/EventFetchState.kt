package com.jetpack.firebasekit.google_signin.viewmodel.remoteConfig.event

import com.jetpack.firebasekit.google_signin.data.remoteconfig.EventModel

sealed class EventFetchState {
    data class Success(val events: List<EventModel>) : EventFetchState()
    object Loading : EventFetchState()
    data class Error(val errorMessage: String) : EventFetchState()
}

