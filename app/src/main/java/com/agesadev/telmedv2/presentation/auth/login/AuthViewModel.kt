package com.agesadev.telmedv2.presentation.auth.login

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

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _signUpFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signUpFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    val currentUser: FirebaseUser?
        get() = authRepository.currentUser

    init {
        if (authRepository.currentUser != null) {
            _loginFlow.value = Resource.Success(authRepository.currentUser!!)
        }
    }


    fun login(email: String, password: String) = viewModelScope.launch {
        val result = authRepository.login(email, password)
        result.collectLatest {
            _loginFlow.value = it
        }
    }

    fun signUp(email: String, password: String) = viewModelScope.launch {
        val result = authRepository.signUp(email, password)
        result.collectLatest {
            _signUpFlow.value = it
        }
    }


    fun logout() {
        authRepository.logout()
        _signUpFlow.value = null
        _loginFlow.value = null
    }
}