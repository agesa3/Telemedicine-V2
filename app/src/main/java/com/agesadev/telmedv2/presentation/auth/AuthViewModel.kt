package com.agesadev.telmedv2.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agesadev.telmedv2.data.repository.auth.AuthRepository
import com.agesadev.telmedv2.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authStateSignUp = MutableStateFlow(AuthState())
    val authStateSignUp: StateFlow<AuthState> = _authStateSignUp

    private val _authStateLogin = MutableStateFlow(AuthState())
    val authStateLogin: StateFlow<AuthState> = _authStateLogin

    val currentUser: FirebaseUser?
        get() = authRepository.currentUser

    init {
        if (authRepository.currentUser != null)
            _authStateLogin.value = AuthState(user = authRepository.currentUser)
    }


    fun signUp(email: String, password: String) = viewModelScope.launch {
        authRepository.signUp(email, password).collectLatest { result ->
            when (result) {
                is Resource.Error -> {
                    _authStateSignUp.value =
                        AuthState(isLoading = false, error = result.error ?: "")
                }
                is Resource.Loading -> {
                    _authStateSignUp.value = AuthState(isLoading = true)
                }
                is Resource.Success -> {
                    _authStateSignUp.value = AuthState(isLoading = false, user = result.data)
                }
            }

        }
    }


    fun login(email: String, password: String) = viewModelScope.launch {
        authRepository.login(email, password).collectLatest { result ->
            when (result) {
                is Resource.Error -> {
                    _authStateLogin.value = AuthState(
                        isLoading = false,
                        error = result.error ?: "An Error Occurred.Try Again"
                    )
                }
                is Resource.Loading -> {
                    _authStateLogin.value = AuthState(isLoading = true)
                }
                is Resource.Success -> {
                    _authStateLogin.value = AuthState(isLoading = false, user = result.data)
                }
            }

        }
    }


    fun logout() {
        authRepository.logout()
    }
}