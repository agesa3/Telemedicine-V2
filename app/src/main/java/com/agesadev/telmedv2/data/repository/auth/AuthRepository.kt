package com.agesadev.telmedv2.data.repository.auth

import com.agesadev.telmedv2.utils.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val currentUser: FirebaseUser?
    fun login(email: String, password: String): Flow<Resource<FirebaseUser>>
    fun signUp(email: String, password: String): Flow<Resource<FirebaseUser>>
    fun logout()
}