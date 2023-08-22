package com.example.tmdb.domain.usecase

import com.example.tmdb.domain.repository.AuthRepository
import com.example.tmdb.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * FirebaseSignUpUseCase encapsula la lógica para el proceso de registro en Firebase utilizando coroutines y Flow.
 * */

class FirebaseSignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    // La función acepta el correo electrónico y la contraseña como argumentos y devuelve un flujo (Flow) de estados Resource<Boolean>.
    suspend operator fun invoke(email: String, password: String): Flow<Resource<Boolean>> = flow {
        // Se emite un estado de carga para indicar que la operación de registro está en curso.
        emit(Resource.Loading)
        // Se encargará de interactuar con las APIs de Firebase para realizar el registro del usuario.
        val isSignUpSuccessfully = authRepository.signUp(email, password)
        // Se verifica si el registro fue exitoso. Si es exitoso, se emite un estado de éxito
        if (isSignUpSuccessfully) {
            emit(Resource.Success(true))
        } else {
            // Si no es exitoso, se emite un estado de error
            emit(Resource.Error("Sign up error"))
        }
    }
}

