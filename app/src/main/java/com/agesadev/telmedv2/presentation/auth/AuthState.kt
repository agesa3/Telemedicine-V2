package com.agesadev.telmedv2.presentation.auth

import com.google.firebase.auth.FirebaseUser

data class AuthState(
    val isLoading: Boolean = false,
    val error: String = "",
    val user: FirebaseUser? = null
)