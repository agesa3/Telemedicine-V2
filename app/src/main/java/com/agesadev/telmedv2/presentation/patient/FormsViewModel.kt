package com.agesadev.telmedv2.presentation.patient

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.agesadev.telmedv2.data.models.PatientInfo
import com.agesadev.telmedv2.data.repository.home.PatientsRepository
import com.agesadev.telmedv2.presentation.forms.FormState
import com.agesadev.telmedv2.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormsViewModel @Inject constructor(
    private val patientsRepository: PatientsRepository
): ViewModel() {

    private val _formsSaved : MutableStateFlow<FormState> = MutableStateFlow(FormState())
    val formsSaved: LiveData<FormState> get() = _formsSaved.asLiveData()

    val patientDetail = PatientInfo("", "")
    val patientDocumentId : String? = null

    fun updatePatientDetails() = viewModelScope.launch {
        if(patientDocumentId != null) {
            patientsRepository.updatePatient(patientDetail, patientDocumentId).collectLatest { result ->
                when(result) {
                    is Resource.Error -> {
                        _formsSaved.value = FormState(false, result.error?:"")
                    }
                    is Resource.Loading -> {
                        _formsSaved.value = FormState(true)
                    }
                    is Resource.Success -> {
                        _formsSaved.value = FormState(false, saved = result.data)
                    }
                }
            }
        }
    }
}