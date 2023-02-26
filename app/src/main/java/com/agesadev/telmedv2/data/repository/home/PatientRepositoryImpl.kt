package com.agesadev.telmedv2.data.repository.home

import com.agesadev.telmedv2.data.models.PatientInfo
import com.agesadev.telmedv2.utils.Resource
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

class PatientRepositoryImpl @Inject constructor(
    private val patientsRef: CollectionReference
) : PatientsRepository {


    override fun getPatients(): Flow<Resource<List<PatientInfo>>> = callbackFlow {
        send(Resource.Loading())
        val snapShotListener = patientsRef.addSnapshotListener { snapshot, exception ->
            val patientsResponse = if (snapshot != null && exception == null) {
                val patients = snapshot.toObjects(PatientInfo::class.java)
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

    override fun registerPatient(patient: PatientInfo): Flow<Resource<String>> = channelFlow{
        send(Resource.Loading())
        try {
            patientsRef.add(patient)
                .addOnSuccessListener { 
                    launch {
                        send(Resource.Success(it.id))
                    }
                }
                .addOnFailureListener { 
                    launch { 
                        send(Resource.Error(null, it.message))
                    }
                }
                
        } catch (error: IOException) {
            send(Resource.Error(null, "Network error. Please check your internet"))
        } catch (error: Exception) {
            send(Resource.Error(null, error.message))
        }
        awaitClose()
    }

    override fun updatePatient(patient: PatientInfo, id: String): Flow<Resource<Boolean>> = channelFlow {
        send(Resource.Loading())
        try {
            patientsRef.document(id).update(toMap(patient))
                .addOnSuccessListener {
                    launch {
                        send(Resource.Success(true))
                    }
                }
                .addOnFailureListener {
                    launch {
                        send(Resource.Error(null, it.message))
                    }
                }
        } catch (error: IOException) {
            send(Resource.Error(null, "Network error. Please check your internet"))
        } catch (error: Exception) {
            send(Resource.Error(null, error.message))
        }
        awaitClose()
    }

    private fun <T: Any> toMap(obj: T): Map<String, Any?> {
        return (obj::class as KClass<T>).memberProperties.associate { prop ->
            prop.name  to prop.get(obj)?.let { value ->
                if (value::class.isData) {
                    toMap(value)
                } else {
                    value
                }
            }
        }
    }

}