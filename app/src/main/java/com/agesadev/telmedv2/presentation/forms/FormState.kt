package com.agesadev.telmedv2.presentation.forms


data class FormState(
    val isLoading: Boolean = false,
    val error: String = "",
    val saved: Boolean? = null
)