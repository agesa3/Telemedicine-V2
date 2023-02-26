package com.agesadev.telmedv2.data.repository.home

import com.agesadev.telmedv2.data.models.PatientInfo
import com.agesadev.telmedv2.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PatientsRepository {
    fun getPatients(): Flow<Resource<List<PatientInfo>>>

    fun registerPatient(patient: PatientInfo): Flow<Resource<String>>

    fun updatePatient(patient: PatientInfo, id: String): Flow<Resource<Boolean>>
}