package com.jetpack.firebasekit.model


data class SignInResponse(
    val data: UserData?,
    val errorMessage: String?
)


data class UserData(
    val userId: String,
    val username: String?,
    val profilePicture: String?,
    val email: String?
)