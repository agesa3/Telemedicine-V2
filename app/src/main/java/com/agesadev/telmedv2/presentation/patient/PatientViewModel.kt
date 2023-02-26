package com.agesadev.telmedv2.presentation.patient

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.agesadev.telmedv2.data.models.PatientInfo
import com.agesadev.telmedv2.data.repository.home.PatientsRepository
import com.agesadev.telmedv2.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientViewModel @Inject constructor(
    private val patientsRepository: PatientsRepository
): ViewModel() {

    private val _patientRegistered: MutableStateFlow<RegistrationState> = MutableStateFlow(
        RegistrationState()
    )
    val patientRegistered: LiveData<RegistrationState> = _patientRegistered.asLiveData()

    fun registerPatient(patient: PatientInfo) = viewModelScope.launch {
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