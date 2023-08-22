package com.example.tmdb.di

import com.example.tmdb.data.remote.FirebaseAuthRepositoryImpl
import com.example.tmdb.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * RepositoryModule se encarga de proporcionar una implementación concreta para la interfaz AuthRepository.
 * Utiliza la anotación @Binds para asociar la implementación FirebaseAuthRepositoryImpl a la interfaz AuthRepository.
 * */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    // Esta función abstracta se encarga de vincular la implementación FirebaseAuthRepositoryImpl a la interfaz AuthRepository
    abstract fun bindAuthRepository(authRepository: FirebaseAuthRepositoryImpl): AuthRepository
}