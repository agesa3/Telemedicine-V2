package com.agesadev.telmedv2.data.repository.auth

import android.util.Log
import com.agesadev.telmedv2.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override fun login(email: String, password: String): Flow<Resource<FirebaseUser>> =
        callbackFlow {
            val task = firebaseAuth.signInWithEmailAndPassword(email, password)
            task.addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Resource.Success(firebaseAuth.currentUser)).isSuccess
                    Log.d("LoginRepository", "login: ${firebaseAuth.currentUser?.displayName}}")
                } else {
                    trySend(Resource.Error(null, it.exception?.message)).isFailure
                }
            }
            awaitClose()
        }

    override fun signUp(email: String, password: String): Flow<Resource<FirebaseUser>> =
        callbackFlow {
            val task = firebaseAuth.createUserWithEmailAndPassword(email, password)
            task.addOnCompleteListener {
                if (it.isSuccessful) {
                    task.result?.user?.updateProfile(
                        UserProfileChangeRequest.Builder().setDisplayName(email).build()
                    )
                    trySend(Resource.Success(firebaseAuth.currentUser)).isSuccess
                } else {
                    trySend(Resource.Error(null, it.exception?.message)).isFailure
                }
            }
            awaitClose()
        }


    override fun logout() {
        firebaseAuth.signOut()
    }
}