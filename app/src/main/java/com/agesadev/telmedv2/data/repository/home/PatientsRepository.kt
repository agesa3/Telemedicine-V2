package com.agesadev.telmedv2.data.repository.home

import com.agesadev.telmedv2.data.models.PersonalInfo
import com.agesadev.telmedv2.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PatientsRepository {
    fun getPatients(): Flow<Resource<List<PersonalInfo>>>
}