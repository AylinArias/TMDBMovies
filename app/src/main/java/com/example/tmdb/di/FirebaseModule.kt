package com.example.tmdb.di


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * FirebaseModule define cómo se deben crear y proporcionar instancias singleton de FirebaseAuth y FirebaseFirestore utilizando Hilt.
 * Estas instancias se utilizarán en otras partes de la aplicación donde se necesite interactuar con Firebase Authentication y Firebase Firestore.
 * */


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    // Esta función provee una instancia singleton de FirebaseAuth. FirebaseAuth.getInstance() crea y devuelve una instancia de la clase FirebaseAuth,
    // que se utiliza para autenticar y gestionar usuarios en Firebase Authentication.
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    // Esta función provee una instancia singleton de FirebaseFirestore. FirebaseFirestore.getInstance() crea
    // y devuelve una instancia de la clase FirebaseFirestore, que se utiliza para interactuar con la base de datos en la nube de Firebase Firestore.
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

}