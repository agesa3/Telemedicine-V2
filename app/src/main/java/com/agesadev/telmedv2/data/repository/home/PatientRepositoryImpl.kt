package com.agesadev.telmedv2.data.repository.home

import com.agesadev.telmedv2.data.models.PersonalInfo
import com.agesadev.telmedv2.utils.Resource
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(
    private val patientsRef: CollectionReference
) : PatientsRepository {


    override fun getPatients(): Flow<Resource<List<PersonalInfo>>> = callbackFlow {
        val snapShotListener = patientsRef.addSnapshotListener { snapshot, exception ->
            val patientsResponse = if (snapshot != null && exception == null) {
                val patients = snapshot.toObjects(PersonalInfo::class.java)
                trySend(Resource.Success(patients)).isSuccess
            } else {
                trySend(
                    Resource.Error(
                        error = exception?.localizedMessage ?: "An Error Occurred"
                    )
                ).isSuccess
            }
        }
        awaitClose {
            snapShotListener.remove()
        }
    }
}