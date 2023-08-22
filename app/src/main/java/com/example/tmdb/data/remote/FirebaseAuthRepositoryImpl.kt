package com.example.tmdb.data.remote

import android.util.Log
import com.example.tmdb.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


/**
 * FirebaseAuthRepositoryImpl es una implementación de la interfaz AuthRepository que utiliza la clase FirebaseAuth de Firebase para realizar operaciones
 * de inicio de sesión y registro. La clase implementa los métodos login y signUp definidos en AuthRepository.
 * */
class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    // Este método se encarga de realizar el proceso de inicio de sesión utilizando el correo electrónico y la contraseña proporcionados.
    // Utiliza la instancia de FirebaseAuth para autenticar al usuario.
    override suspend fun login(email: String, password: String): Boolean {
        return try {
            var isSuccessful = true
            // Se utiliza el método signInWithEmailAndPassword de FirebaseAuth para realizar el inicio de sesión.
            firebaseAuth.signInWithEmailAndPassword(email, password)
                //Si el inicio de sesión es exitoso, se establece isSuccessful en true.
                .addOnSuccessListener { isSuccessful = true }
                // Si el inicio de sesión falla, se establece isSuccessful en false.
                .addOnFailureListener { isSuccessful = false }
                .await()
            isSuccessful
        } catch (e: Exception) {
            Log.d("test", e.toString())
            false
        }
    }

    // Este método suspendido se encarga de realizar el proceso de registro utilizando el correo electrónico y la contraseña proporcionados.
    override suspend fun signUp(email: String, password: String): Boolean {
        return try {
            var isSuccessful = true
            // Utiliza el método createUserWithEmailAndPassword de FirebaseAuth para crear una nueva cuenta de usuario.
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                // Si la creación de la cuenta se completa, se establece isSuccessful según el resultado.
                .addOnCompleteListener { isSuccessful = it.isSuccessful }
                .await()
            isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}