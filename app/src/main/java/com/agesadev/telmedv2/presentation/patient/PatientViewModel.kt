package com.agesadev.telmedv2.presentation.patient

import android.app.Person
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agesadev.telmedv2.data.models.PersonalInfo
import com.agesadev.telmedv2.data.repository.home.PatientsRepository
import com.agesadev.telmedv2.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class PatientViewModel @Inject constructor(
    private val patientsRepository: PatientsRepository
): ViewModel() {

    private val _patientRegistered: MutableStateFlow<RegistrationState> = MutableStateFlow(
        RegistrationState()
    )
    val patientRegistered: StateFlow<RegistrationState> = _patientRegistered

    fun registerPatient(patient: PersonalInfo) = viewModelScope.launch {
        patientsRepository.registerPatient(patient).collectLatest { result ->
            when(result) {
                is Resource.Error -> {
                    _patientRegistered.value = RegistrationState(false, result.error?:"")
                }
                is Resource.Loading -> {
                    _patientRegistered.value = RegistrationState(true)
                }
                is Resource.Success -> {
                    _patientRegistered.value = RegistrationState(false, user = result.data)
                }
            }
        }
    }


}