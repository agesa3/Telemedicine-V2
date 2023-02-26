package com.agesadev.telmedv2.presentation.patient

import com.google.firebase.auth.FirebaseUser


data class RegistrationState(
    val isLoading: Boolean = false,
    val error: String = "",
    val user: String? = null
)