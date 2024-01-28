package com.jetpack.firebasekit.google_signin.data.remoteconfig

import com.google.gson.annotations.SerializedName


data class EventModel(

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("eventDescription")
    val eventDescription: String? = null,

    @field:SerializedName("eventName")
    val eventName: String? = null

)
