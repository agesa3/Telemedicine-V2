package com.agesadev.telmedv2.di

import com.agesadev.telmedv2.data.repository.auth.AuthRepository
import com.agesadev.telmedv2.data.repository.auth.AuthRepositoryImpl
import com.agesadev.telmedv2.data.repository.home.PatientRepositoryImpl
import com.agesadev.telmedv2.data.repository.home.PatientsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataLayerModule {

    @Singleton
    @Provides
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()


    @Provides
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore


    @Provides
    @Singleton
    fun providePatientsRef(db: FirebaseFirestore) = db.collection("patients")

    @Provides
    @Singleton
    fun providePatientsRepository(patientsRef: CollectionReference): PatientsRepository {
        return PatientRepositoryImpl(patientsRef)
    }
}