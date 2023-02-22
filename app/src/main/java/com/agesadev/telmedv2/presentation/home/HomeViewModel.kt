package com.agesadev.telmedv2.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agesadev.telmedv2.data.repository.home.PatientsRepository
import com.agesadev.telmedv2.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val patientsRepository: PatientsRepository
) : ViewModel() {

    private val _patients = MutableStateFlow(PatientListState())
    val patients: StateFlow<PatientListState> = _patients

    init {
        getAllPatients()
    }

    private fun getAllPatients() {
        viewModelScope.launch {
            patientsRepository.getPatients().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _patients.value = PatientListState(
                            isLoading = false,
                            patients = result.data ?: emptyList()
                        )
                    }
                    is Resource.Error -> {
                        _patients.value = PatientListState(
                            errorMessage = result.error ?: "An Error Occurred"
                        )
                    }
                    is Resource.Loading -> {
                        _patients.value = PatientListState(isLoading = true)
                    }
                }
            }
        }

    }
}