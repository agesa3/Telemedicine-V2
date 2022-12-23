package com.agesadev.telmedv2.data.repository.auth

import com.agesadev.telmedv2.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override fun login(email: String, password: String): Flow<Resource<FirebaseUser>> =
        callbackFlow {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                trySend(Resource.Success(user)).isSuccess
            } else {
                trySend(Resource.Error(Exception("User is null"))).isSuccess
            }
            awaitClose()
        }

    override fun signUp(email: String, password: String): Flow<Resource<FirebaseUser>> =
        callbackFlow {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(email).build()
            )?.await()
            val user = result.user
            if (user != null) {
                trySend(Resource.Success(user)).isSuccess
            } else {
                trySend(Resource.Error(Exception("User is null"))).isSuccess
            }
            awaitClose()
        }


    override fun logout() {
        firebaseAuth.signOut()
    }
}