package com.jetpack.firebasekit.viewmodel

import androidx.lifecycle.ViewModel
import com.jetpack.firebasekit.model.SignInResponse
import com.jetpack.firebasekit.viewmodel.state.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel: ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResponse) {
        _state.update { it.copy(
            signInSuccess = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetState() {
        _state.update { SignInState() }
    }
}