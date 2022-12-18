package com.agesadev.telmedv2.di

import com.agesadev.telmedv2.data.repository.auth.AuthRepository
import com.agesadev.telmedv2.data.repository.auth.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DataLayerModule {

    @Provides
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()


    @Provides
    fun provideAuthRepository(auth: AuthRepositoryImpl): AuthRepository = auth
}