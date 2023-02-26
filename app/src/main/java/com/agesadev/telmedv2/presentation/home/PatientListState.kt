package com.agesadev.telmedv2.presentation.home

import com.agesadev.telmedv2.data.models.PatientInfo


data class PatientListState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val patients: List<PatientInfo> = emptyList()
)